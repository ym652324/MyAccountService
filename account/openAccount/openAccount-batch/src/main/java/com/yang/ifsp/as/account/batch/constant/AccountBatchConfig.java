package com.yang.ifsp.as.account.batch.constant;

import org.springframework.stereotype.Component;

@Component
public class AccountBatchConfig {
    private String basePath;

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
