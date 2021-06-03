package com.yang.ifsp.as.account.batch.component;

import com.yang.ifsp.as.account.batch.job.CustInfoRecBatchConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Component
@Qualifier(CustInfoRecBatchConfig.JOBLISTENER)
public class CustSuppJobListener implements JobExecutionListener {

    private static Logger logger = LoggerFactory.getLogger(CustInfoRecBatchConfig.class);

    private static final DateTimeFormatter dft = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSS");


    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("开始执行客户信息补录job");
        jobExecution.getExecutionContext().put("jobStartTime", LocalDateTime.now());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        logger.info("本次job执行完成");
        jobExecution.getExecutionContext().put("jobEndTime",LocalDateTime.now());
        LocalDateTime startTime = (LocalDateTime) jobExecution.getExecutionContext().get("jobStartTime");
        LocalDateTime endTime = (LocalDateTime) jobExecution.getExecutionContext().get("jobEndTime");
        logger.info("处理客户信息补录文件批量结束，共耗时[{}]s", ChronoUnit.SECONDS.between(startTime,endTime));
    }
}
