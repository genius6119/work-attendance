package com.coder520.common.task;

import com.coder520.attend.service.AttendService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Create By Zhang on 2017/11/18
 */
public class AttendCheckTask {
    /**
     * @Author Zhang
     * @Date 2017/11/18 10:04
     * @Description 首先获取没打卡的人，插入打卡记录，设置为缺勤480分钟，
     *              如果有打卡记录，检查早晚打卡，看看是否正常
     */
    @Autowired
    private AttendService attendService;
    public void checkAttend(){
        attendService.checkAttend();

    }
}
