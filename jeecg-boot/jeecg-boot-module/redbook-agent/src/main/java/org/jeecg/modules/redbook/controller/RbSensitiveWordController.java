package org.jeecg.modules.redbook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.redbook.entity.RbSensitiveWord;
import org.jeecg.modules.redbook.service.IRbSensitiveWordService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Tag(name = "RedBook敏感词")
@RestController
@RequestMapping("/redbook/sensitiveWord")
public class RbSensitiveWordController extends RedbookCrudController<RbSensitiveWord, IRbSensitiveWordService> {
    @GetMapping(value = "/list")
    @Operation(summary = "敏感词分页列表")
    public Result<?> list(RbSensitiveWord entity, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        return queryPageList(entity, pageNo, pageSize, req);
    }

    @PostMapping(value = "/add")
    @AutoLog(value = "新增敏感词")
    public Result<?> add(@RequestBody RbSensitiveWord entity) {
        return saveEntity(entity);
    }

    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    @AutoLog(value = "编辑敏感词")
    public Result<?> edit(@RequestBody RbSensitiveWord entity) {
        return updateEntity(entity);
    }

    @DeleteMapping(value = "/delete")
    @AutoLog(value = "删除敏感词")
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
    public ModelAndView exportXls(HttpServletRequest request, RbSensitiveWord entity) {
        return exportExcel(request, entity, RbSensitiveWord.class, "敏感词");
    }

    @PostMapping(value = "/importExcel")
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return importExcelData(request, response, RbSensitiveWord.class);
    }
}
