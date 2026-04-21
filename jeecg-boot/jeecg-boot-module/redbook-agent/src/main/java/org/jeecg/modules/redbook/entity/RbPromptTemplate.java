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
@Schema(description = "提示词模板")
@TableName("rb_prompt_template")
public class RbPromptTemplate extends JeecgEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Excel(name = "模板名称", width = 25)
    @Schema(description = "模板名称")
    private String templateName;

    @Excel(name = "模板编码", width = 25)
    @Schema(description = "模板编码")
    private String templateCode;

    @Excel(name = "工作流类型", width = 20)
    @Schema(description = "工作流类型")
    private String workflowType;

    @Excel(name = "模型提供方", width = 20)
    @Schema(description = "模型提供方")
    private String modelProvider;

    @Excel(name = "提示词内容", width = 80)
    @Schema(description = "提示词内容")
    private String promptContent;

    @Excel(name = "输出格式", width = 60)
    @Schema(description = "输出格式")
    private String outputSchema;

    @Excel(name = "状态", width = 15)
    @Schema(description = "状态")
    private String status;

    @Excel(name = "备注", width = 40)
    @Schema(description = "备注")
    private String remark;
}
