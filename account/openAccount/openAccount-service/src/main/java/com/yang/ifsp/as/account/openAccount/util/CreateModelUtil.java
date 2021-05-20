package com.yang.ifsp.as.account.openAccount.util;

import com.yang.ifsp.as.account.openAccount.db.model.OpenAcctTxnInfoDO;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountReq;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class CreateModelUtil {

    public static OpenAcctTxnInfoDO createModel(OpenAccountReq req, OpenAcctTxnInfoDO openAcctTxnInfoDO) {
        openAcctTxnInfoDO.setRequid(req.getReqUID());
        openAcctTxnInfoDO.setBindcard(req.getBindCard());
        openAcctTxnInfoDO.setCreatetime(new Date());
        openAcctTxnInfoDO.setCustname(req.getCustName());
        openAcctTxnInfoDO.setIdno(req.getIdNo());
        openAcctTxnInfoDO.setMobilephone(req.getMobilePhone());
        openAcctTxnInfoDO.setImage(req.getImage());
        openAcctTxnInfoDO.setEaccount(req.geteAccount());
        openAcctTxnInfoDO.setTrancode(req.getTranCode());
        openAcctTxnInfoDO.setLastoperate("校验报文及流水号");
        openAcctTxnInfoDO.setLastupdatetime(new Date());
        return openAcctTxnInfoDO;
    }
}
