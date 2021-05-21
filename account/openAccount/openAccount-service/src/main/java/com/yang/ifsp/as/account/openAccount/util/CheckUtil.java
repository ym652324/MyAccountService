package com.yang.ifsp.as.account.openAccount.util;

import java.util.HashMap;
import java.util.HashSet;

public class CheckUtil {

    public static HashMap<String, String> check(HashSet<String> set) {
        HashMap<String,String> errorMap = new HashMap<String,String>();
        errorMap.put("0","true");
        if(set.contains("name")){

        }

        if(set.contains("idNo")){

        }

        if(set.contains("mobile")){

        }

        if(set.contains("bindCard")){

        }

        return errorMap;
    }
}
