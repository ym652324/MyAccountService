package com.yang.ifsp.common.redis;


import org.junit.platform.commons.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CacheReqUidUtil {

    private static Logger logger = LoggerFactory.getLogger(CacheReqUidUtil.class);

    private CacheReqUidService reqUidDao;
    private RedisClient redisClient;


    public Date compareReqUidWithResult(String reqUid){
        if(!StringUtils.isBlank(reqUid)){
            String cacheKey = reqUid;
            Date nowDate = new Date();
            String noewDateStr = (new SimpleDateFormat("yyMMdd").format(nowDate));




        }


        return null;

    }
}
