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
@Schema(description = "产品卖点")
@TableName("rb_product")
public class RbProduct extends JeecgEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Excel(name = "产品/服务名称", width = 30)
    @Schema(description = "产品/服务名称")
    private String productName;

    @Excel(name = "核心卖点", width = 50)
    @Schema(description = "核心卖点")
    private String coreSellingPoints;

    @Excel(name = "适用人群", width = 30)
    @Schema(description = "适用人群")
    private String targetAudience;

    @Excel(name = "使用场景", width = 40)
    @Schema(description = "使用场景")
    private String scenarios;

    @Excel(name = "差异化优势", width = 40)
    @Schema(description = "差异化优势")
    private String advantages;

    @Excel(name = "禁止承诺", width = 40)
    @Schema(description = "禁止承诺")
    private String forbiddenPromises;

    @Excel(name = "状态", width = 15)
    @Schema(description = "状态")
    private String status;

    @Excel(name = "备注", width = 40)
    @Schema(description = "备注")
    private String remark;
}
