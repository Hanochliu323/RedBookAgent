package org.jeecg.modules.redbook.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "复盘看板")
public class RedbookReviewDashboardVO {
    @Schema(description = "发布总数")
    private Long publishCount;

    @Schema(description = "已回收发布数")
    private Long collectedPublishCount;

    @Schema(description = "未回收发布数")
    private Long uncollectedPublishCount;

    @Schema(description = "数据回收记录数")
    private Long metricCount;

    @Schema(description = "复盘报告数")
    private Long reviewReportCount;

    @Schema(description = "平均阅读/播放量")
    private BigDecimal avgViews;

    @Schema(description = "平均互动率")
    private BigDecimal avgInteractionRate;

    @Schema(description = "平均收藏率")
    private BigDecimal avgCollectRate;

    @Schema(description = "平均评论率")
    private BigDecimal avgCommentRate;

    @Schema(description = "最新复盘报告名称")
    private String latestReportName;

    @Schema(description = "最新复盘摘要")
    private String latestSummary;

    @Schema(description = "高表现笔记榜")
    private List<RedbookReviewRankItemVO> highPerformList;

    @Schema(description = "低表现笔记榜")
    private List<RedbookReviewRankItemVO> lowPerformList;

    @Schema(description = "赛道表现")
    private List<RedbookReviewDimensionVO> trackBoard;

    @Schema(description = "账号表现")
    private List<RedbookReviewDimensionVO> accountBoard;

    @Schema(description = "发布时间表现")
    private List<RedbookReviewDimensionVO> publishWindowBoard;

    @Schema(description = "下一轮选题建议")
    private List<String> nextTopicSuggestions;

    @Schema(description = "下一轮标题建议")
    private List<String> nextTitleSuggestions;

    @Schema(description = "下一轮发布时间建议")
    private List<String> nextPublishSuggestions;
}
