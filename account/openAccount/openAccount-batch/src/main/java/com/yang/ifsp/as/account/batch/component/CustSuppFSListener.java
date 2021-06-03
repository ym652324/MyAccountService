package com.yang.ifsp.as.account.batch.component;

import com.yang.ifsp.as.account.batch.job.CustInfoRecBatchConfig;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@StepScope
@Qualifier(CustInfoRecBatchConfig.FSSTEPLISTENER)
public class CustSuppFSListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
