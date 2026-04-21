package org.jeecg.modules.redbook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.redbook.entity.RbNoteMetric;
import org.jeecg.modules.redbook.entity.RbPublishPlan;
import org.jeecg.modules.redbook.model.req.RedbookIdRequest;
import org.jeecg.modules.redbook.service.IRbPublishPlanService;
import org.jeecg.modules.redbook.service.IRedbookWorkflowService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import jakarta.annotation.Resource;

@Tag(name = "RedBook发布计划")
@RestController
@RequestMapping("/redbook/publishPlan")
public class RbPublishPlanController extends RedbookCrudController<RbPublishPlan, IRbPublishPlanService> {
    @Resource
    private IRedbookWorkflowService redbookWorkflowService;

    @GetMapping(value = "/list")
    @Operation(summary = "发布计划分页列表")
    public Result<?> list(RbPublishPlan entity, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        return queryPageList(entity, pageNo, pageSize, req);
    }

    @PostMapping(value = "/add")
    @AutoLog(value = "新增发布计划")
    public Result<?> add(@RequestBody RbPublishPlan entity) {
        return saveEntity(entity);
    }

    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    @AutoLog(value = "编辑发布计划")
    public Result<?> edit(@RequestBody RbPublishPlan entity) {
        return updateEntity(entity);
    }

    @DeleteMapping(value = "/delete")
    @AutoLog(value = "删除发布计划")
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
    public ModelAndView exportXls(HttpServletRequest request, RbPublishPlan entity) {
        return exportExcel(request, entity, RbPublishPlan.class, "发布计划");
    }

    @PostMapping(value = "/importExcel")
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return importExcelData(request, response, RbPublishPlan.class);
    }

    @PostMapping(value = "/markPublished")
    @AutoLog(value = "发布计划标记已发布")
    @Operation(summary = "标记排期内容已发布")
    public Result<RbPublishPlan> markPublished(@RequestBody RedbookIdRequest request) {
        RbPublishPlan publishPlan = redbookWorkflowService.markPublished(request.getId());
        return Result.OK("发布计划已标记为已发布", publishPlan);
    }

    @PostMapping(value = "/createMetric")
    @AutoLog(value = "发布计划生成数据回收记录")
    @Operation(summary = "为已发布内容生成下一轮数据回收记录")
    public Result<RbNoteMetric> createMetric(@RequestBody RedbookIdRequest request) {
        RbNoteMetric metric = redbookWorkflowService.createMetricRecord(request.getId());
        return Result.OK("数据回收记录已生成", metric);
    }
}
