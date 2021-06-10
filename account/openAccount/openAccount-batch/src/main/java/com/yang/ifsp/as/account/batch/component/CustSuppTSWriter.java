package com.yang.ifsp.as.account.batch.component;


import com.yang.ifsp.as.account.batch.constant.CustInfoFileBatch;
import com.yang.ifsp.as.account.batch.job.CustInfoRecBatchConfig;
import com.yang.ifsp.as.account.batch.model.CustInfoRecModel;
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
import java.util.List;

@Component
@Qualifier(CustInfoRecBatchConfig.TSWRITER)
public class CustSuppTSWriter implements ItemWriter<CustInfoRecModel> {

    private static Logger logger = LoggerFactory.getLogger(CustSuppTSWriter.class);

    @Autowired
    private CustSuppTSListener custSuppTSListener;

    @Autowired
    private CustInfoFileBatch custInfoFileBatch;

    @Override
    public void write(List<? extends CustInfoRecModel> list) throws Exception {
        String recFileName = (String)custSuppTSListener.getJobExecutionContext().get(custInfoFileBatch.getSendFileNameKey());
        Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(custInfoFileBatch.getSendBakPath()+recFileName,true),"GBK"
        ));

        for(int i=0;i<=list.size()-1;i++){
            String error = list.get(i).getErrorData();
            if(!"".equals(error)){
                writer.write(list.get(i).getErrorData()+",");
                writer.write(list.get(i).getRespCode()+",");
                writer.write(list.get(i).getRespMsg()+"");
                writer.write("\n");
            }else{
                writer.write(list.get(i).getReqUid()+",");
                writer.write(list.get(i).getCustName()+",");
                writer.write(list.get(i).getMobilePhone()+",");
                writer.write(list.get(i).getBindCard()+",");
                writer.write(list.get(i).getIdNo()+",");
                writer.write(list.get(i).getImage()+",");
                writer.write(list.get(i).geteAccount()+",");
                writer.write(list.get(i).getRespCode()+",");
                writer.write(list.get(i).getRespMsg()+"");
                writer.write("\n");
            }
        }
        writer.flush();
        writer.close();
        logger.info("当前批次写入完成");
    }
}
