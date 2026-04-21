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
@Schema(description = "小红书账号")
@TableName("rb_account")
public class RbAccount extends JeecgEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Excel(name = "账号名称", width = 25)
    @Schema(description = "账号名称")
    private String accountName;

    @Excel(name = "平台", width = 15)
    @Schema(description = "平台")
    private String platform;

    @Excel(name = "账号定位", width = 30)
    @Schema(description = "账号定位")
    private String positioning;

    @Excel(name = "目标人群", width = 30)
    @Schema(description = "目标人群")
    private String targetAudience;

    @Excel(name = "内容风格", width = 30)
    @Schema(description = "内容风格")
    private String contentStyle;

    @Excel(name = "主要赛道ID", width = 25)
    @Schema(description = "主要赛道ID")
    private String primaryTrackId;

    @Excel(name = "状态", width = 15)
    @Schema(description = "状态")
    private String status;

    @Excel(name = "备注", width = 40)
    @Schema(description = "备注")
    private String remark;
}
