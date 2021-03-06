package com.warmnut.service.impl;

import com.warmnut.bean.Device;
import com.warmnut.bean.DeviceGroup;
import com.warmnut.dao.DeviceGroupMapper;
import com.warmnut.dao.DeviceMapper;
import com.warmnut.enumerate.LogSucceed;
import com.warmnut.enumerate.YgngError;
import com.warmnut.log.LogManager;
import com.warmnut.log.LogTaskFactory;
import com.warmnut.service.DeviceGroupService;
import com.warmnut.util.DataResponse;
import com.warmnut.util.HttpKit;
import com.warmnut.util.PageRequest;
import com.warmnut.util.SimpleResponse;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author  lupincheng
 * @version  创建时间：2020/12/9 18:24
 * 设备分组 服务层实现
 */
@Service("deviceGroupService")
public class DeviceGroupServiceImpl implements DeviceGroupService {

    @Autowired
    private DeviceGroupMapper deviceGroupDao;
    @Autowired
    private DeviceMapper deviceDao;

    public SimpleResponse add(DeviceGroup deviceGroup) {
        SimpleResponse res = new SimpleResponse();
        try {
            int i = deviceGroupDao.insertSelective(deviceGroup);
            if(i > 0) {
                res.setErrorCode(YgngError.SUCCESS.value());
                res.setErrorMsg(YgngError.SUCCESS.getReasonPhrase());
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        deviceGroup.getCreateUser(), null, "添加设备分组", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "添加设备分组", LogSucceed.SUCCESS, HttpKit.getIp())
                );// 保存操作日志
            }else {
                res.setErrorCode(YgngError.PARAM_ERROR.value());
                res.setErrorMsg(YgngError.PARAM_ERROR.getReasonPhrase());
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        deviceGroup.getCreateUser(), null, "添加设备分组", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "添加设备分组", LogSucceed.FAIL, HttpKit.getIp())
                );// 保存操作日志
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
            LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                    deviceGroup.getCreateUser(), null, "添加设备分组", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "添加设备分组", LogSucceed.FAIL, HttpKit.getIp())
            );// 保存操作日志
        }
        return res;
    }

    public SimpleResponse deleteById(Integer id) {
        SimpleResponse res = new SimpleResponse();
        try {
            int i = deviceGroupDao.deleteById(id);
            if(i > 0) {
                res.setErrorCode(YgngError.SUCCESS.value());
                res.setErrorMsg(YgngError.SUCCESS.getReasonPhrase());
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        null, null, "删除设备分组", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "删除设备分组", LogSucceed.SUCCESS, HttpKit.getIp())
                );// 保存操作日志
            }else {
                res.setErrorCode(YgngError.PARAM_ERROR.value());
                res.setErrorMsg(YgngError.PARAM_ERROR.getReasonPhrase());
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        null, null, "删除设备分组", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "删除设备分组", LogSucceed.FAIL, HttpKit.getIp())
                );// 保存操作日志
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
            LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                    null, null, "删除设备分组", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "删除设备分组", LogSucceed.FAIL, HttpKit.getIp())
            );// 保存操作日志
        }
        return res;
    }

    public SimpleResponse deleteAll(ArrayList<Integer> idList){
        SimpleResponse res = new SimpleResponse();
        try {
            int i = deviceGroupDao.deleteAll(idList);
            if(i > 0) {
                res.setErrorCode(YgngError.SUCCESS.value());
                res.setErrorMsg(YgngError.SUCCESS.getReasonPhrase());
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        null, null, "批量删除设备分组", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "批量删除设备分组", LogSucceed.SUCCESS, HttpKit.getIp())
                );// 保存操作日志
            }else {
                res.setErrorCode(YgngError.PARAM_ERROR.value());
                res.setErrorMsg(YgngError.PARAM_ERROR.getReasonPhrase());
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        null, null, "批量删除设备分组", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "批量删除设备分组", LogSucceed.FAIL, HttpKit.getIp())
                );// 保存操作日志
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
            LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                    null, null, "批量删除设备分组", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "批量删除设备分组", LogSucceed.FAIL, HttpKit.getIp())
            );// 保存操作日志
        }
        return res;
    }

    public SimpleResponse updateById(DeviceGroup deviceGroup) {
        SimpleResponse res = new SimpleResponse();
        try {
            deviceGroup.setUpdateTime(new Date());
            int i = deviceGroupDao.updateByPrimaryKeySelective(deviceGroup);
            if(i > 0) {
                res.setErrorCode(YgngError.SUCCESS.value());
                res.setErrorMsg(YgngError.SUCCESS.getReasonPhrase());
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        deviceGroup.getUpdateUser(), null, "修改设备分组", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "修改设备分组", LogSucceed.SUCCESS, HttpKit.getIp())
                );// 保存操作日志
            }else {
                res.setErrorCode(YgngError.PARAM_ERROR.value());
                res.setErrorMsg(YgngError.PARAM_ERROR.getReasonPhrase());
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        deviceGroup.getUpdateUser(), null, "修改设备分组", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "修改设备分组", LogSucceed.FAIL, HttpKit.getIp())
                );// 保存操作日志
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
            LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                    deviceGroup.getUpdateUser(), null, "修改设备分组", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "修改设备分组", LogSucceed.FAIL, HttpKit.getIp())
            );// 保存操作日志
        }
        return res;
    }

    public DataResponse<List<DeviceGroup>> selectAll(PageRequest params) {
        List<DeviceGroup> deviceGroupList = deviceGroupDao.selectAll(params);
        return new DataResponse<List<DeviceGroup>>(YgngError.SUCCESS.value(), "查询成功", deviceGroupList);
    }

    /**
     * 通过设备id获取设备所在的所有分组
     * @param deviceId 设备id
     * @return
     */
    public DataResponse<List<Integer>> selectByDeviceId(Integer deviceId){
        List<Integer> deviceGroupList = deviceGroupDao.selectByDeviceId(deviceId);
        return new DataResponse<List<Integer>>(YgngError.SUCCESS.value(), "查询成功", deviceGroupList);
    }

    /**
     * 获取分组与它的摄像头设备
     * @param params 查询参数
     * @return
     */
    public DataResponse<List<DeviceGroup>> selectWithDevices(PageRequest params) {
        List<DeviceGroup> deviceGroupList = deviceGroupDao.selectAll(params);
        for (DeviceGroup deviceGroup : deviceGroupList) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("groupId", deviceGroup.getId());
            map.put("type", "camera");
            deviceGroup.setDeviceList( deviceDao.selectAllCameras(map));
        }
        return new DataResponse<>(YgngError.SUCCESS.value(), "查询成功", deviceGroupList);
    }

    /**
     * 为多个设备分配多个分组。先删除设备原有的所有分组，再添加数据。
     * @param deviceIdList 设备id数组
     * @param groupIdList 分组id数组
     * @return 响应结果
     */
    @Override
    public SimpleResponse distributeDevicesToGroups(ArrayList<Integer> deviceIdList,
                                                    ArrayList<Integer> groupIdList,
                                                    Integer createUser) {
        SimpleResponse res = new SimpleResponse();
        try {
            // 移除设备原来所在分组
            int i = deviceGroupDao.removeRelation(deviceIdList);
            int j = 0;
            // 组id列表不为空才执行添加操作
            if( ! groupIdList.isEmpty()){
                j = deviceGroupDao.insertRelation(deviceIdList, groupIdList, createUser);
            }
            if( i>=0 && j>=0){
                res.setErrorCode(YgngError.SUCCESS.value());
                res.setErrorMsg(YgngError.SUCCESS.getReasonPhrase());
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        createUser, null, "分配分组", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "为设备分配分组", LogSucceed.SUCCESS, HttpKit.getIp())
                );// 保存操作日志
            }else {
                res.setErrorCode(YgngError.NO_DATA.value());
                res.setErrorMsg("不存在数据，请刷新后重试");
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        createUser, null, "分配分组", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "为设备分配分组", LogSucceed.FAIL, HttpKit.getIp())
                );// 保存操作日志
            }
        }catch (Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
            LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                    createUser, null, "分配分组", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "为设备分配分组", LogSucceed.FAIL, HttpKit.getIp())
            );// 保存操作日志
        }
        return res;
    }
}
