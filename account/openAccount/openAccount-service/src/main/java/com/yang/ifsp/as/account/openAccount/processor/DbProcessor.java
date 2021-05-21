package com.yang.ifsp.as.account.openAccount.processor;

import com.yang.ifsp.as.account.openAccount.db.dao.OpenAcctTxnInfoDOMapper;
import com.yang.ifsp.as.account.openAccount.db.model.OpenAcctTxnInfoDO;
import com.yang.ifsp.as.account.openAccount.util.CreateModelUtil;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountReq;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class DbProcessor {

    @Autowired
    OpenAcctTxnInfoDOMapper openAcctTxnInfoDOMapper;

    public int insertModel(OpenAccountReq req, OpenAcctTxnInfoDO openAcctTxnInfoDO){
        openAcctTxnInfoDO = CreateModelUtil.createModel(req, openAcctTxnInfoDO);
        return openAcctTxnInfoDOMapper.insert(openAcctTxnInfoDO);
    }

    public void updateModel(Object obj,Object res) {
        if(obj instanceof OpenAcctTxnInfoDO){
            OpenAcctTxnInfoDO openAcctTxnInfoDO = (OpenAcctTxnInfoDO)obj;
            OpenAccountRes openAccountRes = (OpenAccountRes)res;
            openAccountRes.setRespDatetime(new Date());
            openAcctTxnInfoDO.setRespcode(openAccountRes.getRespCode());
            openAcctTxnInfoDO.setRespmsg(openAccountRes.getRespMsg());
            openAcctTxnInfoDO.setLastupdatetime(openAccountRes.getRespDatetime());
            openAcctTxnInfoDOMapper.updateByPrimaryKey(openAcctTxnInfoDO);
        }

    }
}
