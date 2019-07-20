package com.muc.task.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 定时任务
 */
@Service
public class SchedulingService {

    /**
     * second(秒) minute(分)  hour(时)  day of month(日), month(月) day of week(周几)
     *      * * * * * SUN-SAT
     *
     *      【0 0/5 14,18 * * ?】    每天14，18点整,每隔5分钟执行一次
     *      【0 15 10 ？ * 1-6】     每个月的周一至周六10:15分执行一次
     *      【0 0 2 ？ * 6L】        每个月的最后一个周六凌晨两点执行一次
     *      【0 0 2 LW * ?】         每个月的最后一个工作日凌晨两点执行一次
     *      【0 0 2-4 ? * 1#1】      每个月的第一个周一凌晨2点到4点期间,每个整点都执行一次
     *
     */
//    @Scheduled(cron = "0 * * * * SUN-SAT") // 星期一到星期五每 月,日,时,分 0秒执行
//    @Scheduled(cron = "0,1,2,3,4 * * * * SUN-SAT") // 星期一到星期五每 月,日,时,分 0,1,2,3,4秒执行
//    @Scheduled(cron = "0/4 * * * * 0-7") // 星期一到星期日每 月,日,时,分 每4秒执行
    public  void scheduling(){
        System.out.println("定时任务执行...");
    }
}
