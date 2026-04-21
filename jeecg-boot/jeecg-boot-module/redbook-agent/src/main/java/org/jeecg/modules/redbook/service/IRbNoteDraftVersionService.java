package org.jeecg.modules.redbook.service;

import org.jeecg.common.system.base.service.JeecgService;
import org.jeecg.modules.redbook.entity.RbNoteDraft;
import org.jeecg.modules.redbook.entity.RbNoteDraftVersion;

import java.util.List;

public interface IRbNoteDraftVersionService extends JeecgService<RbNoteDraftVersion> {
    RbNoteDraftVersion saveDraftSnapshot(RbNoteDraft draft, String versionType, String remark);

    List<RbNoteDraftVersion> listByDraftId(String draftId);
}
