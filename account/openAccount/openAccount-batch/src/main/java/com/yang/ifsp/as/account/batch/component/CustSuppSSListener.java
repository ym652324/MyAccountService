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


@Component
@Qualifier(CustInfoRecBatchConfig.SSSTEPLISTENER)
@StepScope
public class CustSuppSSListener implements StepExecutionListener {

    private static Logger logger = LoggerFactory.getLogger(CustSuppSSListener.class);

    @Autowired
    private CustInfoFileBatch custInfoFileBatch;

    private ExecutionContext jobExecutionContext;

    public ExecutionContext getJobExecutionContext(){
        return jobExecutionContext;
    }

    public void setJobExecutionContext(ExecutionContext jobExecutionContext){
        this.jobExecutionContext = jobExecutionContext;
    }



    @Override
    public void beforeStep(StepExecution stepExecution) {
        jobExecutionContext = stepExecution.getJobExecution().getExecutionContext();
        jobExecutionContext.put(custInfoFileBatch.getReadLineNumKey(),0);
        logger.info("读取[{}]开始",jobExecutionContext.get(custInfoFileBatch.getSourceFileNameKey()));

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.info("读取[{}]结束",jobExecutionContext.get(custInfoFileBatch.getSourceFileNameKey()));

        return ExitStatus.COMPLETED;
    }
}
