package com.john;

import java.util.HashMap;

public class TransferLog {

    HashMap<String, String> log = new HashMap<>();

    public void add(String transaction) {
        log.put(TransactionUtility.getAmount(transaction), transaction);
    }

    public boolean check(String transaction) {
        return log.containsKey(TransactionUtility.getAmount(transaction));
    }

}
