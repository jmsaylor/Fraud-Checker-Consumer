package com.john;

public class TransactionUtility {
    public static String getAmount(String tx) {
        String[] fields = tx.split(",");
        String amount = fields[2].split("\\.")[0];
        return amount;
    }

    public static String getTopic(String tx) {
        String[] fields = tx.split(",");
        return fields[1].toLowerCase();
    }

}
