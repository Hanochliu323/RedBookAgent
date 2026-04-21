package org.jeecg.modules.redbook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.redbook.entity.RbNoteDraft;
import org.jeecg.modules.redbook.entity.RbPublishPlan;
import org.jeecg.modules.redbook.model.req.RedbookIdRequest;
import org.jeecg.modules.redbook.service.IRbNoteDraftService;
import org.jeecg.modules.redbook.service.IRedbookWorkflowService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import jakarta.annotation.Resource;

@Tag(name = "RedBook笔记草稿")
@RestController
@RequestMapping("/redbook/noteDraft")
public class RbNoteDraftController extends RedbookCrudController<RbNoteDraft, IRbNoteDraftService> {
    @Resource
    private IRedbookWorkflowService redbookWorkflowService;

    @GetMapping(value = "/list")
    @Operation(summary = "笔记草稿分页列表")
    public Result<?> list(RbNoteDraft entity, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        return queryPageList(entity, pageNo, pageSize, req);
    }

    @PostMapping(value = "/add")
    @AutoLog(value = "新增笔记草稿")
    public Result<?> add(@RequestBody RbNoteDraft entity) {
        return saveEntity(entity);
    }

    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    @AutoLog(value = "编辑笔记草稿")
    public Result<?> edit(@RequestBody RbNoteDraft entity) {
        return updateEntity(entity);
    }

    @DeleteMapping(value = "/delete")
    @AutoLog(value = "删除笔记草稿")
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
    public ModelAndView exportXls(HttpServletRequest request, RbNoteDraft entity) {
        return exportExcel(request, entity, RbNoteDraft.class, "笔记草稿");
    }

    @PostMapping(value = "/importExcel")
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return importExcelData(request, response, RbNoteDraft.class);
    }

    @PostMapping(value = "/createPublishPlan")
    @AutoLog(value = "草稿加入发布计划")
    @Operation(summary = "基于草稿生成发布计划")
    public Result<RbPublishPlan> createPublishPlan(@RequestBody RedbookIdRequest request) {
        RbPublishPlan publishPlan = redbookWorkflowService.createPublishPlan(request.getId());
        return Result.OK("发布计划已生成", publishPlan);
    }
}
