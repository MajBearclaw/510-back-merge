package com.warmnut.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.warmnut.bean.log.AlarmHandleLog;
import com.warmnut.enumerate.LogSucceed;
import com.warmnut.log.LogManager;
import com.warmnut.log.LogTaskFactory;
import com.warmnut.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.warmnut.bean.log.AlarmLog;
import com.warmnut.dao.AlarmLogMapper;
import com.warmnut.enumerate.YgngError;
import com.warmnut.service.AlarmLogService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lupincheng
 * @version 创建时间：2020/12/18 17:15
 * 报警日志 服务层实现
 */
@Service("alarmLogService")
public class AlarmLogServiceImpl implements AlarmLogService{

    @Autowired
    private AlarmLogMapper alarmLogDao;

    /**
     * 添加报警日志
     * @param alarmLog 报警日志
     * @return 添加结果
     */
    @Override
    public SimpleResponse add(AlarmLog alarmLog) {
        SimpleResponse res = new SimpleResponse();
        try {
            int i = alarmLogDao.insertSelective(alarmLog);
            if(i > 0) {
                res.setErrorCode(YgngError.SUCCESS.value());
                res.setErrorMsg(YgngError.SUCCESS.getReasonPhrase());
            }else {
                res.setErrorCode(YgngError.PARAM_ERROR.value());
                res.setErrorMsg(YgngError.PARAM_ERROR.getReasonPhrase());
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public SimpleResponse deleteById(Integer id) {
        SimpleResponse res = new SimpleResponse();
        try {
            int i = alarmLogDao.deleteById(id);
            if(i > 0) {
                res.setErrorCode(YgngError.SUCCESS.value());
                res.setErrorMsg("删除日志成功");
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        null, "", "删除报警日志", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "删除报警日志", LogSucceed.SUCCESS, HttpKit.getIp())
                );// 保存操作日志
            }else {
                res.setErrorCode(YgngError.NO_DATA.value());
                res.setErrorMsg("不存在对应日志项");
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        null, "", "删除报警日志", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "删除报警日志", LogSucceed.FAIL, HttpKit.getIp())
                );// 保存操作日志
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
            LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                    null, "", "删除报警日志", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "删除报警日志", LogSucceed.FAIL, HttpKit.getIp())
            );// 保存操作日志
        }
        return res;
    }

    @Override
    public SimpleResponse deleteAll(ArrayList<Integer> idList) {
        SimpleResponse res = new SimpleResponse();
        try {
            int i = alarmLogDao.deleteAll(idList);
            if(i > 0) {
                res.setErrorCode(YgngError.SUCCESS.value());
                res.setErrorMsg("批量删除成功");
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        null, null, "批量删除报警日志", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "批量删除报警日志", LogSucceed.SUCCESS, HttpKit.getIp())
                );// 保存操作日志
            }else {
                res.setErrorCode(YgngError.NO_DATA.value());
                res.setErrorMsg("删除失败，未查找到对应的数据");
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        null, null, "批量删除报警日志", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "批量删除报警日志", LogSucceed.FAIL, HttpKit.getIp())
                );// 保存操作日志
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
            // 保存操作日志
            LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                    null, null, "批量删除报警日志", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "批量删除报警日志", LogSucceed.FAIL, HttpKit.getIp())
            );
        }
        return res;
    }

    /**
     * 获取报警日志列表
     * @param params 查询参数
     * @return 报警日志列表（包含状态信息）
     */
    @Override
    public DataResponse<PageInfo<AlarmLog>> selectAll(Map<String, Object> params) {
        // 设置分页参数: 页号，页大小
        int start,length;
        try{
            start = Integer.parseInt((String)params.get("start"));
            length = Integer.parseInt((String)params.get("length"));
        }catch (NumberFormatException e){
            start = 0;
            length = 10;
        }
        PageHelper.startPage(start, length);
        Page<AlarmLog> alarmLogList = alarmLogDao.selectAll(params);
        return new DataResponse<>(YgngError.SUCCESS.value(), "查询成功", alarmLogList.toPageInfo());
    }

    /**
     * 获取报警处理日志
     * @param id 报警处理日志id
     * @return 报警处理日志 or 错误信息
     */
    @Override
    public DataResponse<AlarmHandleLog> selectAlarmHandleLogById(Integer id) {
        AlarmHandleLog alarmHandleLog = alarmLogDao.selectAlarmHandleLogById(id);
        return new DataResponse<>(YgngError.SUCCESS.value(), "查询成功", alarmHandleLog);
    }

    /**
     * 添加报警处理日志
     * @param alarmLogIdList 被处理的报警日志的id列表
     * @param alarmHandleLog 报警处理日志
     * @return 报警处理日志 or 错误信息
     */
    @Override
    public SimpleResponse addAlarmHandleLog(ArrayList<Integer> alarmLogIdList, AlarmHandleLog alarmHandleLog) {
        int i = alarmLogDao.insertAlarmHandleLog(alarmHandleLog);
        if(i<1){
            LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                    alarmHandleLog.getUserId(), null, "添加报警处理日志", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "处理报警日志", LogSucceed.FAIL, HttpKit.getIp())
            );// 保存操作日志
            return new SimpleResponse(YgngError.UNKNOWN_ERROR.value(),"添加处理信息失败");
        }
        // 设置报警日志为已处理
        int j = alarmLogDao.setAlarmLogHandled(alarmLogIdList, alarmHandleLog.getId());
        if(j<=0){
            LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                    alarmHandleLog.getUserId(), null, "添加报警处理日志", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "处理报警日志", LogSucceed.FAIL, HttpKit.getIp())
            );// 保存操作日志
            return new SimpleResponse(YgngError.UNKNOWN_ERROR.value(),"设置报警日志为\"已处理\"失败");
        }
        LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                alarmHandleLog.getUserId(), null, "添加报警处理日志", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "处理报警日志", LogSucceed.SUCCESS, HttpKit.getIp())
        );// 保存操作日志
        return new SimpleResponse(YgngError.SUCCESS.value(),"添加处理信息成功");
    }
}
