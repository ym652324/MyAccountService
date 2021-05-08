package com.yang.ifsp.as.account.openAccount.bo.impl;

import com.yang.ifsp.as.account.openAccount.bo.OpenAcctBo;
import com.yang.ifsp.as.account.openAccount.bo.vo.OpenAcctReqVo;
import com.yang.ifsp.as.account.openAccount.bo.vo.OpenAcctResVo;
import com.yang.ifsp.as.account.openAccount.model.OpenAccountTxnInfo;
import com.yang.ifsp.as.account.openAccount.processor.DbProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OpenAcctBoImpl implements OpenAcctBo {

    @Autowired
    DbProcessor dbProcessor;

    @Override
    public OpenAcctResVo process(OpenAcctReqVo reqVo) {
        OpenAcctResVo resVo = new OpenAcctResVo();
        String reqUid = reqVo.getReqUID();
        OpenAccountTxnInfo openAcctTxnInfo = dbProcessor.insertModel(reqVo,new OpenAccountTxnInfo());





        return null;
    }
}
