package main.java.com.momo.momoTalent2021.machine.controller;

import main.java.com.momo.momoTalent2021.enums.Coin;
import main.java.com.momo.momoTalent2021.exceptions.InvalidCoinException;
import main.java.com.momo.momoTalent2021.machine.Machine;
import main.java.com.momo.momoTalent2021.machine.inventory.ProductSpiral;
import main.java.com.momo.momoTalent2021.machine.dispenseStrategy.DispenseStrategy;

import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

public  abstract class Controller {

    protected Machine machine;

    Controller(Machine machine) {
        this.machine = machine;
    }

    public DispenseStrategy getDispenseStrategy() {
        return machine.getDispenseStrategy();
    }

    public void setDispenseStrategy(DispenseStrategy dispenseStrategy) {
        machine.setDispenseStrategy(dispenseStrategy);
    }

    public int computeTotalCost() {
        HashMap<ProductSpiral, Integer> selectedProducts = machine.getSelectedProducts();
        int totalCost = 0;
        for (ProductSpiral product: selectedProducts.keySet()) {
            totalCost += selectedProducts.get(product) * product.getProductType().getPrice();
        }
        return totalCost;
    }

    public boolean isAffordable(int cost) {
        return cost <= machine.getBudget();
    }


    public void displayGUI() {
        System.out.println("====================================================================");
        System.out.println("|                            Valid coins                           |");
        System.out.println("====================================================================");
        System.out.print("|    " +Coin.TEN_THOUSAND_VND.getValue()  + " (A) | ");
        System.out.print(Coin.TWENTY_THOUSAND_VND.getValue() + " (B) | ");
        System.out.print(Coin.FIFTY_THOUSAND_VND.getValue() + " (C) | " );
        System.out.print(Coin.ONE_HUNDRED_THOUSAND_VND.getValue() + " (D) | " );
        System.out.println(Coin.TWO_HUNDRED_THOUSAND_VND.getValue() + " (E)    |" );
        System.out.println("====================================================================");
        System.out.println("|                            Controller                            |");
        System.out.println("====================================================================");
        System.out.println("| Insert coin (I) | Dispense (Y) | Cancel (X) | Choose product (P) |");
        System.out.println("====================================================================");

    }

    public void insertCoin() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose your coin (A-E): ");
        String input = scanner.next();
        switch (input.toUpperCase(Locale.ROOT)) {
            case "A": {
                machine.setBudget(machine.getBudget() + Coin.TEN_THOUSAND_VND.getValue());
                break;
            } case "B": {
                machine.setBudget(machine.getBudget() + Coin.TWENTY_THOUSAND_VND.getValue());
                break;
            } case "C": {
                machine.setBudget(machine.getBudget() + Coin.FIFTY_THOUSAND_VND.getValue());
                break;
            } case "D": {
                machine.setBudget(machine.getBudget() + Coin.ONE_HUNDRED_THOUSAND_VND.getValue());
                break;
            } case "E": {
                machine.setBudget(machine.getBudget() + Coin.TWO_HUNDRED_THOUSAND_VND.getValue());
                break;
            } default:{
                throw new InvalidCoinException();
            }
        }
    }

    public void displayMessage() {
        System.out.println("|                          Message window                          |");
        System.out.println("====================================================================");
    }

    public abstract void cancelAndRefund();

    public abstract void confirmAndDispenseProduct();

    public abstract void selectProduct();
}
