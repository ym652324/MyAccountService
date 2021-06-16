package com.yang.ifsp.common.core;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.util.DruidPasswordCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.util.Properties;

@Component
public class EncryptedPasswordCallback extends DruidPasswordCallback {
    private static final Logger logger = LoggerFactory.getLogger(EncryptedPasswordCallback.class);

    private static final long serialVersionUID = 8882903010694484738L;

    public EncryptedPasswordCallback() {
    }

    public void serProperties(Properties properties){
        super.setProperties(properties);
        String password = properties.getProperty("password");
        PublicKey publicKey = ConfigTools.getPublicKey(properties.getProperty("config.decrypt.key"));

        try{
            String dbpassword = ConfigTools.decrypt(publicKey,password);
            this.setPassword(dbpassword.toCharArray());
        }catch (Exception e){
            logger.error("数据库密码解密异常",e);
        }
    }
}
