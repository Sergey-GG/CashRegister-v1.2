package vntu.fcsa.gonchar;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import vntu.fcsa.gonchar.config.SpringConfig;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.Scanner;

/**
 * VNTU-FCSA
 * 1-ICT-20(b)
 * Gonchar Sergey
 **/

@Component
@Scope("prototype")
public class MeatProduct extends Product {

    private MeatProduct() {
    }

    private MeatProduct(int id, String name, double weight, double cost) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.cost = cost;
    }

    public static MeatProduct createMeatProduct() {
        AnnotationConfigApplicationContext context= new AnnotationConfigApplicationContext(SpringConfig.class);
        return context.getBean("meatProduct", MeatProduct.class);
    }

    @Override
    public void readProducts() {
        try {
            Scanner scanner = new Scanner(new File(CashRegister.MEAT_PRODUCTS_TXT), StandardCharsets.UTF_8);
            while (scanner.hasNextLine()) {
                Product product = createMeatProduct();
                String[] strings = scanner.nextLine().split(";");
                int i = 0;
                int id = CashRegister.MILK_PRODUCTS_LIST.size() + 1;
                while (i <= CashRegister.MEAT_PRODUCTS_LIST.size()) {
                    product.setId(id);
                    id++;
                    i++;
                }
                product.setName(strings[1]);
                product.setWeight(Double.parseDouble(strings[2]));
                product.setCost(Double.parseDouble(strings[3]));
                product.autoDelivery();
                CashRegister.MEAT_PRODUCTS_LIST.add(new MeatProduct(product.getId(), product.getName(), product.getWeight(),
                        product.getCost()));

            }
            scanner.close();
        } catch (IndexOutOfBoundsException | IOException ex) {
            System.out.println(ex.getMessage());
        }
        CashRegister.MILK_PRODUCTS_LIST.sort(Comparator.comparing(IProducts::getId));

    }
}

