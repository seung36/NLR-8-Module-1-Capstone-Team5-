package com.techelevator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Financial {
    private double money = 0;
    private double total = 0;
    private Scanner in;
    private static String pattern = "MM-dd-yyyy hh:mm:ss a";
    private static File file;
    static {

        file = new File("Log.txt");
    }

    public static void setPattern(String pattern) {
        Financial.pattern = pattern;
    }

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

    public static void log(String event, String moneyAmt) {
        String date = formatTheDate(pattern);
        String str = date + " " + event + ": " + moneyAmt;
        appendToFile(file, str);
        //return str;
    }
    public static void log(Product product, String moneyAmt, String balance) {
        String date = formatTheDate(pattern);
        String str = date + " " + product.getProductName() + " " + product.getSlot() + " $" + moneyAmt + " $" + balance;
        appendToFile(file, str);
        //return str;
    }
    public static void log(String event, String moneyAmt, String balance) {
        String date = formatTheDate(pattern);
        String str = date + " " + event + ": " + "$" + moneyAmt + " $"+balance;
        appendToFile(file, str);
        //return str;
    }

    public static String formatTheDate(String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date());
    }

    public static void appendToFile(File newFile, String message){
        boolean append = newFile.exists() ? true : false;
        try (PrintWriter writer =
                     new PrintWriter(new FileOutputStream(newFile, append))) {
            writer.append(message + "\n");
        } catch(IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }

    }

}


