package org.jeecg.modules.redbook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.redbook.entity.RbAccount;
import org.jeecg.modules.redbook.service.IRbAccountService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Tag(name = "RedBook账号管理")
@RestController
@RequestMapping("/redbook/account")
public class RbAccountController extends RedbookCrudController<RbAccount, IRbAccountService> {
    @GetMapping(value = "/list")
    @Operation(summary = "账号分页列表")
    public Result<?> list(RbAccount entity, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        return queryPageList(entity, pageNo, pageSize, req);
    }

    @PostMapping(value = "/add")
    @AutoLog(value = "新增小红书账号")
    public Result<?> add(@RequestBody RbAccount entity) {
        return saveEntity(entity);
    }

    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    @AutoLog(value = "编辑小红书账号")
    public Result<?> edit(@RequestBody RbAccount entity) {
        return updateEntity(entity);
    }

    @DeleteMapping(value = "/delete")
    @AutoLog(value = "删除小红书账号")
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
    public ModelAndView exportXls(HttpServletRequest request, RbAccount entity) {
        return exportExcel(request, entity, RbAccount.class, "小红书账号");
    }

    @PostMapping(value = "/importExcel")
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return importExcelData(request, response, RbAccount.class);
    }
}
