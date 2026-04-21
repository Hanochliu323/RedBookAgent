package org.jeecg.modules.redbook.service;

import org.jeecg.common.system.base.service.JeecgService;
import org.jeecg.modules.redbook.entity.RbNoteDraft;
import org.jeecg.modules.redbook.entity.RbNoteDraftVersion;

import java.util.List;

public interface IRbNoteDraftService extends JeecgService<RbNoteDraft> {
    RbNoteDraft createDraft(RbNoteDraft draft);

    RbNoteDraft updateDraft(RbNoteDraft draft);

    RbNoteDraft saveOrUpdateDraft(RbNoteDraft draft, String versionType, String remark);

    RbNoteDraft approveDraft(String draftId, String auditOpinion);

    RbNoteDraft rejectDraft(String draftId, String auditOpinion);

    List<RbNoteDraftVersion> listVersions(String draftId);

    RbNoteDraft restoreVersion(String versionId);
}
