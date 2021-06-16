package com.yang.ifsp.as.account.batch.component;


import com.yang.ifsp.as.account.batch.dao.CustInfoRecDOMapper;
import com.yang.ifsp.as.account.batch.job.CustInfoRecBatchConfig;
import com.yang.ifsp.as.account.batch.model.CustInfoRecDO;
import com.yang.ifsp.as.account.batch.task.CustInfoRecService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
@Qualifier(CustInfoRecBatchConfig.SSWRITER)
public class CustSuppSSWriter implements ItemWriter<CustInfoRecDO> {

    private static Logger logger = LoggerFactory.getLogger(CustSuppSSWriter.class);


    @Autowired
    private CustInfoRecService custInfoRecService;

    @Autowired
    private CustInfoRecDOMapper custInfoRecDOMapper;


    @Override
    public void write(List<? extends CustInfoRecDO> list) throws Exception {
//        String fileHead = (String)custSuppSSListener.getJobExecutionContext().get(custInfoFileBatch.getFileHeadKey());
        CountDownLatch countDownLatch = new CountDownLatch(list.size());
        for(int i=0;i<=list.size()-1;i++){
            CustInfoRecDO custInfoRecDO=list.get(i);
            if(!"".equals(custInfoRecDO.getErrordata())){
                custInfoRecService.sendCustInfoRec(list.get(i),countDownLatch);
            }else{
               try{
                   custInfoRecDO.setArrivetime(new Date());
                   custInfoRecDOMapper.insertSelective(custInfoRecDO);
               }catch (Exception e){
                   logger.error("插入文件记录至数据库异常");
               }finally {
                   countDownLatch.countDown();
               }
            }
        }

        logger.info("主线程等待子线程执行结果");
        countDownLatch.await();
    }
}
