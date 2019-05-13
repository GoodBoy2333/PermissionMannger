package com.fy.PermissionMannger.Scheduling;

import org.springframework.stereotype.Component;

/**
 * 〈〉
 *
 * @author fangyan
 * @create 2019/5/11
 * @since 1.0.0
 */
@Component
public class MyScheduling {
    int i=0;
    //@Scheduled(fixedDelay = 2000)
    public void scheduled() throws InterruptedException {
        System.out.println("执行了："+ ++i);
    }
}
