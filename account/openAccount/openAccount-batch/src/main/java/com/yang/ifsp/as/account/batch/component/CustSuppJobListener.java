package com.yang.ifsp.as.account.batch.component;

import com.yang.ifsp.as.account.batch.job.CustInfoRecBatchConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@Qualifier(CustInfoRecBatchConfig.JOBLISTENER)
public class CustSuppJobListener implements JobExecutionListener {

    private static Logger logger = LoggerFactory.getLogger(CustInfoRecBatchConfig.class);

    private static final DateTimeFormatter dft = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSS");


    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

    }
}
