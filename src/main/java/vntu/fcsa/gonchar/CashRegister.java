package vntu.fcsa.gonchar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * VNTU-FCSA
 * 1-ICT-20(b)
 * Gonchar Sergey
 **/

@Component
public class CashRegister {
    private static Double cashInCass;
    private static Double cashInBank;
    @Value("${cashRegister.model}")
    private String model;
    @Value("${cashRegister.batteryCharge}")
    private int batteryCharge;

    static final Double maxCashInCass = 1000.0;
    static final Double minWeight = 1.0;
    static final Double autoProductsDelivery = 3.0;
    static final List<IProducts> MILK_PRODUCTS_LIST = new ArrayList<>();
    static final List<IProducts> MEAT_PRODUCTS_LIST = new ArrayList<>();
    static final String MILK_PRODUCTS_TXT = "milkProducts.txt";
    static final String MEAT_PRODUCTS_TXT = "meatProducts.txt";
    static final String CASS = "cass.txt";
    static final String CHECKS = "checks-logs.txt";
    static final String BANK = "bank.txt";

    private CashRegister() {
    }

    @Override
    public String toString() {
        return "Cash register was started...\n" + "Model: '" + model + '\''
                + ", batteryCharge: " + batteryCharge
                + "%; \nCash in cass: " + cashInCass + "$, cash in bank: " + cashInBank + "$";
    }

    static ArrayList<IProducts> getDataMilks() {
        ArrayList<IProducts> out = new ArrayList<>(MILK_PRODUCTS_LIST);
        Collections.copy(out, MILK_PRODUCTS_LIST);
        return out;
    }

    static ArrayList<IProducts> getDataMeats() {
        ArrayList<IProducts> out1 = new ArrayList<>(MEAT_PRODUCTS_LIST);
        Collections.copy(out1, MEAT_PRODUCTS_LIST);
        return out1;
    }

    static void putMilks(IProducts IProducts) {
        MILK_PRODUCTS_LIST.add(IProducts);
    }

    static void putMeats(IProducts IProducts) {
        MEAT_PRODUCTS_LIST.add(IProducts);
    }

    void batteryDischarge() {
        this.batteryCharge = this.batteryCharge - 20;
    }

    void printCharge() {
        System.out.println("\n--------------------------------------------------------\nBatteryCharge: "
                + this.batteryCharge + "%\nCash in cass: " + cashInCass + "$, cash in bank: "
                + cashInBank + "$\n--------------------------------------------------------\n");
    }


    static void readCass() throws IOException {
        Scanner scanner = new Scanner(new File(CashRegister.CASS), StandardCharsets.UTF_8);
        while (scanner.hasNextLine()) {
            String[] strings = scanner.nextLine().split(";");
            cashInCass = Double.parseDouble(strings[0]);
        }
        Scanner scanner1 = new Scanner(new File(CashRegister.BANK), StandardCharsets.UTF_8);
        while (scanner1.hasNextLine()) {
            String strings;
            strings = scanner1.next();
            cashInBank = Double.parseDouble(strings);
        }
        scanner.close();
    }

    static void cassToBank() throws IOException {
        double toBank = cashInCass - 10;
        cashInCass = cashInCass - toBank;
        cashInBank = cashInBank + toBank;
        FileWriter fileWriter = new FileWriter(CashRegister.BANK);
        fileWriter.write(String.valueOf(cashInBank));
        fileWriter.close();
        FileWriter fileWriter1 = new FileWriter(CashRegister.CASS);
        fileWriter1.write(String.valueOf(cashInCass));
        fileWriter1.close();
    }


    static void remove() {
        System.out.println("Remove product from database (you must enter ID):");
        Scanner scanner = new Scanner(System.in);
        Main.printProducts();
        if (scanner.hasNextInt()) {
            int idKey = scanner.nextInt();
            if (idKey <= MILK_PRODUCTS_LIST.size()) {
                MILK_PRODUCTS_LIST.remove(idKey - 1);
            } else if (idKey <= (MILK_PRODUCTS_LIST.size() + MEAT_PRODUCTS_LIST.size())) {
                MEAT_PRODUCTS_LIST.remove(idKey - MILK_PRODUCTS_LIST.size() - 1);
            } else System.out.println("Error!");
        }
        System.out.println("Removing program has been closed.");
    }


