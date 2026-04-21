package org.jeecg.modules.redbook.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Schema(description = "复盘榜单项")
public class RedbookReviewRankItemVO {
    @Schema(description = "发布计划ID")
    private String publishPlanId;

    @Schema(description = "草稿ID")
    private String draftId;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "赛道ID")
    private String trackId;

    @Schema(description = "赛道名称")
    private String trackName;

    @Schema(description = "账号ID")
    private String accountId;

    @Schema(description = "账号名称")
    private String accountName;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "发布时间")
    private Date publishTime;

    @Schema(description = "采集节点")
    private String collectNode;

    @Schema(description = "阅读/播放量")
    private Long views;

    @Schema(description = "点赞数")
    private Long likes;

    @Schema(description = "收藏数")
    private Long collects;

    @Schema(description = "评论数")
    private Long comments;

    @Schema(description = "分享数")
    private Long shares;

    @Schema(description = "互动率")
    private BigDecimal interactionRate;

    @Schema(description = "收藏率")
    private BigDecimal collectRate;

    @Schema(description = "综合表现分")
    private BigDecimal score;

    @Schema(description = "笔记链接")
    private String noteUrl;
}
