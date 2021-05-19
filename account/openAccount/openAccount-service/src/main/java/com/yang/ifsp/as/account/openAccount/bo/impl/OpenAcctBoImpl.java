package com.yang.ifsp.as.account.openAccount.bo.impl;

import com.yang.ifsp.as.account.openAccount.bo.OpenAcctBo;
import com.yang.ifsp.as.account.openAccount.constants.AccountEnums;
import com.yang.ifsp.as.account.openAccount.db.model.OpenAcctTxnInfoDO;
import com.yang.ifsp.as.account.openAccount.processor.DbProcessor;
import com.yang.ifsp.as.account.openAccount.util.MakeMessage;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountReq;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OpenAcctBoImpl implements OpenAcctBo {

    private static Logger logger = LoggerFactory.getLogger(OpenAcctBoImpl.class);

    @Autowired
    DbProcessor dbProcessor;

    @Override
    public OpenAccountRes process(OpenAccountReq req) {
        OpenAccountRes res = new OpenAccountRes();
        String reqUid = req.getReqUID();
        OpenAcctTxnInfoDO openAcctTxnInfoDO = new OpenAcctTxnInfoDO();
        if(dbProcessor.insertModel(req,openAcctTxnInfoDO)==0){
            logger.error("请求报文入库失败,流水号：[{}]"+reqUid);
            res.setRespCode(AccountEnums.SYSTEM_INNRT_ERROR.getRespCode());
            res.setRespMsg(AccountEnums.SYSTEM_INNRT_ERROR.getRespMsg());
            MakeMessage.makeOpenAcctCommonRes(req,res);
            return res;
        }

        return res;
    }
}
