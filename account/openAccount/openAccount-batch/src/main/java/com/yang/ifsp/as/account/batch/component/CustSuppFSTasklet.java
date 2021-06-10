package com.yang.ifsp.as.account.batch.component;

import com.yang.ifsp.as.account.batch.constant.CustInfoFileBatch;
import com.yang.ifsp.as.account.batch.dao.BatchRecMapper;
import com.yang.ifsp.as.account.batch.job.CustInfoRecBatchConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Qualifier(CustInfoRecBatchConfig.FSSTEPTASKLET)
@StepScope
public class CustSuppFSTasklet implements Tasklet {

    private static Logger logger = LoggerFactory.getLogger(CustSuppFSTasklet.class);

    @Autowired
    private BatchRecMapper batchRecMapper;

    @Autowired
    private CustInfoFileBatch custInfoFileBatch;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put(custInfoFileBatch.getFileExistFlagKey(),false);
        File f = new File(custInfoFileBatch.getSourcePath());
        if(!f.exists()){
            logger.error("源文件夹[{}]不存在！",custInfoFileBatch.getSourcePath());
            return RepeatStatus.FINISHED;
        }

        logger.info("读取源文件路径[{}]",custInfoFileBatch.getSourcePath());

        String names[]={};
        names = f.list();
        if(names == null || names.length == 0){
            logger.info("未发现补录文件，准备退出job");
            return RepeatStatus.FINISHED;
        }
        String fileName = names[0];
        logger.info("读取文件["+fileName+"]");
        File file = new File(custInfoFileBatch.getSourcePath()+fileName);





        return null;
    }
}
