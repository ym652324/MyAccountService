package com.yang.ifsp.common.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ValidatorUtil {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private ValidatorUtil() {throw new IllegalAccessError("Utility class");}

    public static <T> Map<String,StringBuilder> validate(T obj) {
        Map<String,StringBuilder> errorMap = null;
        Set<ConstraintViolation<T>> set = validator.validate(obj,new Class[]{Default.class});
        if(set != null && set.size() > 0){
            errorMap = new HashMap<>();
            String property = "";
            Iterator var4 = set.iterator();

            while (var4.hasNext()){
                ConstraintViolation<T> cv = (ConstraintViolation)var4.next();
                property = cv.getPropertyPath().toString();
                if(errorMap.get(property)!= null){
                    ((StringBuilder)errorMap.get(property)).append(","+cv.getMessage());
                }else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(cv.getMessage());
                    errorMap.put(property,sb);
                }
            }
        }

        return errorMap;

    }
}
