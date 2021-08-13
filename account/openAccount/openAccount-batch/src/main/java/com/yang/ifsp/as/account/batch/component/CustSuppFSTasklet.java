package com.yang.ifsp.as.account.batch.component;

import com.yang.ifsp.as.account.batch.constant.CustInfoFileBatch;
import com.yang.ifsp.as.account.batch.dao.CustInfoRecDOMapper;
import com.yang.ifsp.as.account.batch.dao.RecFileNameDoMapper;
import com.yang.ifsp.as.account.batch.job.CustInfoRecBatchConfig;
import com.yang.ifsp.as.account.batch.model.RecFileNameDo;
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
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.util.Date;
import java.util.UUID;

@Component
@Qualifier(CustInfoRecBatchConfig.FSSTEPTASKLET)
@StepScope
public class CustSuppFSTasklet implements Tasklet {

    private static Logger logger = LoggerFactory.getLogger(CustSuppFSTasklet.class);

    @Autowired
    private CustInfoRecDOMapper custInfoRecDOMapper;

    @Autowired
    private RecFileNameDoMapper recFileNameDoMapper;

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
        if(file.isDirectory()){
            logger.info("发现文件夹，退出job");
            file.delete();
            return RepeatStatus.FINISHED;
        }

        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put(custInfoFileBatch.getSourceFileNameKey(),fileName);
        try{
            FileCopyUtils.copy(file,new File(custInfoFileBatch.getSourceBakPath()+fileName));
            logger.info("备份[{}]成功",fileName);
        }catch (Exception e){
            logger.error("复制文件出错！");
        }

        if(file.length() == 0){
            logger.info("空文件[{}]，准备退出job",fileName);
            return RepeatStatus.FINISHED;
        }

        if(!fileName.matches(custInfoFileBatch.getFileNamePattern())){
            logger.info("文件命名不规范[{}]，准备退出job",fileName);
            return RepeatStatus.FINISHED;
        }



        RecFileNameDo recFileNameDo = null;
        try{
            recFileNameDo = recFileNameDoMapper.selectByFileName(fileName);
        }catch (Exception e){
            logger.error("数据库查询异常");
            return RepeatStatus.FINISHED;
        }
        if(recFileNameDo != null){
            logger.info("数据库文件名为：[{}]",recFileNameDo.getFilename());
            logger.info("文件已经被解析，检查上次解析结果");
            if(recFileNameDo.getStatus() == 2){
                logger.info("上次解析成功，停止job");
            }else{
                logger.info("上次解析失败，停止job");
            }
            return RepeatStatus.FINISHED;
        }else{
            logger.info("+++++++++null+++++++++");
            recFileNameDo = new RecFileNameDo();
            recFileNameDo.setId(UUID.randomUUID().toString().replace("-",""));
            recFileNameDo.setFilename(fileName);
            recFileNameDo.setStatus(1);
            try{
                recFileNameDo.setCreatetime(new Date());
                recFileNameDoMapper.insertSelective(recFileNameDo);
            }catch (Exception e){
                logger.error("插入文件信息到数据库失败");
            }
        }

        String fileHead = fileName.split("_")[0];
        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put(custInfoFileBatch.getFileExistFlagKey(),true);
        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put(custInfoFileBatch.getFileHeadKey(),fileHead);
        return RepeatStatus.FINISHED;

    }
}
