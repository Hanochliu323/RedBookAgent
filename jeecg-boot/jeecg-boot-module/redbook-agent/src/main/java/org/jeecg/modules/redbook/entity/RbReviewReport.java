package org.jeecg.modules.redbook.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.system.base.entity.JeecgEntity;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "复盘报告")
@TableName("rb_review_report")
public class RbReviewReport extends JeecgEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Excel(name = "报告名称", width = 30)
    @Schema(description = "报告名称")
    private String reportName;

    @Excel(name = "赛道ID", width = 25)
    @Schema(description = "赛道ID")
    private String trackId;

    @Excel(name = "账号ID", width = 25)
    @Schema(description = "账号ID")
    private String accountId;

    @Excel(name = "开始日期", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "开始日期")
    private Date periodStart;

    @Excel(name = "结束日期", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "结束日期")
    private Date periodEnd;

    @Excel(name = "表现总结", width = 80)
    @Schema(description = "表现总结")
    private String summary;

    @Excel(name = "高表现因素", width = 80)
    @Schema(description = "高表现因素")
    private String highPerformingFactors;

    @Excel(name = "低表现原因", width = 80)
    @Schema(description = "低表现原因")
    private String lowPerformingReasons;

    @Excel(name = "可复用选题", width = 80)
    @Schema(description = "可复用选题")
    private String reusableTopics;

    @Excel(name = "停止方向", width = 80)
    @Schema(description = "停止方向")
    private String stoppedDirections;

    @Excel(name = "下一轮选题建议", width = 80)
    @Schema(description = "下一轮选题建议")
    private String nextTopicSuggestions;

    @Excel(name = "下一轮标题建议", width = 80)
    @Schema(description = "下一轮标题建议")
    private String nextTitleSuggestions;

    @Excel(name = "下一轮发布时间建议", width = 60)
    @Schema(description = "下一轮发布时间建议")
    private String nextPublishSuggestions;

    @Schema(description = "AI 原始输出")
    private String rawResult;

    @Excel(name = "状态", width = 20)
    @Schema(description = "状态")
    private String status;
}
