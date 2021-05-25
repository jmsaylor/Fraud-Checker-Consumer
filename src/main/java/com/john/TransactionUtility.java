package com.john;

public class TransactionUtility {
    public static String getAmount(String tx) {
        String[] fields = tx.split(",");
        String amount = fields[2];
        return amount;
    }

    public static String getTopic(String tx) {
        String[] fields = tx.split(",");
        return fields[1].toLowerCase();
    }

    public static Boolean isTransferOrCashOut(String transaction) {
        String[] fields = transaction.split(",");
        if (transaction.length() < 10 || fields.length < 1) return false;

        return fields[1].equals("TRANSFER") || fields[1].equals("CASH_OUT");
    }

    public static Boolean isTransfer(String tx) {
        String[] fields = tx.split(",");
        return fields[1].equals("TRANSFER");
    }

    public static Boolean isCashOut(String tx) {
        String[] fields = tx.split(",");
        return fields[1].equals("CASH_OUT");
    }

}
