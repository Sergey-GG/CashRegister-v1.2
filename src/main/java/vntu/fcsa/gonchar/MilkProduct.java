package vntu.fcsa.gonchar;

import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

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


    @Override
    public void readProducts() {
        try {
            Scanner scanner = new Scanner(new File(CashRegister.MILK_PRODUCTS_TXT), StandardCharsets.UTF_8);
            while (scanner.hasNextLine()) {
                ClassPathXmlApplicationContext context =
                        new ClassPathXmlApplicationContext("applicationContext.xml");
                Product product = context.getBean("milkProduct", MilkProduct.class);
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
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        CashRegister.MILK_PRODUCTS_LIST.sort(Comparator.comparing(IProducts::getId));

    }
}