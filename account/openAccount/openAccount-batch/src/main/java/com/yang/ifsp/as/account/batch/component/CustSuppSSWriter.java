package com.yang.ifsp.as.account.batch.component;

import com.yang.ifsp.as.account.batch.constant.CustInfoFileBatch;
import com.yang.ifsp.as.account.batch.dao.BatchRecMapper;
import com.yang.ifsp.as.account.batch.job.CustInfoRecBatchConfig;
import com.yang.ifsp.as.account.batch.model.CustInfoRecModel;
import com.yang.ifsp.as.account.batch.task.CustInfoRecService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
@Qualifier(CustInfoRecBatchConfig.SSWRITER)
public class CustSuppSSWriter implements ItemWriter<CustInfoRecModel> {

    private static Logger logger = LoggerFactory.getLogger(CustSuppSSWriter.class);

    @Autowired
    private CustSuppSSListener custSuppSSListener;

    @Autowired
    private CustInfoFileBatch custInfoFileBatch;

    @Autowired
    private CustInfoRecService custInfoRecService;

    @Autowired
    private BatchRecMapper batchRecMapper;


    @Override
    public void write(List<? extends CustInfoRecModel> list) throws Exception {
//        String fileHead = (String)custSuppSSListener.getJobExecutionContext().get(custInfoFileBatch.getFileHeadKey());
        CountDownLatch countDownLatch = new CountDownLatch(list.size());
        for(int i=0;i<=list.size()-1;i++){
            CustInfoRecModel custInfoRecModel=list.get(i);
            if(!"".equals(custInfoRecModel.getErrorData())){
                custInfoRecService.sendCustInfoRec(list.get(i),countDownLatch);
            }else{
               try{
                   custInfoRecModel.setCreateTime(new Date());
                   batchRecMapper.insertCustInfo(custInfoRecModel);
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
