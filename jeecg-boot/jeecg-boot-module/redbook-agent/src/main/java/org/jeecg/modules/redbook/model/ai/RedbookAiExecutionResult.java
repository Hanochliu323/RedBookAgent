package org.jeecg.modules.redbook.model.ai;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
public class RedbookAiExecutionResult {
    private boolean success;

    private boolean remoteUsed;

    private String provider;

    private String templateCode;

    private String rawResult;

    private String errorMessage;

    private Map<String, Object> outputs = new LinkedHashMap<>();
}
