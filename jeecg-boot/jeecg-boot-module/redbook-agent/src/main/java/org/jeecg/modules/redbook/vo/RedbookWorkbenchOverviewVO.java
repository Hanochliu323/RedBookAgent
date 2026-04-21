package org.jeecg.modules.redbook.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "工作台概览")
public class RedbookWorkbenchOverviewVO {
    @Schema(description = "赛道数量")
    private Long trackCount;

    @Schema(description = "热点数量")
    private Long hotspotCount;

    @Schema(description = "分析数量")
    private Long analysisCount;

    @Schema(description = "草稿数量")
    private Long draftCount;

    @Schema(description = "排期数量")
    private Long publishPlanCount;

    @Schema(description = "数据回收数量")
    private Long metricCount;

    @Schema(description = "复盘报告数量")
    private Long reviewReportCount;

    @Schema(description = "待分析热点数")
    private Long pendingHotspotCount;

    @Schema(description = "待审核草稿数")
    private Long pendingReviewCount;

    @Schema(description = "待发布排期数")
    private Long pendingPublishCount;

    @Schema(description = "已发布内容数")
    private Long publishedCount;

    @Schema(description = "平均热度")
    private BigDecimal avgHeatScore;

    @Schema(description = "平均互动率")
    private BigDecimal avgInteractionRate;

    @Schema(description = "待办列表")
    private List<RedbookWorkbenchTodoVO> todoList;

    @Schema(description = "赛道表现榜")
    private List<RedbookWorkbenchTrackVO> trackBoard;
}
