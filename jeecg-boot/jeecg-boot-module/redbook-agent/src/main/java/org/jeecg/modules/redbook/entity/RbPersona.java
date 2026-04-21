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
@Schema(description = "账号人设")
@TableName("rb_persona")
public class RbPersona extends JeecgEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Excel(name = "人设名称", width = 25)
    @Schema(description = "人设名称")
    private String personaName;

    @Excel(name = "语气风格", width = 30)
    @Schema(description = "语气风格")
    private String toneStyle;

    @Excel(name = "专业程度", width = 20)
    @Schema(description = "专业程度")
    private String professionalism;

    @Excel(name = "目标用户", width = 30)
    @Schema(description = "目标用户")
    private String targetAudience;

    @Excel(name = "禁止表达", width = 40)
    @Schema(description = "禁止表达")
    private String forbiddenExpressions;

    @Excel(name = "常用表达", width = 40)
    @Schema(description = "常用表达")
    private String commonExpressions;

    @Excel(name = "状态", width = 15)
    @Schema(description = "状态")
    private String status;

    @Excel(name = "备注", width = 40)
    @Schema(description = "备注")
    private String remark;
}
