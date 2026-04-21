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
@Schema(description = "敏感词/禁用词")
@TableName("rb_sensitive_word")
public class RbSensitiveWord extends JeecgEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Excel(name = "词条", width = 25)
    @Schema(description = "词条")
    private String word;

    @Excel(name = "分类", width = 20)
    @Schema(description = "分类")
    private String category;

    @Excel(name = "风险等级", width = 15)
    @Schema(description = "风险等级")
    private String riskLevel;

    @Excel(name = "替换建议", width = 40)
    @Schema(description = "替换建议")
    private String replacementSuggestion;

    @Excel(name = "状态", width = 15)
    @Schema(description = "状态")
    private String status;

    @Excel(name = "备注", width = 40)
    @Schema(description = "备注")
    private String remark;
}
