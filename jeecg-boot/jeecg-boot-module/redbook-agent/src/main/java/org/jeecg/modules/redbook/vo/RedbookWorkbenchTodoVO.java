package org.jeecg.modules.redbook.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "工作台待办项")
public class RedbookWorkbenchTodoVO {
    @Schema(description = "业务ID")
    private String id;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "模块名称")
    private String moduleTitle;

    @Schema(description = "跳转路径")
    private String routePath;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "摘要")
    private String summary;

    @Schema(description = "更新时间")
    private Date updateTime;
}
