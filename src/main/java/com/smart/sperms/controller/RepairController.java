package com.smart.sperms.controller;

import com.smart.sperms.request.RepairDelReq;
import com.smart.sperms.request.RepairEditReq;
import com.smart.sperms.response.CommonWrapper;
import com.smart.sperms.response.PageSearchWrapper;
import com.smart.sperms.response.SingleQueryWrapper;
import com.smart.sperms.service.RepairService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "repair", description = "设备维修")
@RestController
@RequestMapping(value = "repair")
public class RepairController {

    private static Logger logger = LoggerFactory.getLogger(RepairController.class);

    @Autowired
    private RepairService repairService;

    @ApiOperation(value = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="startTime", value = "开始时间", paramType = "form", dataType = "String"),
            @ApiImplicitParam(name="endTime", value = "结束时间", paramType = "form", dataType = "String"),
            @ApiImplicitParam(name="pageNo", value = "当前页", required = true, paramType = "form", dataType = "int"),
            @ApiImplicitParam(name="pageSize", value = "每页大小", required = true, paramType = "form", dataType = "int"),
            @ApiImplicitParam(name="keywords", value = "关键字", paramType = "form", dataType = "String")
    })
    @PostMapping(value = "/page_search")
    public PageSearchWrapper pageSearch(@RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime,
                                        @RequestParam int pageNo, @RequestParam int pageSize, @RequestParam(required = false) String keywords) {
        PageSearchWrapper wrapper = repairService.queryPage(pageNo, pageSize, startTime, endTime, keywords);

        logger.debug(" startTime = {}, endTime = {}, pageNo = {}, pageSize = {}, keywords = {}",
                new Object[]{startTime, endTime, pageNo, pageSize, keywords});
        return wrapper;
    }

    @ApiOperation(value = "新增设备维修信息")
    @PostMapping(value = "/add")
    public CommonWrapper add(@RequestBody RepairEditReq params) {
        logger.debug(" params = {}", new Object[]{params});
        CommonWrapper wrapper = repairService.addInfo(params);
        return wrapper;
    }

    @ApiOperation(value = "修改设备维修信息")
    @PostMapping(value = "/update")
    public CommonWrapper update(@RequestBody RepairEditReq params) {
        logger.debug(" params = {}", new Object[]{params});
        CommonWrapper wrapper = repairService.updateInfo(params);
        return wrapper;
    }

    @ApiOperation(value = "删除设备维修信息")
    @PostMapping(value = "/delete")
    public CommonWrapper delete(@RequestBody RepairDelReq params) {
        logger.debug(" params = {}", new Object[]{params});
        CommonWrapper wrapper = repairService.deleteInfo(params.geteIds());
        return wrapper;
    }

    @ApiOperation(value = "按ID查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="recordId", value = "记录ID", required = true, paramType = "form", dataType = "String")
    })
    @PostMapping(value = "/find_by_id")
    public SingleQueryWrapper findRecordById(@RequestParam String recordId) {
        SingleQueryWrapper wrapper = repairService.findRecordById(recordId);

        logger.debug(" recordId = {}",
                new Object[]{recordId});
        return wrapper;
    }
}
