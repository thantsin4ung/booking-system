package com.tsa.dev.config;

import com.tsa.dev.quartzJob.AutoCancelBookingJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail autoCancelJobDetail() {
        return JobBuilder.newJob(AutoCancelBookingJob.class)
                .withIdentity("autoCancelJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger autoCancelTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(autoCancelJobDetail())
                .withIdentity("autoCancelTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/2 * * * ?")) // every 2 minutes
                .build();
    }
}