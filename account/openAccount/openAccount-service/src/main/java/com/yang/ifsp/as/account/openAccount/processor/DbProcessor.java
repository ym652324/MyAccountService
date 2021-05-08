package com.yang.ifsp.as.account.openAccount.processor;

import com.yang.ifsp.as.account.openAccount.bo.vo.OpenAcctReqVo;
import com.yang.ifsp.as.account.openAccount.model.OpenAccountTxnInfo;
import com.yang.ifsp.common.protocol.AbstractMSORes;
import org.springframework.stereotype.Component;


@Component
public class DbProcessor {

    public OpenAccountTxnInfo insertModel(OpenAcctReqVo reqVo,OpenAccountTxnInfo dataModel){


        return dataModel;
    }
}
