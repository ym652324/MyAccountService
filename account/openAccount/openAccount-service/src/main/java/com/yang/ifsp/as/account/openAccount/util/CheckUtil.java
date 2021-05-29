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
                return errorMap;
            }
        }

        if(checkMap.containsKey("idNo")){
            if(!checkIdNo(checkMap.get("idNo"))){
                errorMap.put("0","false");
                errorMap.put("1","idNo");
                return errorMap;
            }
        }

        if(checkMap.containsKey("mobilePhone")){
            if(!checkMobile(checkMap.get("mobilePhone"))){
                errorMap.put("0","false");
                errorMap.put("1","mobilePhone");
                return errorMap;
            }
        }

        if(checkMap.containsKey("bindCard")){
            if(!checkBindCard(checkMap.get("bindCard"))){
                errorMap.put("0","false");
                errorMap.put("1","bindCard");
                return errorMap;
            }
        }

        return errorMap;
    }

    private static boolean checkBindCard(String bindCard) {

        if(!checkNum(bindCard)){
            logger.error("绑定卡号应为数字");
            return false;
        }
        return true;
    }

    private static boolean checkMobile(String mobile) {
        String regStr = "^(1)\\d{10}$";
        return Pattern.matches(regStr,mobile);
    }

    private static boolean checkIdNo(String idNo) {
        if(idNo.length() != 18){
            logger.error("身份证号码长度应为18");
            return false;
        }
        String subIdNo = idNo.substring(0,17);
        if(!checkNum(subIdNo)){
            logger.error("身份证号码前17位应为数字");
            return false;
        }
        String last = idNo.substring(idNo.length()-1);
        if(!(checkNum(last)||"x".equals(last)||"X".equals(last))){
            logger.error("身份证号码最后一位应为数字或X、x");
            return false;
        }
//        String last = idNo.substring(idNo.length()-1);
//        if(!("x".equals(last)||"X".equals(last))){
//            logger.error("身份证号最后一位应为x或X");
//            return false;
//        }

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
