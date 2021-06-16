package com.yang.ifsp.as.account.batch.component;


import com.yang.ifsp.as.account.batch.constant.CustInfoFileBatch;
import com.yang.ifsp.as.account.batch.job.CustInfoRecBatchConfig;
import com.yang.ifsp.as.account.batch.model.CustInfoRecDO;
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
public class CustSuppTSWriter implements ItemWriter<CustInfoRecDO> {

    private static Logger logger = LoggerFactory.getLogger(CustSuppTSWriter.class);

    @Autowired
    private CustSuppTSListener custSuppTSListener;

    @Autowired
    private CustInfoFileBatch custInfoFileBatch;

    @Override
    public void write(List<? extends CustInfoRecDO> list) throws Exception {
        String recFileName = (String)custSuppTSListener.getJobExecutionContext().get(custInfoFileBatch.getSendFileNameKey());
        Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(custInfoFileBatch.getSendBakPath()+recFileName,true),"GBK"
        ));

        for(int i=0;i<=list.size()-1;i++){
            String error = list.get(i).getErrordata();
            if(!"".equals(error)){
                writer.write(list.get(i).getErrordata()+",");
                writer.write(list.get(i).getRespcode()+",");
                writer.write(list.get(i).getRespmsg()+"");
                writer.write("\n");
            }else{
                writer.write(list.get(i).getFid()+",");
                writer.write(list.get(i).getCustname()+",");
                writer.write(list.get(i).getMobilephone()+",");
                writer.write(list.get(i).getBindcard()+",");
                writer.write(list.get(i).getIdno()+",");
                writer.write(list.get(i).getImage()+",");
                writer.write(list.get(i).getEaccount()+",");
                writer.write(list.get(i).getRespcode()+",");
                writer.write(list.get(i).getRespmsg()+"");
                writer.write("\n");
            }
        }
        writer.flush();
        writer.close();
        logger.info("当前批次写入完成");
    }
}
