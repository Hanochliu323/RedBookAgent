package org.jeecg.modules.redbook.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "RedBook审核请求")
public class RedbookAuditRequest {
    @Schema(description = "业务ID")
    private String id;

    @Schema(description = "审核意见")
    private String auditOpinion;
}
