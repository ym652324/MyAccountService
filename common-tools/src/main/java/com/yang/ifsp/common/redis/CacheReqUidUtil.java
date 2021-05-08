//package com.yang.ifsp.common.redis;
//
//
//import org.junit.platform.commons.util.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import redis.clients.jedis.Jedis;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public class CacheReqUidUtil {
//
//    private static Logger logger = LoggerFactory.getLogger(CacheReqUidUtil.class);
//
//    private CacheReqUidService reqUidDao;
//    private RedisClient redisClient;
//
//
//    public Date compareReqUidWithResult(String reqUid){
//        if(!StringUtils.isBlank(reqUid)){
//            String cacheKey = reqUid;
//            Date nowDate = new Date();
//            String nowDateStr = (new SimpleDateFormat("yyMMdd").format(nowDate));
//
//            try {
//                if(this.reqUidDao != null){
//                    int res2 = this.reqUidDao.insertReqUid(cacheKey,nowDateStr);
//                    if(res2 == 0){
//                        return null;
//                    }
//                }
//            }catch (Exception var23){
//                logger.error("compareReqUidWithResult数据库操作失败",var23);
//                return null;
//            }
//
//            long res = 0L;
//
//            try{
//                Jedis jedis = this.redisClient.getRedis();
//                Throwable var9 = null;
//                try{
//                    res = jedis.setnx(cacheKey,nowDateStr);
//                    if(res != 0L){
//                        jedis.expire(cacheKey,864000);
//                    }
//                }catch (Throwable var20){
//                    var9 = var20;
//                    throw var20;
//                }finally {
//                    if(jedis != null){
//                        if(var9 != null){
//                            try{
//                                jedis.close();
//                            }catch (Throwable var19){
//                                var9.addSuppressed(var19);
//                            }
//                        }else {
//                            jedis.close();
//                        }
//                    }
//                }
//
//            }catch (Exception var22){
//                logger.warn("读取redis setnx失败"+this.redisClient.getServerLists());
//            }
//
//            return nowDate;
//
//
//        }else{
//            logger.error("传入的reqUid为空");
//            throw new RuntimeException("传入的reqUid为空");
//        }
//
//
//    }
//}
