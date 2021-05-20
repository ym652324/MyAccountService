package com.yang.ifsp.as.account.openAccount.processor;

import com.yang.ifsp.as.account.openAccount.db.dao.OpenAcctTxnInfoDOMapper;
import com.yang.ifsp.as.account.openAccount.db.model.OpenAcctTxnInfoDO;
import com.yang.ifsp.as.account.openAccount.util.CreateModelUtil;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DbProcessor {

    @Autowired
    OpenAcctTxnInfoDOMapper openAcctTxnInfoDOMapper;

    public int insertModel(OpenAccountReq req, OpenAcctTxnInfoDO openAcctTxnInfoDO){
        openAcctTxnInfoDO = CreateModelUtil.createModel(req, openAcctTxnInfoDO);
        return openAcctTxnInfoDOMapper.insert(openAcctTxnInfoDO);
    }

    public void updateModel(Object obj) {
        if(obj instanceof OpenAcctTxnInfoDO){
            OpenAcctTxnInfoDO openAcctTxnInfoDO = (OpenAcctTxnInfoDO)obj;
            openAcctTxnInfoDOMapper.updateByPrimaryKey(openAcctTxnInfoDO);
        }

    }
}
