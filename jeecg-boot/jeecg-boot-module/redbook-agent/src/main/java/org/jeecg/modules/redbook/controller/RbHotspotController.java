package org.jeecg.modules.redbook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.redbook.entity.RbHotspot;
import org.jeecg.modules.redbook.entity.RbHotspotAnalysis;
import org.jeecg.modules.redbook.model.req.RedbookIdRequest;
import org.jeecg.modules.redbook.service.IRbHotspotService;
import org.jeecg.modules.redbook.service.IRedbookWorkflowService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import jakarta.annotation.Resource;

@Tag(name = "RedBook热点池")
@RestController
@RequestMapping("/redbook/hotspot")
public class RbHotspotController extends RedbookCrudController<RbHotspot, IRbHotspotService> {
    @Resource
    private IRedbookWorkflowService redbookWorkflowService;

    @GetMapping(value = "/list")
    @Operation(summary = "热点分页列表")
    public Result<?> list(RbHotspot entity, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        return queryPageList(entity, pageNo, pageSize, req);
    }

    @PostMapping(value = "/add")
    @AutoLog(value = "新增热点")
    public Result<?> add(@RequestBody RbHotspot entity) {
        return saveEntity(entity);
    }

    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    @AutoLog(value = "编辑热点")
    public Result<?> edit(@RequestBody RbHotspot entity) {
        return updateEntity(entity);
    }

    @DeleteMapping(value = "/delete")
    @AutoLog(value = "删除热点")
    public Result<?> delete(@RequestParam(name = "id") String id) {
        return removeEntity(id);
    }

    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids") String ids) {
        return removeBatch(ids);
    }

    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id") String id) {
        return queryEntityById(id);
    }

    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, RbHotspot entity) {
        return exportExcel(request, entity, RbHotspot.class, "热点池");
    }

    @PostMapping(value = "/importExcel")
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return importExcelData(request, response, RbHotspot.class);
    }

    @PostMapping(value = "/analyze")
    @AutoLog(value = "热点一键分析")
    @Operation(summary = "基于热点生成选题分析")
    public Result<RbHotspotAnalysis> analyze(@RequestBody RedbookIdRequest request) {
        RbHotspotAnalysis analysis = redbookWorkflowService.analyzeHotspot(request.getId());
        return Result.OK("热点分析已生成", analysis);
    }
}
