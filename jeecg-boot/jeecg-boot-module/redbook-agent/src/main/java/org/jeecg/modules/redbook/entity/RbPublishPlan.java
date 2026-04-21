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
@Schema(description = "发布计划")
@TableName("rb_publish_plan")
public class RbPublishPlan extends JeecgEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Excel(name = "草稿ID", width = 25)
    @Schema(description = "草稿ID")
    private String draftId;

    @Excel(name = "账号ID", width = 25)
    @Schema(description = "账号ID")
    private String accountId;

    @Excel(name = "计划发布时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "计划发布时间")
    private Date plannedPublishTime;

    @Excel(name = "实际发布时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "实际发布时间")
    private Date actualPublishTime;

    @Excel(name = "发布状态", width = 20)
    @Schema(description = "发布状态")
    private String publishStatus;

    @Excel(name = "小红书笔记链接", width = 60)
    @Schema(description = "小红书笔记链接")
    private String noteUrl;

    @Excel(name = "发布人", width = 20)
    @Schema(description = "发布人")
    private String publisher;

    @Excel(name = "备注", width = 40)
    @Schema(description = "备注")
    private String remark;
}
