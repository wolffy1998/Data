package com.mango.rumen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

/**
 * @Author mango
 * @Since 2020/2/1 17:13
 **/
@EnableScheduling
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {

    /**
     * 默认是单线程，把线程池数量设大点防止赌赛
     * @param taskRegistrar
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(5));
    }

}
