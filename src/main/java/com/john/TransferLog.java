package com.john;

import java.util.HashMap;
import java.util.HashSet;

public class TransferLog {

    HashSet<String> set = new HashSet<>();

    public void add(String transaction) {
        set.add(TransactionUtility.getAmount(transaction));
    }

    public boolean check(String transaction) {
        return set.contains(TransactionUtility.getAmount(transaction));
    }

}
