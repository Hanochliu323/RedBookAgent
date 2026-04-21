package org.jeecg.modules.redbook.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.system.base.entity.JeecgEntity;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "笔记草稿")
@TableName("rb_note_draft")
public class RbNoteDraft extends JeecgEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Excel(name = "热点ID", width = 25)
    @Schema(description = "热点ID")
    private String hotspotId;

    @Excel(name = "分析ID", width = 25)
    @Schema(description = "分析ID")
    private String analysisId;

    @Excel(name = "赛道ID", width = 25)
    @Schema(description = "赛道ID")
    private String trackId;

    @Excel(name = "账号ID", width = 25)
    @Schema(description = "账号ID")
    private String accountId;

    @Excel(name = "标题", width = 40)
    @Schema(description = "标题")
    private String title;

    @Excel(name = "封面文案", width = 40)
    @Schema(description = "封面文案")
    private String coverCopy;

    @Excel(name = "正文", width = 100)
    @Schema(description = "正文")
    private String body;

    @Excel(name = "标签", width = 40)
    @Schema(description = "标签")
    private String tags;

    @Excel(name = "评论区引导语", width = 40)
    @Schema(description = "评论区引导语")
    private String commentGuide;

    @Excel(name = "发布时间建议", width = 40)
    @Schema(description = "发布时间建议")
    private String publishTimeSuggestion;

    @Excel(name = "内容类型", width = 20)
    @Schema(description = "内容类型")
    private String contentType;

    @Excel(name = "AI版本", width = 30)
    @Schema(description = "AI版本")
    private String aiVersion;

    @Excel(name = "人工修改版本", width = 30)
    @Schema(description = "人工修改版本")
    private String manualVersion;

    @Excel(name = "风险检测结果", width = 60)
    @Schema(description = "风险检测结果")
    private String riskCheckResult;

    @Excel(name = "审核状态", width = 20)
    @Schema(description = "审核状态")
    private String auditStatus;

    @Excel(name = "审核意见", width = 50)
    @Schema(description = "审核意见")
    private String auditOpinion;

    @Excel(name = "状态", width = 20)
    @Schema(description = "状态")
    private String status;
}
