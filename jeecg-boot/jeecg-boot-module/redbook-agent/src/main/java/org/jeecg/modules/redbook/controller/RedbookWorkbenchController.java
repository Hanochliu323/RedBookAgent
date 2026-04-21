package org.jeecg.modules.redbook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.redbook.service.IRedbookWorkflowService;
import org.jeecg.modules.redbook.vo.RedbookReviewDashboardVO;
import org.jeecg.modules.redbook.vo.RedbookWorkbenchOverviewVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

@Tag(name = "RedBook工作台")
@RestController
@RequestMapping("/redbook/workbench")
public class RedbookWorkbenchController {
    @Resource
    private IRedbookWorkflowService redbookWorkflowService;

    @GetMapping(value = "/overview")
    @Operation(summary = "获取小红书运营工作台概览")
    public Result<RedbookWorkbenchOverviewVO> overview() {
        return Result.OK(redbookWorkflowService.getWorkbenchOverview());
    }

    @GetMapping(value = "/reviewDashboard")
    @Operation(summary = "获取小红书复盘看板")
    public Result<RedbookReviewDashboardVO> reviewDashboard() {
        return Result.OK(redbookWorkflowService.getReviewDashboard());
    }
}
