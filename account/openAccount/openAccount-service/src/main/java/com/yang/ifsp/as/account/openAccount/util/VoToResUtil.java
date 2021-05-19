package com.yang.ifsp.as.account.openAccount.util;

import com.yang.ifsp.as.account.openAccount.bo.vo.OpenAcctReqVo;
import com.yang.ifsp.as.account.openAccount.bo.vo.OpenAcctResVo;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountReq;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountRes;

public class VoToResUtil {
    public static void openAcctVoToRes(OpenAccountReq req, OpenAcctResVo resVo, OpenAccountRes res){

        res.setReqUID(req.getReqUID());
        res.setTranCode(req.getTranCode());
        res.setIdNo(req.getIdNo());
        res.setCustName(req.getCustName());

        res.setRespMsg(resVo.getRespMsg());
        res.setRespCode(resVo.getRespCode());
        res.seteAccount(resVo.geteAccount());

    }
}
