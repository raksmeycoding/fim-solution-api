package com.fimsolution.group.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@Configuration
public class AsyncConfig {

//    @Bean(name = "asyncExecutor")
//    public Executor asyncExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(2);
//        executor.setMaxPoolSize(4);
//        executor.setQueueCapacity(50);
//        executor.setThreadNamePrefix("AsyncThread-");
//        executor.setKeepAliveSeconds(60);
//        executor.setTaskDecorator(new MdcTaskDecorator());  // Apply the MDC decorator
//        executor.initialize();
//        return executor;
//    }


    @Bean(name = "fim-async-executor")
    public ThreadPoolTaskExecutor taskExecutor(TaskDecorator taskDecorator) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(8);
        executor.setQueueCapacity(100);
        executor.setTaskDecorator(taskDecorator);
        executor.setKeepAliveSeconds(60);
        executor.initialize();
        return executor;
    }
}
