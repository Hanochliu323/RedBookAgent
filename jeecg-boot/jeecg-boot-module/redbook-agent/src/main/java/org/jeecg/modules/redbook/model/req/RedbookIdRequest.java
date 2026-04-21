package org.jeecg.modules.redbook.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "RedBook通用ID请求")
public class RedbookIdRequest {
    @Schema(description = "业务ID")
    private String id;
}
