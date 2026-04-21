package org.jeecg.modules.redbook.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "复盘维度表现")
public class RedbookReviewDimensionVO {
    @Schema(description = "维度ID")
    private String dimensionId;

    @Schema(description = "维度名称")
    private String dimensionName;

    @Schema(description = "发布数量")
    private Long publishCount;

    @Schema(description = "已回收数量")
    private Long collectedCount;

    @Schema(description = "总阅读/播放量")
    private Long totalViews;

    @Schema(description = "平均阅读/播放量")
    private BigDecimal avgViews;

    @Schema(description = "平均互动率")
    private BigDecimal avgInteractionRate;

    @Schema(description = "表现权重")
    private BigDecimal score;
}
