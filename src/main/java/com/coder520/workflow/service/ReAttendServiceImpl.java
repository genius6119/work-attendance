//package com.coder520.workflow.service;/**
// * Created by wangjianbin on 2017/6/28.
// */
//
//import com.coder520.attend.dao.AttendMapper;
//import com.coder520.attend.entity.Attend;
//import com.coder520.workflow.dao.ReAttendMapper;
//import com.coder520.workflow.entity.ReAttend;
//import org.activiti.engine.RuntimeService;
//import org.activiti.engine.TaskService;
//import org.activiti.engine.runtime.ProcessInstance;
//import org.activiti.engine.task.Task;
//import org.apache.commons.collections.CollectionUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 补签实现类
// *
// * @author wangjianbin
// * @create 2017-06-28 17:23
// **/
//@Service
//public class ReAttendServiceImpl implements ReAttendService{
//
//
//    private static final String RE_ATTEND_FLOW_ID = "re_attend";
//
//    /**
//     * 补签流程状态
//     */
//    private static final Byte RE_ATTEND_STATUS_ONGOING =1 ;
//    private static final Byte RE_ATTEND_STATUS_PSSS =2 ;
//    private static final Byte RE_ATTEND_STATUS_REFUSE =3 ;
//    /**
//     *
//     */
//    private static final Byte ATTEND_STATUS_NORMAL =1 ;
//    /**
//     * 流程下一步处理人
//     */
//    private static final String NEXT_HANDLER = "next_handler";
//    /**
//     * 任务关联补签数据键
//     */
//    private static final String RE_ATTEND_SIGN = "re_attend";
//
//    @Autowired
//    private RuntimeService runtimeService;
//
//    @Autowired
//    private TaskService taskService;
//
//    @Autowired
//    private ReAttendMapper reAttendMapper;
//
//    @Autowired
//    private AttendMapper attendMapper;
//
//
//    /**
//     *@Author JackWang [www.coder520.com]
//     *@Date 2017/7/1 20:42
//     *@Description 开启补签工作流
//     */
//    @Override
//    @Transactional
//    public void startReAttendFlow(ReAttend reAttend){
//        //从公司组织架构中 查询到此人上级领导用户名
//        reAttend.setCurrentHandler("laowang666");
//        reAttend.setStatus(RE_ATTEND_STATUS_ONGOING);
//        //插入数据库补签表
//        reAttendMapper.insertSelective(reAttend);
//        Map<String,Object> map = new HashMap<>();
//        map.put(RE_ATTEND_SIGN,reAttend);
//        map.put(NEXT_HANDLER,reAttend.getCurrentHandler());
//       //启动补签流程实例
//        ProcessInstance instance = runtimeService.startProcessInstanceByKey(RE_ATTEND_FLOW_ID,map);
//        //提交用户补签任务
//        Task task = taskService.createTaskQuery().processInstanceId(instance.getId()).singleResult();
//        taskService.complete(task.getId(),map);
//    }
//
//
//    /**
//     *@Author JackWang [www.coder520.com]
//     *@Date 2017/7/1 20:42
//     *@Description 根据流程任务变量 查询某人需要处理的任务
//     */
//    @Override
//    public List<ReAttend> listTasks(String userName) {
//
//        List<ReAttend> reAttendList = new ArrayList<>();
//        List<Task> taskList= taskService.createTaskQuery().processVariableValueEquals(userName).list();
//        //转换成页面实体
//        if(CollectionUtils.isNotEmpty(taskList)){
//            for(Task task : taskList){
//                Map<String, Object> variable = taskService.getVariables(task.getId());
//                ReAttend reAttend = (ReAttend)variable.get(RE_ATTEND_SIGN);
//                reAttend.setTaskId(task.getId());
//                reAttendList.add(reAttend);
//            }
//        }
//        return reAttendList;
//    }
//
//
//    /**
//     *@Author JackWang [www.coder520.com]
//     *@Date 2017/7/1 20:42
//     *@Description 审批工作流
//     */
//    @Override
//    @Transactional
//    public void approve(ReAttend reAttend) {
//       Task task =  taskService.createTaskQuery().taskId(reAttend.getTaskId()).singleResult();
//
//       if((""+RE_ATTEND_STATUS_PSSS).equals(reAttend.getApproveFlag())){
//           //审批通过 修改补签数据状态
//           //修改相关考勤数据 考勤状态改为正常
//           Attend attend = new Attend();
//           attend.setId(reAttend.getAttendId());
//           attend.setAttendStatus(ATTEND_STATUS_NORMAL);
//           attendMapper.updateByPrimaryKeySelective(attend);
//           reAttend.setStatus(RE_ATTEND_STATUS_PSSS);
//           reAttendMapper.updateByPrimaryKeySelective(reAttend);
//       }else if((""+RE_ATTEND_STATUS_REFUSE).equals(reAttend.getApproveFlag())){
//           reAttend.setStatus(RE_ATTEND_STATUS_REFUSE);
//           reAttendMapper.updateByPrimaryKeySelective(reAttend);
//       }
//       taskService.complete(reAttend.getTaskId());
//    }
//
//
//    /**
//     *@Author JackWang [www.coder520.com]
//     *@Date 2017/7/1 20:42
//     *@Description 查询补签申请状态
//     * @param username
//     */
//    @Override
//    public List<ReAttend> listReAttend(String username) {
//        List<ReAttend> list =reAttendMapper.selectReAttendRecord(username);
//        return list;
//    }
//
//}
