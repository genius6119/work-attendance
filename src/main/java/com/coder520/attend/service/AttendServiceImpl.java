package com.coder520.attend.service;

import com.coder520.attend.dao.AttendMapper;
import com.coder520.attend.entity.Attend;
import com.coder520.attend.vo.QueryCondition;
import com.coder520.common.page.PageQueryBean;
import com.coder520.common.utils.DateUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Create By Zhang on 2017/11/11
 */
@Service("attendServiceImpl")
public class AttendServiceImpl implements AttendService {

//    中午12点，判定上下午
    private static final int NOON_HOUR = 12;
    private static final int NOON_MINITE = 0;
    private Log log= LogFactory.getLog(AttendServiceImpl.class);

    //    格式化日期
    private SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
    @Autowired
    private AttendMapper attendMapper;



    /**
     * @Author Zhang
     * @Date 2017/11/11 12:53
     * @Description  打卡业务
     *  中午十二点前打卡都算早晨打卡，如果九点半以后直接异常，算迟到；
     *  十二点后打开都算下午打卡，十八点以前算异常，
     *  如果早上打了多次卡，算第一次
     *  如果下午打了多次卡，算最后一次
     *  检查下午打卡与上午打卡的时间差，不足八小时都算异常，并且将缺席时间存进去
     */
    @Override
    public void signAttend(Attend attend) throws Exception {
        try {
            Date today=new Date();
            attend.setAttendDate(today);
            attend.setAttendWeek((byte)DateUtils.getTodayWeek());
//            查询当天有无打卡记录
            Attend todayRecord=attendMapper.selectTodaySignRecord(attend.getUserId());

            Date moringAttend=DateUtils.getDate(9,30);

            Date noon= DateUtils.getDate(NOON_HOUR,NOON_MINITE);
            if(todayRecord==null){
//                如果打卡记录不存在
                if(today.compareTo(noon)<=0){
//                打卡时间小于12点 判断为早上打卡
                    attend.setAttendMoring(today);
                    if (today.compareTo(moringAttend)>0){
                        //打卡时间晚于9.30，迟到了
                        attend.setAttendStatus((byte) 2);
                        attend.setAbsence(DateUtils.getMinite(moringAttend,today));
                    }
                }else {
//                晚上打卡
                    attend.setAttendEvening(today);
                    //打卡时间早于18.00，早退
                    Date eveningAttend=DateUtils.getDate(18,00);
                    if (today.compareTo(eveningAttend)<0){
                        todayRecord.setAttendStatus((byte) 2);

                        todayRecord.setAbsence(DateUtils.getMinite(today,eveningAttend));
                    }else {
                        todayRecord.setAttendStatus((byte) 1);
                        todayRecord.setAbsence(0);
                    }
                    attendMapper.updateByPrimaryKeySelective(todayRecord);
                }
                attendMapper.insertSelective(attend);
            }else {
//                如果打卡记录存在
                if(today.compareTo(noon)<=0){
                    return;
                }else {
                    todayRecord.setAttendEvening(today);
                    attendMapper.updateByPrimaryKeySelective(todayRecord);
                }
            }
        }catch (Exception e){
            log.error("用户签到异常",e);
            throw e;
        }

    }

    @Override
    public PageQueryBean listAttend(QueryCondition condition) {
        //根据条件查询 count记录数目
        int count = attendMapper.countByCondition(condition);
        PageQueryBean pageResult = new PageQueryBean();
        if(count>0){
            pageResult.setTotalRows(count);
            pageResult.setCurrentPage(condition.getCurrentPage());
            pageResult.setPageSize(condition.getPageSize());
            List<Attend> attendList =  attendMapper.selectAttendPage(condition);
            pageResult.setItems(attendList);
        }
        //如果有记录 才去查询分页数据 没有相关记录数目 没必要去查分页数据
        return pageResult;
    }


    /**
     * @Author Zhang
     * @Date 2017/11/20 18:59
     * @Description 首先获取没打卡的人，插入打卡记录，设置为缺勤480分钟，
     *              如果有打卡记录，检查早晚打卡，看看是否正常
     */
    @Override
    @Transactional
    public void checkAttend(){
        List<Long> userIdList=attendMapper.selectTodayAbsence();
        if(CollectionUtils.isNotEmpty(userIdList)){
            List<Attend> attendList=new ArrayList<Attend>();
            for (Long userId:userIdList){
                Attend attend=new Attend();
                attend.setUserId(userId);
                attend.setAttendDate(new Date());
                attend.setAttendWeek((byte) DateUtils.getTodayWeek());
                attend.setAbsence(480);//缺席480分钟
                attend.setAttendStatus((byte) 2);//状态设置为异常
                attendList.add(attend);
             }
            attendMapper.batchInsert(attendList);
        }
        //检查晚打卡，将晚上未打卡记录设置为异常
        List<Attend> absenceList=attendMapper.selectTodayEveningAbsence();
        if(CollectionUtils.isNotEmpty(absenceList)){
            for (Attend attend:absenceList){
                attend.setAbsence(480);
                attend.setAttendStatus((byte) 2);
                attendMapper.updateByPrimaryKeySelective(attend);
            }
        }

    }


}
