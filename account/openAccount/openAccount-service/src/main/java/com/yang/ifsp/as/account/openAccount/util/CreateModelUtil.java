package com.yang.ifsp.as.account.openAccount.util;

import com.yang.ifsp.as.account.openAccount.db.model.OpenAcctTxnInfoDO;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountReq;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class CreateModelUtil {

    public static OpenAcctTxnInfoDO createModel(OpenAccountReq req, OpenAcctTxnInfoDO openAcctTxnInfoDO) {
        openAcctTxnInfoDO.setBindcard(req.getBindCard());
        openAcctTxnInfoDO.setCreatetime(new Date());
        openAcctTxnInfoDO.setCustname(req.getCustName());
        openAcctTxnInfoDO.setIdno(req.getIdNo());
        openAcctTxnInfoDO.setMobilephone(req.getMobilePhone());
        openAcctTxnInfoDO.setImage(req.getImage());
        return openAcctTxnInfoDO;
    }
}
