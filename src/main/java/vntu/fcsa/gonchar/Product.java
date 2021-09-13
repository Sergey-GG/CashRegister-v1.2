package vntu.fcsa.gonchar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import vntu.fcsa.gonchar.config.SpringConfig;

import java.io.FileWriter;
import java.io.IOException;

/**
 * VNTU-FCSA
 * 1-ICT-20(b)
 * Gonchar Sergey
 **/

@Component
@Scope("prototype")
public class Product implements IProducts {
    int id;
    String name;
    double weight;
    double cost;

    @Autowired
    public Product() {
    }

    public Product(int id, String name, double weight, double cost) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.cost = cost;
    }

    @Override
    public String toProductsTXT() {
        return id + ";" + name + ";" + weight + ";" + cost + "\n";
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public double getCost() {
        return cost;
    }


    @Override
    public String toString() {
        return String.format("\n%3s | %-20s | %6s | %5s", id, name, weight, cost);
    }

    @Override
    public void readProducts() {
    }

    public static Product createProduct() {
        AnnotationConfigApplicationContext  context= new AnnotationConfigApplicationContext(SpringConfig.class);
        return context.getBean("product", Product.class);
    }

    public void autoDelivery() {
        try {
            if (this.getWeight() < CashRegister.minWeight) {
                double deliveryCost = CashRegister.autoProductsDelivery * this.getCost() * 3 / 4;
                System.out.println("\nStocks of product '" + this.getName()
                        + "' depleted. Available quantity of product: "
                        + this.getWeight() + " kg.\nDelivery in progress...");
                if (deliveryCost <= CashRegister.getCashInBank()) {
                    this.setWeight(this.getWeight() + CashRegister.autoProductsDelivery);
                    CashRegister.setCashInBank(CashRegister.getCashInBank() - deliveryCost);
                    FileWriter fileWriter = new FileWriter(CashRegister.BANK);
                    fileWriter.write(String.valueOf(CashRegister.getCashInBank()));
                    fileWriter.close();
                    System.out.println("Delivery successfully completed.\nAvailable quantity of product: " +
                            this.getWeight() + " kg.");
                } else System.out.println("Not enough money for delivery.");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
