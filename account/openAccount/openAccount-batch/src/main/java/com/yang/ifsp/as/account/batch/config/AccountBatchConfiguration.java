package com.yang.ifsp.as.account.batch.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.yang.ifsp.common.core.EncryptedPasswordCallback;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = "com.yang.ifsp.as.account.batch.dao",sqlSessionTemplateRef = "SqlSessionTemplate2")
public class AccountBatchConfiguration {

    @Bean(name="DataSource2")
    @ConfigurationProperties(prefix = "spring.datasource.shard2")
    public DataSource druidDataSource2(@Autowired EncryptedPasswordCallback encryptedPasswordCallback){
        DruidDataSource dataSource = (DruidDataSource)DruidDataSourceBuilder.create().build();
        dataSource.setPasswordCallback(encryptedPasswordCallback);
        return dataSource;
    }

    @Bean(name="SqlSessionFactory2")
    public SqlSessionFactory sqlSessionFactory2(@Qualifier("DataSource2")DataSource dataSource)throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "SqlSessionTemplate2")
    public SqlSessionTemplate sqlSessionTemplate2(@Qualifier("SqlSessionFactory2") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean("transactionFactory2")
    public ManagedTransactionFactory transactionFactory(){
        ManagedTransactionFactory managedTransactionFactory = new ManagedTransactionFactory();
        Properties properties = new Properties();
        properties.setProperty("closeConnection","true");
        managedTransactionFactory.setProperties(properties);
        return managedTransactionFactory;
    }

}
