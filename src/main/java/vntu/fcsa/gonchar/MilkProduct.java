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
public class MilkProduct extends Product {

    private MilkProduct() {
    }

    private MilkProduct(int id, String name, double weight, double cost) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.cost = cost;
    }

    public static MilkProduct createMilkProduct() {
        AnnotationConfigApplicationContext context= new AnnotationConfigApplicationContext(SpringConfig.class);
        return context.getBean("milkProduct", MilkProduct.class);
    }

    @Override
    public void readProducts() {
        try {
            Scanner scanner = new Scanner(new File(CashRegister.MILK_PRODUCTS_TXT), StandardCharsets.UTF_8);
            while (scanner.hasNextLine()) {
                Product product = createMilkProduct();
                String[] strings = scanner.nextLine().split(";");
                product.setId(Integer.parseInt(strings[0]));
                product.setName(strings[1]);
                product.setWeight(Double.parseDouble(strings[2]));
                product.setCost(Double.parseDouble(strings[3]));
                product.autoDelivery();
                CashRegister.MILK_PRODUCTS_LIST.add(new MilkProduct(product.getId(), product.getName(), product.getWeight(),
                        product.getCost()));

            }
            scanner.close();
        } catch (IOException | ArrayIndexOutOfBoundsException ex) {
            System.out.println(ex.getMessage());
        }
        CashRegister.MILK_PRODUCTS_LIST.sort(Comparator.comparing(IProducts::getId));
    }
}