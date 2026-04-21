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
@Schema(description = "笔记草稿版本")
@TableName("rb_note_draft_version")
public class RbNoteDraftVersion extends JeecgEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Excel(name = "草稿ID", width = 25)
    @Schema(description = "草稿ID")
    private String draftId;

    @Excel(name = "版本号", width = 15)
    @Schema(description = "版本号")
    private Integer versionNo;

    @Excel(name = "版本类型", width = 20)
    @Schema(description = "版本类型")
    private String versionType;

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

    @Excel(name = "备注", width = 50)
    @Schema(description = "备注")
    private String remark;
}
