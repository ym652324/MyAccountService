package com.yang.ifsp.as.account.batch.component;

import com.yang.ifsp.as.account.batch.constant.CustInfoFileBatch;
import com.yang.ifsp.as.account.batch.job.CustInfoRecBatchConfig;
import com.yang.ifsp.as.account.batch.model.CustInfoRecDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
@Qualifier(CustInfoRecBatchConfig.FSSTEPLINEMAPPER)
@StepScope
public class CustSuppSSLineMapper implements LineMapper {

    private static Logger logger = LoggerFactory.getLogger(CustSuppSSLineMapper.class);

    @Autowired
    private CustSuppSSListener custSuppSSListener;


    @Autowired
    private CustInfoFileBatch custInfoFileBatch;


    @Override
    public CustInfoRecDO mapLine(String s, int i) throws Exception {
        int lineNum = (int)custSuppSSListener.getJobExecutionContext().get(custInfoFileBatch.getReadLineNumKey());
        lineNum++;
        logger.info("当前读取第[{}]行",lineNum);
        custSuppSSListener.getJobExecutionContext().put(custInfoFileBatch.getReadLineNumKey(),lineNum);
        String fileName = (String)custSuppSSListener.getJobExecutionContext().get(custInfoFileBatch.getReadLineNumKey());
        CustInfoRecDO custInfoRecDO = new CustInfoRecDO();
        custInfoRecDO.setFilename(fileName);
        if(lineNum==1){
            if(s.startsWith("\uFEFF"));{
                s=s.replace("/uFEFF","");
            }
        }

        String[] lineArray = s.split(",",-1);
        Integer length = lineArray.length;
        if(length!=7){
            String rid = lineArray[0].trim();
            custInfoRecDO.setFid(rid);
            if(rid.equals("")){
                String uuid = UUID.randomUUID().toString().replace("-","");
                custInfoRecDO.setFid(uuid);
            }
            custInfoRecDO.setErrordata(s);
            custInfoRecDO.setRespcode("[S91_1998]");
            custInfoRecDO.setRespmsg("[交易失败，数据格式不正确]");
            return custInfoRecDO;
        }

        String rid = lineArray[0].trim();
        String custName = lineArray[1].trim();
        String mobile = lineArray[2].trim();
        String bindCard = lineArray[3].trim();
        String idNo = lineArray[4].trim();
        String image = lineArray[5].trim();
        String eAccount = lineArray[6].trim();

        custInfoRecDO.setFid(rid);
        custInfoRecDO.setCustname(custName);
        custInfoRecDO.setMobilephone(mobile);
        custInfoRecDO.setBindcard(bindCard);
        custInfoRecDO.setIdno(idNo);
        custInfoRecDO.setImage(image.getBytes());
        custInfoRecDO.setEaccount(eAccount);
        custInfoRecDO.setErrordata("");

        if("".equals(rid)||"".equals(eAccount)||"".equals(idNo)){
            if("".equals(rid)){
                String uuid = UUID.randomUUID().toString().replace("-","");
                custInfoRecDO.setFid(uuid);
            }
            custInfoRecDO.setErrordata(s);
            custInfoRecDO.setRespcode("[S91_1998]");
            custInfoRecDO.setRespmsg("[交易失败，缺少必输字段]");
            return custInfoRecDO;
        }

        if("".equals(custName)&&"".equals(mobile)&&"".equals(bindCard)&&"".equals(image)){
            custInfoRecDO.setErrordata(s);
            custInfoRecDO.setRespcode("[S91_1998]");
            custInfoRecDO.setRespmsg("[交易失败，缺少补录字段]");
            return custInfoRecDO;
        }



        return custInfoRecDO;
    }
}
