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
@Schema(description = "运营赛道")
@TableName("rb_track")
public class RbTrack extends JeecgEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Excel(name = "赛道名称", width = 25)
    @Schema(description = "赛道名称")
    private String trackName;

    @Excel(name = "关键词", width = 40)
    @Schema(description = "关键词")
    private String keywords;

    @Excel(name = "目标人群", width = 30)
    @Schema(description = "目标人群")
    private String targetAudience;

    @Excel(name = "内容方向", width = 40)
    @Schema(description = "内容方向")
    private String contentDirection;

    @Excel(name = "竞品账号", width = 40)
    @Schema(description = "竞品账号")
    private String competitorAccounts;

    @Excel(name = "优先级", width = 15)
    @Schema(description = "优先级")
    private Integer priority;

    @Excel(name = "状态", width = 15)
    @Schema(description = "状态")
    private String status;

    @Excel(name = "备注", width = 40)
    @Schema(description = "备注")
    private String remark;
}
