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
@Schema(description = "热点池")
@TableName("rb_hotspot")
public class RbHotspot extends JeecgEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Excel(name = "赛道ID", width = 25)
    @Schema(description = "赛道ID")
    private String trackId;

    @Excel(name = "来源平台", width = 15)
    @Schema(description = "来源平台")
    private String sourcePlatform;

    @Excel(name = "热点标题", width = 40)
    @Schema(description = "热点标题")
    private String title;

    @Excel(name = "热点摘要", width = 80)
    @Schema(description = "热点摘要")
    private String summary;

    @Excel(name = "原文链接", width = 60)
    @Schema(description = "原文链接")
    private String originalUrl;

    @Excel(name = "作者/账号", width = 25)
    @Schema(description = "作者/账号")
    private String authorName;

    @Excel(name = "点赞数", width = 15)
    @Schema(description = "点赞数")
    private Long likeCount;

    @Excel(name = "收藏数", width = 15)
    @Schema(description = "收藏数")
    private Long collectCount;

    @Excel(name = "评论数", width = 15)
    @Schema(description = "评论数")
    private Long commentCount;

    @Excel(name = "分享数", width = 15)
    @Schema(description = "分享数")
    private Long shareCount;

    @Excel(name = "发布时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "发布时间")
    private Date publishTime;

    @Excel(name = "采集时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "采集时间")
    private Date collectTime;

    @Excel(name = "标签", width = 40)
    @Schema(description = "标签")
    private String tags;

    @Excel(name = "热度评分", width = 15)
    @Schema(description = "热度评分")
    private BigDecimal heatScore;

    @Excel(name = "可二创评分", width = 15)
    @Schema(description = "可二创评分")
    private BigDecimal remixScore;

    @Excel(name = "风险等级", width = 15)
    @Schema(description = "风险等级")
    private String riskLevel;

    @Excel(name = "状态", width = 20)
    @Schema(description = "状态")
    private String status;
}
