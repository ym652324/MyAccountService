package com.yang.ifsp.as.account.openAccount.processor;

import com.yang.ifsp.as.account.openAccount.db.dao.AccountInfoDOMapper;
import com.yang.ifsp.as.account.openAccount.db.dao.OpenAcctTxnInfoDOMapper;
import com.yang.ifsp.as.account.openAccount.db.model.AccountInfoDO;
import com.yang.ifsp.as.account.openAccount.db.model.OpenAcctTxnInfoDO;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountReq;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountRes;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;


@Component
public class DbProcessor {

    @Autowired
    OpenAcctTxnInfoDOMapper openAcctTxnInfoDOMapper;

    @Autowired
    AccountInfoDOMapper accountInfoDOMapper;

    public int insertModel(Object obj,Object srcObj){
//        CreateModelUtil.createModel(srcObj, obj);
        if(obj instanceof OpenAcctTxnInfoDO){
            OpenAcctTxnInfoDO openAcctTxnInfoDO = (OpenAcctTxnInfoDO)obj;
            OpenAccountReq req = (OpenAccountReq)srcObj;
            openAcctTxnInfoDO.setRequid(req.getReqUID());
            openAcctTxnInfoDO.setBindcard(req.getBindCard());
            openAcctTxnInfoDO.setCreatetime(new Date());
            openAcctTxnInfoDO.setCustname(req.getCustName());
            openAcctTxnInfoDO.setIdno(req.getIdNo());
            openAcctTxnInfoDO.setMobilephone(req.getMobilePhone());
            openAcctTxnInfoDO.setImage(req.getImage());
            openAcctTxnInfoDO.setEaccount(req.geteAccount());
            openAcctTxnInfoDO.setTrantype(req.getTranType());
            openAcctTxnInfoDO.setLastoperate("校验报文及流水号");
            openAcctTxnInfoDO.setLastupdatetime(new Date());
            openAcctTxnInfoDO.setAccounttype(req.getAccountType());
            openAcctTxnInfoDO.setUsertype(req.getUserType());
            return openAcctTxnInfoDOMapper.insertSelective(openAcctTxnInfoDO);
        }
        if(obj instanceof AccountInfoDO){
            AccountInfoDO accountInfoDO = (AccountInfoDO) obj;
            OpenAcctTxnInfoDO openAcctTxnInfoDO = (OpenAcctTxnInfoDO) srcObj;
            accountInfoDO.setBindcard(openAcctTxnInfoDO.getBindcard());
            accountInfoDO.setEaccount(openAcctTxnInfoDO.getEaccount());
            accountInfoDO.setUserid(openAcctTxnInfoDO.getUserid());
            accountInfoDO.setIdno(openAcctTxnInfoDO.getIdno());
            accountInfoDO.setMoney(new BigDecimal(0));
            accountInfoDO.setAccounttype(openAcctTxnInfoDO.getAccounttype());
            accountInfoDO.setAccountstatus("S01");
            accountInfoDO.setImagestatus(openAcctTxnInfoDO.getImage()==null||openAcctTxnInfoDO.getImage().length==0?"M02":"M01");
            return accountInfoDOMapper.insertSelective(accountInfoDO);
        }

        return 0;
    }

    public void updateModel(Object obj,Object srcObj) {
        if(obj instanceof OpenAcctTxnInfoDO){
            OpenAcctTxnInfoDO openAcctTxnInfoDO = (OpenAcctTxnInfoDO)obj;
            OpenAccountRes openAccountRes = (OpenAccountRes)srcObj;
            openAccountRes.setRespDatetime(new Date());
            openAcctTxnInfoDO.setRespcode(openAccountRes.getRespCode());
            openAcctTxnInfoDO.setRespmsg(openAccountRes.getRespMsg());
            openAcctTxnInfoDO.setLastupdatetime(openAccountRes.getRespDatetime());
            openAcctTxnInfoDO.setEaccount(openAccountRes.geteAccount());
            openAcctTxnInfoDOMapper.updateByPrimaryKey(openAcctTxnInfoDO);
        }
        if(obj instanceof AccountInfoDO){
            AccountInfoDO accountInfoDO = (AccountInfoDO)obj;
            OpenAccountReq openAccountReq = (OpenAccountReq) srcObj;
            accountInfoDO.setLogpassword(openAccountReq.getLogPassword());
            accountInfoDO.setPaypassword(openAccountReq.getPayPassword());
            accountInfoDOMapper.updateByPrimaryKey(accountInfoDO);
        }
    }
}
