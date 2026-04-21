package org.jeecg.modules.redbook.service;

import org.jeecg.modules.redbook.model.ai.RedbookAiExecutionResult;

import java.util.Map;

public interface IRedbookAiService {
    RedbookAiExecutionResult runWorkflow(String templateCode, Map<String, Object> payload);
}
