package com.coder520.attend.controller;



import com.coder520.attend.entity.Attend;
import com.coder520.attend.service.AttendService;
import com.coder520.attend.vo.QueryCondition;
import com.coder520.common.page.PageQueryBean;
import com.coder520.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Create By Zhang on 2017/11/9
 */
@Controller
@RequestMapping("attend")
public class AttendController {

    @Autowired
    private AttendService attendService;
    @RequestMapping
    public String toAttend(){
        return "attend";
    }

    @RequestMapping("/sign")
    @ResponseBody
    public String signAttend(@RequestBody Attend attend) throws Exception {
        attendService.signAttend(attend);
        return "succ";
    }


    /**
     * @Author Zhang
     * @Date 2017/11/14 18:43
     * @Description  考勤数据分页查询
     */
    @RequestMapping("/attendList")
    @ResponseBody
    public PageQueryBean listAttend(QueryCondition condition,HttpSession session){
        User user = (User) session.getAttribute("userinfo");
        String [] rangeDate = condition.getRangeDate().split("/");
        condition.setStartDate(rangeDate[0]);
        condition.setEndDate(rangeDate[1]);
        condition.setUserId(user.getId());
        PageQueryBean result = attendService.listAttend(condition);
        return result;
    }

}
