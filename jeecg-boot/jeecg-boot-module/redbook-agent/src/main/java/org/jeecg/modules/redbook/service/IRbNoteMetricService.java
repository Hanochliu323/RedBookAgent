package org.jeecg.modules.redbook.service;

import org.jeecg.common.system.base.service.JeecgService;
import org.jeecg.modules.redbook.entity.RbNoteMetric;

public interface IRbNoteMetricService extends JeecgService<RbNoteMetric> {
    RbNoteMetric normalizeMetric(RbNoteMetric entity);
}
