package com.yang.ifsp.as.account.batch.job;

import com.yang.ifsp.as.account.batch.component.*;
import com.yang.ifsp.as.account.batch.constant.CustInfoFileBatch;

import com.yang.ifsp.as.account.batch.model.CustInfoRecDO;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.util.HashMap;

@Configuration
public class CustInfoRecBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CustSuppJobListener custSuppJobListener;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private CustSuppSSListener custSuppSSListener;

    @Autowired
    private CustSuppTSListener custSuppTSListener;

    @Autowired
    private CustInfoFileBatch custInfoFileBatch;

    @Autowired
    private CustSuppSSLineMapper custSuppSSLineMapper;



    private static Logger logger = LoggerFactory.getLogger(CustInfoRecBatchConfig.class);

    private static final String CUSTINFORECJOB = "custInfoRecJob";

    public static final String GETFILESNDCHECK = "getFileAndCheck";

    public static final String SAVETODB = "saveToDb";

    public static final String SELECTFROMDB = "selectFromDbAndWriterFile";

    public static final String JOBLISTENER = "custInfoRecJobListener";

    public static final String FSSTEPTASKLET = "getCustInfoRecFileTasklet";

    public static final String FSSTEPLISTENER = "custInfoRecStepListener";

    public static final String FSSTEPRESULTSUCESS = "GETSUCCESS";

    public static final String FSSTEPRESULTFAIL = "GETFAIL";

    public static final String SSREADER = "custInfoFileReader";

    public static final String SSWRITER = "supplementWriter";

    public static final String TSREADER = "selectDbReader";

    public static final String TSWRITER = "sendFileWriter";

    public static final String SSSTEPLISTENER = "checkCustInfoFileListener";


    public static final String FSSTEPLINEMAPPER = "custInfoRecLineMapper";




    @Bean(name = CUSTINFORECJOB)
    public Job getCustInfoRecJob(@Qualifier(GETFILESNDCHECK) Step getFileCheckStep,
                                 @Qualifier(SAVETODB) Step saveToDbStep,
                                 @Qualifier(SELECTFROMDB) Step selectFromDbStep){
        return jobBuilderFactory.get(CUSTINFORECJOB)
                .repository(jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(custSuppJobListener)
                .start(getFileCheckStep)
                .on(FSSTEPRESULTFAIL).end()
                .from(getFileCheckStep).on(FSSTEPRESULTSUCESS).to(saveToDbStep)
                .next(selectFromDbStep)
                .end()
                .build();
    }


    //第一步获取文件并核对
    @Bean(name = GETFILESNDCHECK)
    public Step getCustInfoRecStep1(CustSuppFSTasklet custSuppFSTasklet, CustSuppFSListener custSuppFSListener){
        return stepBuilderFactory.get(GETFILESNDCHECK)
                .allowStartIfComplete(true)
                .tasklet(custSuppFSTasklet)
                .listener(custSuppFSListener)
                .build();

    }


    //第二步，异步发送请求并将返回结果入库
    @Bean(name = SAVETODB)
    public Step insertDataBaseStep2(@Qualifier(SSREADER)FlatFileItemReader flatFileItemReader,
                                    @Qualifier(SSWRITER)ItemWriter itemWriter
                                    ){
        return stepBuilderFactory.get(SAVETODB)
                .allowStartIfComplete(true)
                .listener(custSuppSSListener)
                .chunk(100)
                .reader(flatFileItemReader)
                .writer(itemWriter)
                .build();
    }


    //第三步，从数据库查询相应结果并且生成回盘文件、
    @Bean(name = SELECTFROMDB)
    public Step selectRecStep3(@Qualifier(TSREADER) ItemReader itemReader,
                               @Qualifier(TSWRITER)ItemWriter itemWriter
    ){
        return stepBuilderFactory.get(SELECTFROMDB)
                .allowStartIfComplete(true)
                .listener(custSuppTSListener)
                .chunk(1000)
                .reader(itemReader)
                .writer(itemWriter)
                .build();
    }


    //读取源文件
    @Bean(name = SSREADER)
    @StepScope
    public FlatFileItemReader<CustInfoRecDO> getItemReader(){
        String fileName = (String) custSuppSSListener.getJobExecutionContext().get(custInfoFileBatch.getSourceFileNameKey());
        FlatFileItemReader<CustInfoRecDO> flatFileItemReader = new FlatFileItemReader<>();
        FileSystemResource fr = new FileSystemResource(custInfoFileBatch.getSourceBakPath()+fileName);
        logger.info("读取文件路径是"+fileName);
        flatFileItemReader.setEncoding("UTF-8");
        flatFileItemReader.setResource(fr);
        flatFileItemReader.setLineMapper(custSuppSSLineMapper);
        return  flatFileItemReader;


    }

    //数据库读取结果信息
    @Bean(name = TSREADER)
    @StepScope
    public MyBatisCursorItemReader selectFromDB(@Qualifier("SqlSessionFactory2")SqlSessionFactory sqlSessionFactory){
        String fileName = (String) custSuppTSListener.getJobExecutionContext().get(custInfoFileBatch.getSourceFileNameKey());
        MyBatisCursorItemReader myBatisCursorItemReader = new MyBatisCursorItemReader();
        myBatisCursorItemReader.setSqlSessionFactory(sqlSessionFactory);
        HashMap m = new HashMap<>();
        m.put("fileName",fileName);
        logger.info("带入文件名查询"+m.get("fileName"));

        myBatisCursorItemReader.setQueryId("com.yang.ifsp.as.account.batch.dao.BatchRecMapper.selectCustInfo");
        myBatisCursorItemReader.setParameterValues(m);

        logger.info("数据库查询完成");

        return  myBatisCursorItemReader;


    }
}