    static void writeMilks() {
        try {
            FileWriter fileWriter = new FileWriter(CashRegister.MILK_PRODUCTS_TXT);
            MILK_PRODUCTS_LIST.sort(Comparator.comparing(IProducts::getId));
            for (IProducts IProducts : MILK_PRODUCTS_LIST) {
                fileWriter.write(IProducts.toProductsTXT());
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void writeMeats() {
        try {
            FileWriter fileWriter = new FileWriter(CashRegister.MEAT_PRODUCTS_TXT);
            MEAT_PRODUCTS_LIST.sort(Comparator.comparing(IProducts::getId));
            for (IProducts IProducts : MEAT_PRODUCTS_LIST) {
                fileWriter.write(IProducts.toProductsTXT());
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static void buyProducts() throws IOException {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter ID to select product:");
        IProducts product = context.getBean("product", Product.class);
        product = getIProducts(scan, product);
        System.out.println("Enter weight to buy:");
        double weightToBuy = scan.nextDouble();
        assert product != null;
        if (weightToBuy <= product.getWeight()) {
            double sum = weightToBuy * product.getCost();
            product.setWeight(product.getWeight() - weightToBuy);
            readCass();
            double cash = cashInCass;
            cash += sum;
            String cashS = String.valueOf(cash);
            String strings = Main.readUsingBufferedReader();
            String check = "--------------------------------------\nID of product: " + product.getId()
                    + "\nName of product: " + product.getName() + "\nWeight: " + weightToBuy
                    + " kg.\nCost: " + product.getCost() + "$\nSum to pay: " + sum
                    + "$";
            System.out.println(check);
            try {
                FileWriter fileWriter = new FileWriter(CashRegister.CHECKS);
                fileWriter.write(strings + "\n" + check);
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                FileWriter fileWriter = new FileWriter(CashRegister.CASS);
                fileWriter.write(cashS);
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else System.out.println("There is not enough product");
    }

    static void manualProductsDelivery() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter ID to select product:");
        IProducts products = context.getBean("product", Product.class);
        products = getIProducts(scan, products);
        System.out.println("Available quantity of product: "
                + products.getWeight() + " kg.\nDo you want order delivery of product?");
        if (scan.next().equals("+")) {
            System.out.println("Enter the weight of product for delivery:");
            double deliveryWeight = scan.nextDouble();
            System.out.println("Delivery in progress...");
            products.setWeight(products.getWeight() + deliveryWeight);
            System.out.println("Delivery successfully completed.");
        } else System.out.println("Delivery was cancel.");
    }

    public static IProducts getIProducts(Scanner scan, IProducts products) {
        int idKey = scan.nextInt();
        if (idKey <= MILK_PRODUCTS_LIST.size()) {
            products = MILK_PRODUCTS_LIST.get(idKey - 1);
        } else if (idKey <= (MILK_PRODUCTS_LIST.size() + MEAT_PRODUCTS_LIST.size())) {
            products = MEAT_PRODUCTS_LIST.get(idKey - MILK_PRODUCTS_LIST.size() - 1);
        }
        return products;
    }

    static void addProduct() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        MilkProduct product = context.getBean("milkProduct", MilkProduct.class);
        Scanner scan = new Scanner(System.in);
        System.out.println("\nEnter the product ID:");
        product.setId(scan.nextInt());
        System.out.println("Enter the name of product:");
        product.setName(scan.next());
        System.out.println("Enter the weight of product:");
        product.setWeight(scan.nextDouble());
        System.out.println("Enter the product cost:");
        product.setCost(scan.nextDouble());
        String print = Main.printTitle() + "\n" + Main.printBorder() + product + "\n" + Main.printBorder();
        System.out.println(print);
        System.out.println("\nSave product?\nEnter the product type (meat or milk):");
        Scanner scanner = new Scanner(System.in);
        String key = scanner.nextLine();
        if (key.equals("milk")) {
            CashRegister.putMilks(product);
            CashRegister.writeMilks();
            System.out.println(print);
            System.out.println("\nThis product was saved.");
        } else if (key.equals("meat")) {
            CashRegister.putMeats(product);
            CashRegister.writeMeats();
            System.out.println(print);
            System.out.println("\nThis product was saved.");
        } else {
            System.out.println(print);
            System.out.println("\nThis product was removed.");
        }
    }


    void readProd() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        Product products = context.getBean("milkProduct", MilkProduct.class);
        Product product1 = context.getBean("meatProduct", MeatProduct.class);
        products.readProducts();
        product1.readProducts();
    }

    static void cashRegisterFunctional() throws IOException {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        CashRegister cashRegister = context.getBean("cashRegister", CashRegister.class);
        readCass();
        if (cashInCass >= maxCashInCass) {
            cassToBank();
        }
        cashRegister.readProd();
        System.out.println("\n" + cashRegister);
        writeMilks();
        writeMeats();
        System.out.println("\nSelect the desired option:\n*001 - Add product to database;" +
                "\n*002 - Remove product from database;\n*003 - Show list of available products;" +
                "\n*004 - Buy products;\n*005 - Order delivery of product;\n*006 - Switch off cash register.");

        String option1 = "*001";
        String option2 = "*002";
        String option3 = "*003";
        String option4 = "*004";
        String option5 = "*005";
        String option6 = "*006";
        while (cashRegister.batteryCharge > 0) {
            Scanner scanner = new Scanner(System.in);
            String optionKey = scanner.next();
            if (optionKey.equals(option1)) {
                CashRegister.addProduct();
                writeMilks();
                writeMeats();
                cashRegister.batteryDischarge();
                cashRegister.printCharge();
            } else if (optionKey.equals(option2)) {
                remove();
                writeMilks();
                writeMeats();
                cashRegister.batteryDischarge();
                cashRegister.printCharge();
            } else if (optionKey.equals(option3)) {
                Main.printProducts();
                writeMilks();
                writeMeats();
                cashRegister.batteryDischarge();
                cashRegister.printCharge();
            } else if (optionKey.equals(option4)) {
                buyProducts();
                writeMilks();
                writeMeats();
                readCass();
                cashRegister.batteryDischarge();
                cashRegister.printCharge();
            } else if (optionKey.equals(option5)) {
                manualProductsDelivery();
                writeMilks();
                writeMeats();
                cashRegister.batteryDischarge();
                cashRegister.printCharge();
            } else if (optionKey.equals(option6)) {
                break;
            }
        }
        System.out.println("\nThe cash register has been switched off or the battery has been discharged!");
        context.close();
    }
}