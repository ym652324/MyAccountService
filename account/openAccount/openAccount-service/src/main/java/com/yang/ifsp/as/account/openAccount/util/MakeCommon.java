package com.yang.ifsp.as.account.openAccount.util;

import com.yang.ifsp.as.account.openAccount.db.model.OpenAcctTxnInfoDO;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountReq;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountRes;

import java.util.Date;

public class MakeCommon {


    public static void makeOpenAcctCommonRes(OpenAccountReq openAccountReq, OpenAccountRes openAccountRes) {
        //报文头
        openAccountRes.setReqUID(openAccountReq.getReqUID());
        openAccountRes.setTranCode(openAccountReq.getTranCode());
        openAccountRes.setRespDatetime(new Date());

        //报文体
        openAccountRes.setCustName(openAccountReq.getCustName());
        openAccountRes.setIdNo(openAccountReq.getIdNo());
        openAccountRes.setTranType(openAccountReq.getTranType());
    }

    public static void makeOpenAcctInsertComm(OpenAcctTxnInfoDO openAcctTxnInfoDO, OpenAccountReq req) {
        openAcctTxnInfoDO.setRequid(req.getReqUID());
        openAcctTxnInfoDO.setBindcard(req.getBindCard());
        openAcctTxnInfoDO.setCreatetime(new Date());
        openAcctTxnInfoDO.setCustname(req.getCustName());
        openAcctTxnInfoDO.setIdno(req.getIdNo());
        openAcctTxnInfoDO.setMobilephone(req.getMobilePhone());
        openAcctTxnInfoDO.setImage(req.getImage());
        openAcctTxnInfoDO.setEaccount(req.geteAccount());
        openAcctTxnInfoDO.setLastoperate("校验报文及流水号");
        openAcctTxnInfoDO.setLastupdatetime(new Date());
    }

    public static void makeOpenAcctUpdateComm(OpenAcctTxnInfoDO openAcctTxnInfoDO, OpenAccountRes openAccountRes) {
        openAccountRes.setRespDatetime(new Date());
        openAcctTxnInfoDO.setRespcode(openAccountRes.getRespCode());
        openAcctTxnInfoDO.setRespmsg(openAccountRes.getRespMsg());
        openAcctTxnInfoDO.setLastupdatetime(openAccountRes.getRespDatetime());
    }
}
