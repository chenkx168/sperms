package com.smart.sperms.service;

import com.smart.sperms.dao.EquipmentDao;
import com.smart.sperms.dao.RepairDao;
import com.smart.sperms.dao.dto.RepairDto;
import com.smart.sperms.dao.model.Repair;
import com.smart.sperms.enums.DevStateEnum;
import com.smart.sperms.enums.ResultCodeEnum;
import com.smart.sperms.request.RepairAddReq;
import com.smart.sperms.request.RepairEditReq;
import com.smart.sperms.response.CommonWrapper;
import com.smart.sperms.response.PageSearchWrapper;
import com.smart.sperms.response.SingleQueryWrapper;
import com.smart.sperms.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;

@Service
public class RepairService {

    @Autowired
    private RepairDao repairDao;

    @Autowired
    private EquipmentDao equipmentDao;

    /**
     * 新增记录
     * @param req
     * @return
     */
    @Transactional
    public CommonWrapper addInfo(RepairAddReq req) {
        CommonWrapper wrapper = new CommonWrapper();
        wrapper.setResultCode(ResultCodeEnum.FAILURE.getCode());

        String rId = UUID.randomUUID().toString();
        rId = rId.replaceAll("-","");

        Repair info = new Repair();
        info.seteId(req.geteId());
        info.setrManufacturer(req.getrManufacturer());
        info.setrCost(req.getrCost());
        info.setrPerson(req.getrPerson());
        info.setrState(req.getrState());
        info.setErrCode(req.getErrCode());
        info.setrTime(DateUtils.parseStrToDate(req.getrTime(), DateUtils.DEFAULT_FORMAT));
        info.setDownTime(DateUtils.parseStrToDate(req.getDownTime(), DateUtils.DEFAULT_FORMAT));

        int cnt = repairDao.saveData(info);
        if(cnt > 0) {
            equipmentDao.updateStateSingle(req.geteId(), String.valueOf(DevStateEnum.ERROR.getCode()));
            wrapper.setResultCode(ResultCodeEnum.SUCCESS.getCode());
            wrapper.setResultMsg(ResultCodeEnum.SUCCESS.getDesc());
        }
        return wrapper;
    }

    /**
     * 修改记录
     * @param req
     * @return
     */
    public CommonWrapper updateInfo(RepairEditReq req) {
        CommonWrapper wrapper = new CommonWrapper();
        wrapper.setResultCode(ResultCodeEnum.FAILURE.getCode());

        Repair info = new Repair();
        info.setrManufacturer(req.getrManufacturer());
        info.setrPerson(req.getrPerson());
        info.setrState(req.getrState());
        info.setrCost(req.getrCost());
        info.setErrCode(req.getErrCode());
        info.setrTime(DateUtils.parseStrToDate(req.getrTime(), DateUtils.DEFAULT_FORMAT));
        info.setDownTime(DateUtils.parseStrToDate(req.getDownTime(), DateUtils.DEFAULT_FORMAT));

        int cnt = repairDao.updateData(req.getId(), info);
        if(cnt > 0) {
            if("0".equals(req.getrState())) {
                Repair repair = repairDao.querySingle(req.getId());
                if(repair != null){
                    equipmentDao.updateStateSingle(repair.geteId(), String.valueOf(DevStateEnum.OFFLINE.getCode()));
                }
            }
            wrapper.setResultCode(ResultCodeEnum.SUCCESS.getCode());
            wrapper.setResultMsg(ResultCodeEnum.SUCCESS.getDesc());
        }
        return wrapper;
    }

    /**
     * 删除记录
     * @param rIds
     * @return
     */
    public CommonWrapper deleteInfo(List<Integer> rIds) {
        CommonWrapper wrapper = new CommonWrapper();
        wrapper.setResultCode(ResultCodeEnum.FAILURE.getCode());
        int cnt = repairDao.delData(rIds);

        wrapper.setResultCode(ResultCodeEnum.SUCCESS.getCode());
        wrapper.setResultMsg("成功删除【"+ cnt +"】条记录");

        return wrapper;
    }

    /**
     * 分页查询
     * @param pageNo    当前页
     * @param pageSize  每页大小
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param customerNo    客户编号
     * @param customerName  客户名称
     * @param eId       设备编号
     * @param eName     设备名称
     * @return
     */
    public PageSearchWrapper queryPage(int pageNo, int pageSize,
                                       String beginTime, String endTime,
                                       String customerNo, String customerName,
                                       String eId, String eName) {
        PageSearchWrapper wrapper = new PageSearchWrapper();

        int total = repairDao.queryPageTotal(beginTime, endTime, customerNo, customerName, eId, eName);
        List<RepairDto> result = repairDao.queryPage(pageNo, pageSize, beginTime, endTime, customerNo, customerName, eId, eName);

        wrapper.setTotalCount(total);
        wrapper.setPageNo(pageNo);
        wrapper.setRecords(result);
        wrapper.setResultCode(ResultCodeEnum.SUCCESS.getCode());

        return wrapper;
    }

    /**
     * 查询单个结果
     * @param recordId
     * @return
     */
    public SingleQueryWrapper findRecordById(Integer recordId) {
        SingleQueryWrapper wrapper = new SingleQueryWrapper();

        Repair condition = new Repair();
        condition.setId(recordId);

        List<Repair> result = repairDao.queryList(condition);
        if(!CollectionUtils.isEmpty(result)) {
            wrapper.setRecord(result.get(0));
        }
        wrapper.setResultCode(ResultCodeEnum.SUCCESS.getCode());

        return wrapper;
    }

    /**
     * 判断记录是否存在
     * @param eId
     * @return
     */
    private boolean isExists(String eId) {
        boolean isExist= false;
        Repair condition = new Repair();
        condition.seteId(eId);

        List<Repair> dataList = repairDao.queryList(condition);
        if(!CollectionUtils.isEmpty(dataList)) {
            isExist = true;
        }
        return isExist;
    }
}
