package com.example.enid.myapplication.dataS;

import java.util.HashMap;

/**
 * Created by big_love on 2016/11/15.
 */

public class Hash {
    public static int additiveHash(String key,int prime){
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash += key.charAt(i);
        }
        return hash % prime;
    }
}
