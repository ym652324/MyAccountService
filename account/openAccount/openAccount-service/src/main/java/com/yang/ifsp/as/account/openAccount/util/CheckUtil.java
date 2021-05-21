package com.yang.ifsp.as.account.openAccount.util;

import com.yang.ifsp.as.account.openAccount.vo.OpenAccountReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.regex.Pattern;

public class CheckUtil {

    private static Logger logger = LoggerFactory.getLogger(CheckUtil.class);

    public static HashMap<String, String> check(HashMap<String,String> checkMap) {
        HashMap<String,String> errorMap = new HashMap<>();
        errorMap.put("0","true");

        if(checkMap.containsKey("custName")){
            if(!checkName(checkMap.get("custName"))){
                errorMap.put("0","false");
                errorMap.put("1","custName");
            }
        }

        if(checkMap.containsKey("idNo")){
            if(!checkIdNo(checkMap.get("idNo"))){
                errorMap.put("0","false");
                errorMap.put("1","idNo");
            }
        }

        if(checkMap.containsKey("mobilePhone")){
            if(!checkMobile(checkMap.get("mobilePhone"))){
                errorMap.put("0","false");
                errorMap.put("1","mobilePhone");
            }
        }

        if(checkMap.containsKey("bindCard")){
            if(!checkBindCard(checkMap.get("bindCard"))){
                errorMap.put("0","false");
                errorMap.put("1","bindCard");
            }
        }

        return errorMap;
    }

    private static boolean checkBindCard(String bindCard) {


        return true;


    }

    private static boolean checkMobile(String mobile) {
        String regStr = "^(1)\\d{10}$";
        return Pattern.matches(regStr,mobile);


    }

    private static boolean checkIdNo(String idNo) {
        if(idNo.length()<18){
            logger.error("身份证号码长度应为18");
            return false;
        }
        String subIdNo = idNo.substring(0,17);
        if(!checkNum(subIdNo)){
            logger.error("身份证号码前17位应为数字");
            return false;
        }




        return true;
    }

    private static boolean checkNum(String num) {
        String regStr = "[0-9]*";
        return Pattern.matches(regStr,num);
    }

    private static boolean checkName(String name) {
        String regStr = "^[\u4e00-\u9fa5]+([.•\u4e00-\u9fa5]+)*$";
        return Pattern.matches(regStr,name);

    }
}
