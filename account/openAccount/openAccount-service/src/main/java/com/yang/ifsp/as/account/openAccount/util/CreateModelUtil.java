package com.yang.ifsp.as.account.openAccount.util;

import com.yang.ifsp.as.account.openAccount.bo.vo.OpenAcctReqVo;
import com.yang.ifsp.as.account.openAccount.db.model.OpenAcctTxnInfoDO;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class CreateModelUtil {

    public static OpenAcctTxnInfoDO createModel(OpenAcctReqVo reqVo, OpenAcctTxnInfoDO openAcctTxnInfoDO) {
        openAcctTxnInfoDO.setBindcard(reqVo.getBindCard());
        openAcctTxnInfoDO.setCreatetime(new Date());
        openAcctTxnInfoDO.setCustname(reqVo.getCustName());
        openAcctTxnInfoDO.setIdno(reqVo.getIdNo());
        openAcctTxnInfoDO.setMobilephone(reqVo.getMobilePhone());
        openAcctTxnInfoDO.setImagestatus(reqVo.getImageStatus());
        return openAcctTxnInfoDO;
    }
}
