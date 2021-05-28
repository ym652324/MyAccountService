package com.yang.ifsp.common.util;

import com.yang.ifsp.common.redis.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;


@Component
public class ReqUidUtil {
    private static Logger logger = LoggerFactory.getLogger(ReqUidUtil.class);

    @Autowired
    RedisService redisService;

    public boolean checkIsRepeat(String reqUid){
        if(StringUtils.isEmpty(redisService.get(reqUid))){
            Date nowData = new Date();
            String nowDateStr = (new SimpleDateFormat("yyMMdd")).format(nowData);

            if(!redisService.set(reqUid,nowDateStr)){
                logger.error("请求流水号插入redis失败");
                return false;
            }
            return true;
        }else{
            logger.error("请求流水号重复");
            return false;
        }
    }
}
