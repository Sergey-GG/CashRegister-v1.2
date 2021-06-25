package vntu.fcsa.gonchar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * VNTU-FCSA
 * 1-ICT-20(b)
 * Gonchar Sergey
 **/

public class Main {
    /**
     * Print methods:
     **/
    static String readUsingBufferedReader() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(CashRegister.CHECKS));
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    static String printTitle() {
        String txtID = "ID";
        String txtName = "Name";
        String txtWeight = "Weight";
        String txtCost = "Cost";
        return String.format("\nProducts:\n%3s | %-20s | %6s | %5s", txtID, txtName, txtWeight, txtCost);
    }

    static String printBorder1() {
        return "]===========================================";
    }

    static String printBorder() {
        return "]===========================================[";
    }

    static void printProducts() {
        System.out.println(printTitle());
        System.out.print(printBorder1());
        System.out.println(CashRegister.getDataMilks());
        System.out.println(CashRegister.getDataMeats());
        System.out.println(printBorder());
        System.out.println();
    }

    public static void main(String[] args) {
        CashRegister.cashRegisterFunctional();
    }
}
