package com.example.enid.myapplication.dataS;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by big_love on 2016/11/15.
 */

public class Hash extends Activity{
    public static int additiveHash(String key,int prime){
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash += key.charAt(i);
        }
        return hash % prime;
    }
}

