package com.yang.ifsp.as.account.openAccount.util;

import com.yang.ifsp.as.account.openAccount.bo.vo.OpenAcctResVo;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountReq;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountRes;

import java.util.Date;

public class MakeMessage {


    public static void makeOpenAcctRes(OpenAccountReq openAccountReq, OpenAccountRes openAccountRes, OpenAcctResVo openAcctResVo) {
        //报文头
        openAccountRes.setReqUID(openAccountReq.getReqUID());
        openAccountRes.setTranCode(openAccountReq.getTranCode());
        openAccountRes.setRespDatetime(new Date());

        //报文体
        openAccountRes.setCustName(openAccountReq.getCustName());
        openAccountRes.setIdNo(openAccountReq.getIdNo());
        openAccountRes.setRespCode(openAcctResVo.getRespCode());
        openAccountRes.setRespMsg(openAcctResVo.getRespMsg());
    }
}
