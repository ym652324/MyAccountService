package com.yang.ifsp.as.account.openAccount.processor;

import com.yang.ifsp.as.account.openAccount.bo.vo.OpenAcctReqVo;
import com.yang.ifsp.as.account.openAccount.util.CreateModelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DbProcessor {

    @Autowired
    OpenAcctTxnInfoDOMapper openAcctTxnInfoDOMapper;



    public OpenAcctTxnInfoDO insertModel(OpenAcctReqVo reqVo, OpenAcctTxnInfoDO openAcctTxnInfoDO){
        openAcctTxnInfoDO = CreateModelUtil.createModel(reqVo, openAcctTxnInfoDO);
        openAcctTxnInfoDOMapper.insert(openAcctTxnInfoDO);

        return openAcctTxnInfoDO;
    }
}
