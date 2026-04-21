package org.jeecg.modules.redbook.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

/**
 * RedBook Agent simple CRUD base controller.
 */
public abstract class RedbookCrudController<T, S extends IService<T>> extends JeecgController<T, S> {

    protected Result<?> queryPageList(T entity, Integer pageNo, Integer pageSize, HttpServletRequest req) {
        QueryWrapper<T> queryWrapper = QueryGenerator.initQueryWrapper(entity, req.getParameterMap());
        queryWrapper.orderByDesc("create_time");
        Page<T> page = new Page<>(pageNo, pageSize);
        IPage<T> pageList = service.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    protected Result<?> saveEntity(T entity) {
        service.save(entity);
        return Result.OK("添加成功！");
    }

    protected Result<?> updateEntity(T entity) {
        service.updateById(entity);
        return Result.OK("更新成功！");
    }

    protected Result<?> removeEntity(String id) {
        service.removeById(id);
        return Result.OK("删除成功！");
    }

    protected Result<?> removeBatch(String ids) {
        service.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功！");
    }

    protected Result<?> queryEntityById(String id) {
        return Result.OK(service.getById(id));
    }

    protected ModelAndView exportExcel(HttpServletRequest request, T entity, Class<T> clazz, String title) {
        return super.exportXls(request, entity, clazz, title);
    }

    protected Result<?> importExcelData(HttpServletRequest request, HttpServletResponse response, Class<T> clazz) {
        return super.importExcel(request, response, clazz);
    }
}
