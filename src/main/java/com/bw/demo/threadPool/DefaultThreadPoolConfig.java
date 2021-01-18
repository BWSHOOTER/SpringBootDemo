package com.bw.demo.threadPool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/** 修改默认线程池 */
@Slf4j
@EnableAsync
@Configuration
public class DefaultThreadPoolConfig implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程池大小
        executor.setCorePoolSize(10);
        //最大线程数
        executor.setMaxPoolSize(20);
        //队列容量
        executor.setQueueCapacity(20);
        //活跃时间
        executor.setKeepAliveSeconds(60);
        //线程名字前缀
        executor.setThreadNamePrefix("DiyThreadPool_");
        // 拒绝策略
        executor.setRejectedExecutionHandler(
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        // 停机是否等待任务
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 停机等待任务的最大时长
        executor.setAwaitTerminationSeconds(60);

        // 线程池初始化
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncUncaughtExceptionHandler(){
            @Override
            public void handleUncaughtException(Throwable throwable,
                                                Method method,
                                                Object... objects) {
                throwable.printStackTrace();
                log.error("AsyncError: {}, Method: {}",
                        throwable.getMessage(), method.getName());
            }
        };
    }
}
