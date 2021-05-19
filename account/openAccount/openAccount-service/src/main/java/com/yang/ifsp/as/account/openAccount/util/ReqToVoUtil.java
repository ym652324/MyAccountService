package com.yang.ifsp.as.account.openAccount.util;

import com.yang.ifsp.as.account.openAccount.bo.vo.OpenAcctReqVo;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountReq;

public class ReqToVoUtil {

    public static void openAcctReqToVo(OpenAccountReq req, OpenAcctReqVo reqVo){
        reqVo.setReqUID(req.getReqUID());
        reqVo.setTranCode(req.getTranCode());
        reqVo.setCustName(req.getCustName());
        reqVo.setBindCard(req.getBindCard());
        reqVo.setIdNo(req.getIdNo());
        reqVo.setMobilePhone(req.getMobilePhone());
        reqVo.setImageStatus(req.getImageStatus());

    }
}
