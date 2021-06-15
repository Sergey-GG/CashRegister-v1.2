package vntu.fcsa.gonchar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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

    public void readProducts() {
    }

    public void autoDelivery() {
        if (this.getWeight() < CashRegister.minWeight) {
            System.out.println("\nStocks of product '" + this.getName()
                    + "' depleted. Available quantity of product: "
                    + this.getWeight() + " kg.\nDelivery in progress...");
            this.setWeight(this.getWeight() + CashRegister.autoProductsDelivery);
            System.out.println("Delivery successfully completed.\nAvailable quantity of product: " +
                    +this.getWeight() + " kg.");
        }
    }
}
