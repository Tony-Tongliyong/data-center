package com.tong.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import static java.util.concurrent.Executors.newScheduledThreadPool;

/**
 * @author: tongly
 * @contact:wuxin@yscredit.com
 * @file: ScheduleConfig
 * @time: 2019/1/19 9:54
 * @desc:
 */

@Configuration
public class ScheduleConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        taskRegistrar.setScheduler(newScheduledThreadPool(100));
    }
}

