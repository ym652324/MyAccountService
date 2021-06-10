package com.yang.ifsp.as.account.batch.component;


import com.yang.ifsp.as.account.batch.constant.CustInfoFileBatch;
import com.yang.ifsp.as.account.batch.dao.BatchRecMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Date;

@Component
@Qualifier("custInfoRecFileCompleteListener")
public class CustSuppTSListener implements StepExecutionListener {

    private static Logger logger = LoggerFactory.getLogger(CustSuppTSListener.class);

    @Autowired
    private BatchRecMapper batchRecMapper;

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
        String fileName = (String)jobExecutionContext.get(custInfoFileBatch.getSourceFileNameKey());
        jobExecutionContext.put(custInfoFileBatch.getSendFileNameKey(),"RESP_"+fileName);
        String recFileName = (String)jobExecutionContext.get(custInfoFileBatch.getSendFileNameKey());
        File file = new File(custInfoFileBatch.getSendBakPath()+recFileName);
        if(file.exists()){
            logger.info("上次文件存在，删除[{}]",recFileName);
            file.delete();
        }

        String systemName = System.getProperty("os.name").toLowerCase();
        logger.info("系统[{}]",systemName);
        if(systemName.indexOf("windows")!= -1){
            logger.info("WINDOWS系统");
            jobExecutionContext.put(custInfoFileBatch.getMvCmds(),"cmd exe /c move"+custInfoFileBatch.getTempPath()+"\\\\"+recFileName+"  "+custInfoFileBatch.getSendPath());
        }
        if(systemName.indexOf("linux")!= -1){
            logger.info("LINUX系统");
            jobExecutionContext.put(custInfoFileBatch.getMvCmds(),"mv"+custInfoFileBatch.getTempPath()+"/"+recFileName+"  "+custInfoFileBatch.getSendPath());
        }

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        String recFileName = (String)jobExecutionContext.get(custInfoFileBatch.getSendFileNameKey());
        String systemShell = (String)jobExecutionContext.get(custInfoFileBatch.getSystemShell());
        String cmd = (String)jobExecutionContext.get(custInfoFileBatch.getMvCmds());
        File f = new File(custInfoFileBatch.getSendBakPath()+recFileName);
        try{
            RandomAccessFile raf = new RandomAccessFile(f,"rws");
            long lo = raf.length();
            long index = lo-1;
            if(lo>0){
                raf.setLength(index);
            }
            raf.close();
            FileCopyUtils.copy(f,new File(custInfoFileBatch.getTempPath()+"/"+recFileName));
            logger.info("文件[{}]成功复制到[{}]",new Object[]{recFileName,custInfoFileBatch.getTempPath()});
            systemDemand(cmd,systemShell,recFileName);
            logger.info("["+custInfoFileBatch.getSendPath()+"]"+"回盘文件发送至JDP读取目录");
        }catch (Exception e){
            logger.error("生成回盘文件报错");

        }

        try{
            batchRecMapper.updateReadFile(new Date(),recFileName,2);
            logger.info("新文件写入成功修改文件状态");
        }catch (Exception e){
            logger.error("更新文件状态至数据库异常");
        }

        return ExitStatus.COMPLETED;
    }


    public void systemDemand(String cmd,String systemShell,String sendFileName){
        Runtime runtime = Runtime.getRuntime();
        Process process = null;
        int result = -1;
        try{
            if(systemShell!=null){
                logger.info("执行[{}]",systemShell+" "+cmd);
                String[] cmds = {systemShell,cmd};
                process = runtime.exec(cmds);
                result = process.waitFor();
            }else{
                logger.info("执行[{}]",cmd);
                process = runtime.exec(cmd);
                result = process.waitFor();
            }
            logger.info("[{}]文件，从[{}]移动到[{}]成功，命令返回结果[{}]",new Object[]{sendFileName,custInfoFileBatch.getTempPath(),custInfoFileBatch.getSendPath(),result});
        }catch (Exception e){
            logger.error("调用系统命令结果[{}]出错:[{}]",new Object[]{result,e});
        }finally {
            if(process!=null){
                process.destroy();
            }
        }

    }
}
