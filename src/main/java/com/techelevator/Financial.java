package com.techelevator;

import java.math.BigDecimal;
import java.util.Scanner;

public class Financial {
    private double money = 0;
    private double total = 0;
    private Scanner in;

    public void setTotal(double total) {
        this.total = total;
    }

    public Financial(double money) {
        this.money = money;
    }

    public BigDecimal convertBD(double money) {
        BigDecimal bd = new BigDecimal(money);
        return bd;
    }

    public double amountDeposited() {
        BigDecimal bigDecimal = new BigDecimal(total);
        bigDecimal = convertBD(total);
        total = bigDecimal.doubleValue();
        return total;
    }

    public double totalMoney() {
        double funds = money + total;
        return funds;
    }

    public void feedMoney(double money) {
        total += money;
        System.out.println("Feed Money");
        String inputMoney = in.nextLine();
        double funds = Double.parseDouble(inputMoney);
        System.out.println("Current Money Provided: $%,.2d");
        
    }

}
