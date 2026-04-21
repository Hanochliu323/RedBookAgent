package org.jeecg.modules.redbook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.redbook.entity.RbNoteMetric;
import org.jeecg.modules.redbook.service.IRbNoteMetricService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Tag(name = "RedBook数据回收")
@RestController
@RequestMapping("/redbook/noteMetric")
public class RbNoteMetricController extends RedbookCrudController<RbNoteMetric, IRbNoteMetricService> {
    @GetMapping(value = "/list")
    @Operation(summary = "笔记数据分页列表")
    public Result<?> list(RbNoteMetric entity, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        return queryPageList(entity, pageNo, pageSize, req);
    }

    @PostMapping(value = "/add")
    @AutoLog(value = "新增笔记数据")
    public Result<?> add(@RequestBody RbNoteMetric entity) {
        return saveEntity(service.normalizeMetric(entity));
    }

    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    @AutoLog(value = "编辑笔记数据")
    public Result<?> edit(@RequestBody RbNoteMetric entity) {
        return updateEntity(service.normalizeMetric(entity));
    }

    @DeleteMapping(value = "/delete")
    @AutoLog(value = "删除笔记数据")
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
    public ModelAndView exportXls(HttpServletRequest request, RbNoteMetric entity) {
        return exportExcel(request, entity, RbNoteMetric.class, "笔记数据回收");
    }

    @PostMapping(value = "/importExcel")
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return importExcelData(request, response, RbNoteMetric.class);
    }
}
