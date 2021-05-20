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

import java.util.Date;


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
        dbProcessor.insertModel(req,openAcctTxnInfoDO);
//        if(dbProcessor.insertModel(req,openAcctTxnInfoDO)==0){
//            logger.error("请求报文入库失败,流水号：[{}]"+reqUid);
//            res.setRespCode(AccountEnums.SYSTEM_INNRT_ERROR.getRespCode());
//            res.setRespMsg(AccountEnums.SYSTEM_INNRT_ERROR.getRespMsg());
//            MakeMessage.makeOpenAcctCommonRes(req,res);
//            openAcctTxnInfoDO.setRespcode(res.getRespCode());
//            openAcctTxnInfoDO.setRespmsg(res.getRespMsg());
//            openAcctTxnInfoDO.setLastupdatetime(new Date());
//            openAcctTxnInfoDO.setLastoperate("请求报文入库");
//            dbProcessor.updateModel(openAcctTxnInfoDO);
//            return res;
//        }
        String tranCode = req.getTranCode();
        if("T0001".equals(tranCode)){
            logger.info("进入开户接口流程");

        }else if("T0002".equals(tranCode)){
            logger.info("进入补录接口流程");

        }else{
            logger.error("无效的交易码");
            res.setRespCode(AccountEnums.TRAN_CODE_ERROR.getRespCode());
            res.setRespMsg(AccountEnums.TRAN_CODE_ERROR.getRespMsg());
            openAcctTxnInfoDO.setLastoperate("请求报文入库");
            openAcctTxnInfoDO.setLastupdatetime(new Date());
            openAcctTxnInfoDO.setRespcode(res.getRespCode());
            openAcctTxnInfoDO.setRespmsg(res.getRespMsg());
        }

        dbProcessor.updateModel(openAcctTxnInfoDO);
        return res;
    }
}
