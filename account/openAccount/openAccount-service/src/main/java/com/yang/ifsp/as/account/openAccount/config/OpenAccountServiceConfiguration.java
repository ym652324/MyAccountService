//package com.yang.ifsp.as.account.openAccount.openAccount.config;
//
//import com.yang.ifsp.common.redis.CacheReqUidService;
//import com.yang.ifsp.common.redis.CacheReqUidUtil;
//import com.yang.ifsp.common.redis.RedisClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class OpenAccountServiceConfiguration {
//
//    @Bean
//    public CacheReqUidUtil cacheReqUidUtil(@Autowired CacheReqUidService dbReqUidDao, @Qualifier("mainRedisClient")RedisClient redisClient){
//        return new CacheReqUidUtil(dbReqUidDao,redisClient);
//    }
//}
