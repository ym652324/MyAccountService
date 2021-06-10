package com.yang.ifsp.as.account.batch.task;


import com.yang.ifsp.as.account.batch.dao.BatchRecMapper;
import com.yang.ifsp.as.account.batch.model.CustInfoRecModel;
import com.yang.ifsp.as.account.openAccount.api.OpenAccountService;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountReq;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

@Service
public class CustInfoRecService {
    private static Logger logger = LoggerFactory.getLogger(CustInfoRecService.class);
    @Autowired
    private OpenAccountService openAccountService;

    @Autowired
    private BatchRecMapper batchRecMapper;

    @Async("batchTraceExecutor")
    public void sendCustInfoRec(CustInfoRecModel custInfoRecModel, CountDownLatch countDownLatch){
        OpenAccountReq acctSupplementReq = new OpenAccountReq();
        OpenAccountRes acctSupplementRes = new OpenAccountRes();
        String reqUid = UUID.randomUUID().toString().replace("_","");
        acctSupplementReq.setReqUID(reqUid);
        acctSupplementReq.setBindCard(custInfoRecModel.getBindCard());
        acctSupplementReq.setCustName(custInfoRecModel.getCustName());
        acctSupplementReq.seteAccount(custInfoRecModel.geteAccount());
        acctSupplementReq.setIdNo(custInfoRecModel.getIdNo());
        acctSupplementReq.setImage(custInfoRecModel.getImage());
        acctSupplementReq.setMobilePhone(custInfoRecModel.getMobilePhone());
        acctSupplementReq.setTranCode("ACCOUNT_00001");
        acctSupplementReq.setTranType("T0002");
        logger.info("发送联机补录接口请求对象："+acctSupplementReq);
        try{
            acctSupplementRes = openAccountService.doOpenAccount(acctSupplementReq);
            custInfoRecModel.setRespMsg(acctSupplementRes.getRespMsg());
            custInfoRecModel.setRespCode(acctSupplementRes.getRespCode());
        }catch (Exception e){
            logger.info("请求联机补录接口异常，流水号："+reqUid);
            custInfoRecModel.setRespMsg("【交易失败】");
            custInfoRecModel.setRespCode("[S91_1998]");
        }

        logger.info("获取联机补录接口响应对象："+acctSupplementRes);

        try{
            custInfoRecModel.setCreateTime(new Date());
            int flag = batchRecMapper.insertCustInfo(custInfoRecModel);
        }catch (Exception e){
            logger.error("插入文件记录至数据库异常");
        }
        finally {
            countDownLatch.countDown();
        }
    }

}
