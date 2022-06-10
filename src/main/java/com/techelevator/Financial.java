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

   public static String returnChange(String amt) {
       int moneyFeed = (int) (Double.valueOf(amt) * 100);
       int quarters = 0;
       int nickels = 0;
       int dimes = 0;
       while (moneyFeed > 0) {
           if (moneyFeed >= 25) {
               quarters++;
               moneyFeed -= 25;
           } else if (moneyFeed >= 10) {
               dimes++;
               moneyFeed -= 10;
           } else {
               if (moneyFeed >= 5) {
                   nickels++;
                   moneyFeed -= 5;
               }
           }
       }
       return "Change return: " + quarters + " quarters, " + dimes + " dimes, " + nickels + " nickels.";
       }


   }


