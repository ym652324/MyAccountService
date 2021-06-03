package com.yang.ifsp.as.account.batch.job;

import com.yang.ifsp.as.account.batch.component.CustSuppJobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustInfoRecBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CustSuppJobListener custSuppJobListener;



    private static Logger logger = LoggerFactory.getLogger(CustInfoRecBatchConfig.class);

    private static final String CUSTINFORECJOB = "getFileAndCheck";

    public static final String GETFILESNDCHECK = "saveToDb";

    public static final String SAVETODB = "saveToDb";

    public static final String SELECTFROMDB = "selectFromDbAndWriterFile";

    public static final String FSSTEPRESULTFALL = "GETFAIL";

    public static final String JOBLISTENER = "custInfoRecJobListener";


    @Bean(name = CUSTINFORECJOB)
    public Job getCustInfoRecJob(@Qualifier(GETFILESNDCHECK) Step getFileCheckStep,
                                 @Qualifier(SAVETODB) Step saveToDbStep,
                                 @Qualifier(SELECTFROMDB) Step selectFromDbStep){
        return jobBuilderFactory.get(CUSTINFORECJOB)
                .repository(jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(custSuppJobListener)
                .start(getFileCheckStep)
                .on(FSSTEPRESULTFALL).end()
                .from(getFileCheckStep).on(FSSTEPRESULTFALL).to(saveToDbStep)
                .next(selectFromDbStep)
                .end()
                .build();
    }
}
