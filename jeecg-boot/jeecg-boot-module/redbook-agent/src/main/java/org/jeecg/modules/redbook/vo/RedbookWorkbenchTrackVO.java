package org.jeecg.modules.redbook.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "工作台赛道看板")
public class RedbookWorkbenchTrackVO {
    @Schema(description = "赛道ID")
    private String trackId;

    @Schema(description = "赛道名称")
    private String trackName;

    @Schema(description = "热点数")
    private Long hotspotCount;

    @Schema(description = "草稿数")
    private Long draftCount;

    @Schema(description = "已发布数")
    private Long publishedCount;
}
