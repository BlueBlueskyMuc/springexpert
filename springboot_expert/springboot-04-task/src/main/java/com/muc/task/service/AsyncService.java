package com.muc.task.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    // 告诉Spring这是一个异步方法 使用前需要开启异步功能
    @Async
    public void task(){
        System.out.println("执行任务中...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("执行任务完成!");
    }
}
