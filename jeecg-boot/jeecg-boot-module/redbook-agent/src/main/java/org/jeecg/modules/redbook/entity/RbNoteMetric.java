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
import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "笔记数据回收")
@TableName("rb_note_metric")
public class RbNoteMetric extends JeecgEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Excel(name = "发布计划ID", width = 25)
    @Schema(description = "发布计划ID")
    private String publishPlanId;

    @Excel(name = "草稿ID", width = 25)
    @Schema(description = "草稿ID")
    private String noteDraftId;

    @Excel(name = "采集节点", width = 15)
    @Schema(description = "采集节点")
    private String collectNode;

    @Excel(name = "曝光量", width = 15)
    @Schema(description = "曝光量")
    private Long impressions;

    @Excel(name = "阅读/播放量", width = 15)
    @Schema(description = "阅读/播放量")
    private Long views;

    @Excel(name = "点赞数", width = 15)
    @Schema(description = "点赞数")
    private Long likes;

    @Excel(name = "收藏数", width = 15)
    @Schema(description = "收藏数")
    private Long collects;

    @Excel(name = "评论数", width = 15)
    @Schema(description = "评论数")
    private Long comments;

    @Excel(name = "分享数", width = 15)
    @Schema(description = "分享数")
    private Long shares;

    @Excel(name = "关注数", width = 15)
    @Schema(description = "关注数")
    private Long followers;

    @Excel(name = "私信数", width = 15)
    @Schema(description = "私信数")
    private Long messages;

    @Excel(name = "线索数", width = 15)
    @Schema(description = "线索数")
    private Long leads;

    @Excel(name = "转化数", width = 15)
    @Schema(description = "转化数")
    private Long conversions;

    @Excel(name = "采集时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "采集时间")
    private Date collectTime;

    @Excel(name = "互动率", width = 15)
    @Schema(description = "互动率")
    private BigDecimal interactionRate;

    @Excel(name = "收藏率", width = 15)
    @Schema(description = "收藏率")
    private BigDecimal collectRate;

    @Excel(name = "评论率", width = 15)
    @Schema(description = "评论率")
    private BigDecimal commentRate;

    @Excel(name = "备注", width = 40)
    @Schema(description = "备注")
    private String remark;
}
