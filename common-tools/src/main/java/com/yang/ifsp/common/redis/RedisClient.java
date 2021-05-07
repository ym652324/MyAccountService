package com.yang.ifsp.common.redis;

import org.apache.commons.lang3.StringUtils;


import redis.clients.jedis.*;
import redis.clients.util.Pool;

import java.util.*;

public class RedisClient {
    private int maxIdle;
    private int maxTotal;
    private long maxWaitMillis;
    private int connectTimeout;
    private String redisClusterType;
    private List<String> serverList;
    private String masterName;
    private String password;

    public static final String CLUSTER_SINGLE = "single";
    public static final String CLUSTER_CLUSTER = "cluster";
    public static final String CLUSTER_SENTINEL = "sentinel";


    private Pool<Jedis> pool;

    public RedisClient(int maxIdle,int maxTotal,long maxWaitMillis,int connectTimeout,String redisClusterType,String serverListString,String masterName,String password){
        if(maxIdle <= 0){
            boolean maxIdle1 = true;
        }else{
            this.maxIdle = maxIdle;
        }

        if(maxTotal <= 0){
            boolean maxTotal1 = true;
        }else{
            this.maxTotal = maxTotal;
        }

        if(maxWaitMillis <= 0L){
            maxWaitMillis = 5000L;
        }else{
            this.maxWaitMillis = maxWaitMillis;
        }

        if(connectTimeout <= 0){
            boolean connectTimeout1 = true;
        }else{
            this.connectTimeout = connectTimeout;
        }

        this.redisClusterType = redisClusterType;
        this.serverList = new ArrayList();
        if(StringUtils.isEmpty(serverListString)){
            throw new RuntimeException("空的redis服务器地址");
        }else{
            String[] servers = StringUtils.split(serverListString,",");
            this.serverList = Arrays.asList(servers);
            this.masterName = masterName;
            this.password = password;
            this.init();
        }
    }

    private void init() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(this.maxIdle);
        config.setMaxTotal(this.maxTotal);
        config.setMaxWaitMillis(this.maxWaitMillis);
        config.setTestOnBorrow(true);
        config.setTestOnCreate(true);
        if(StringUtils.isEmpty(this.redisClusterType)){
            this.redisClusterType = "single";
        }
        if("sentinel".equals(this.redisClusterType)){
            Set<String> serverSet = new HashSet(this.serverList);
            if(StringUtils.isEmpty(this.password)){
                this.pool = new JedisSentinelPool(this.masterName,serverSet,config,this.connectTimeout);
            }else{
                this.pool = new JedisSentinelPool(this.masterName,serverSet,config,this.connectTimeout,this.password);
            }
        }else{
            if("cluster".equals(this.redisClusterType)){
                throw new RuntimeException("暂未支持redis集群模式");
            }
            String server = (String)this.serverList.get(0);
            HostAndPort hap = HostAndPort.parseString(server);
            if(StringUtils.isEmpty(this.password)){
                this.pool = new JedisPool(config,hap.getHost(),hap.getPort(),this.connectTimeout);
            }else{

                this.pool = new JedisPool(config,hap.getHost(),hap.getPort(),this.connectTimeout,this.password);
            }
        }
    }

    public Jedis getRedis(){
        return (Jedis)this.pool.getResource();
    }

    public List<String> getServerList() {
        return serverList;
    }

    public String getMasterName() {
        return masterName;
    }

    public String getServerLists(){
        return String.join(",",this.serverList);
    }
}
