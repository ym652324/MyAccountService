package com.yang.ifsp.as.account.batch.component;

import com.yang.ifsp.as.account.batch.constant.CustInfoFileBatch;
import com.yang.ifsp.as.account.batch.job.CustInfoRecBatchConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@StepScope
@Qualifier(CustInfoRecBatchConfig.FSSTEPLISTENER)
public class CustSuppFSListener implements StepExecutionListener {

    private static Logger logger = LoggerFactory.getLogger(CustSuppFSListener.class);

    private ExecutionContext jobExecutionContext;

    @Autowired
    private CustInfoFileBatch custInfoFileBatch;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        jobExecutionContext = stepExecution.getJobExecution().getExecutionContext();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        String fileName = (String)jobExecutionContext.get(custInfoFileBatch.getSourceFileNameKey());
        File file = new File(custInfoFileBatch.getSourcePath()+fileName);
        if(file.exists()){
            file.delete();
            logger.info("删除源文件[{}]成功",fileName);
        }
        boolean existFlag = (boolean)jobExecutionContext.get(custInfoFileBatch.getFileExistFlagKey());
        if(existFlag){
            logger.info("文件获取成功，执行下一步");
            return new ExitStatus(CustInfoRecBatchConfig.FSSTEPRESULTSUCESS);
        }
        else{
            logger.info("文件获取失败，执行下一步");
            return new ExitStatus(CustInfoRecBatchConfig.FSSTEPRESULTFAIL);
        }
    }
}
