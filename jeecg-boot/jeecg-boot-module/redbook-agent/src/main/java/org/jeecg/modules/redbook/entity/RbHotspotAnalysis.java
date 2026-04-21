package org.jeecg.modules.redbook.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.system.base.entity.JeecgEntity;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "热点分析结果")
@TableName("rb_hotspot_analysis")
public class RbHotspotAnalysis extends JeecgEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Excel(name = "热点ID", width = 25)
    @Schema(description = "热点ID")
    private String hotspotId;

    @Excel(name = "用户痛点", width = 60)
    @Schema(description = "用户痛点")
    private String painPoints;

    @Excel(name = "爆点拆解", width = 60)
    @Schema(description = "爆点拆解")
    private String hookAnalysis;

    @Excel(name = "内容角度", width = 60)
    @Schema(description = "内容角度")
    private String contentAngle;

    @Excel(name = "标题方向", width = 60)
    @Schema(description = "标题方向")
    private String titleDirections;

    @Excel(name = "正文结构建议", width = 80)
    @Schema(description = "正文结构建议")
    private String outlineSuggestion;

    @Excel(name = "封面文案建议", width = 60)
    @Schema(description = "封面文案建议")
    private String coverCopySuggestion;

    @Excel(name = "标签建议", width = 40)
    @Schema(description = "标签建议")
    private String tagSuggestion;

    @Excel(name = "产品植入建议", width = 60)
    @Schema(description = "产品植入建议")
    private String productFit;

    @Excel(name = "风险提示", width = 60)
    @Schema(description = "风险提示")
    private String riskWarning;

    @Excel(name = "原创化建议", width = 60)
    @Schema(description = "原创化建议")
    private String originalitySuggestion;

    @Excel(name = "综合评分", width = 15)
    @Schema(description = "综合评分")
    private BigDecimal score;

    @Schema(description = "AI 原始输出")
    private String rawResult;

    @Excel(name = "状态", width = 20)
    @Schema(description = "状态")
    private String status;
}
