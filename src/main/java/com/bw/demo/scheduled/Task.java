package com.bw.demo.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Task {
    @Scheduled(fixedRate = 1*1000)
    @Async
    public void task1(){
        log.info("======= " + Thread.currentThread().getName() + ": task 1 is finished. =======");
    }

    @Scheduled(fixedRate = 2*1000)
    @Async("taskScheduler")
    public void task2(){
        log.info("======= " + Thread.currentThread().getName() + ": task 2 is finished. =======");
    }

    @Scheduled(fixedRate = 3*1000)
    @Async("MyThreadPool")
    public void task3(){
        log.info("======= " + Thread.currentThread().getName() + ": task 3 is finished. =======");
    }
}
