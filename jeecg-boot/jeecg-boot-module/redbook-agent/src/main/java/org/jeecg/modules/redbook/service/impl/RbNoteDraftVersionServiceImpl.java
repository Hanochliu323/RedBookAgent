package org.jeecg.modules.redbook.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.redbook.entity.RbNoteDraft;
import org.jeecg.modules.redbook.entity.RbNoteDraftVersion;
import org.jeecg.modules.redbook.mapper.RbNoteDraftVersionMapper;
import org.jeecg.modules.redbook.service.IRbNoteDraftVersionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RbNoteDraftVersionServiceImpl extends ServiceImpl<RbNoteDraftVersionMapper, RbNoteDraftVersion> implements IRbNoteDraftVersionService {
    @Override
    public RbNoteDraftVersion saveDraftSnapshot(RbNoteDraft draft, String versionType, String remark) {
        if (draft == null || draft.getId() == null || draft.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("草稿不存在，无法保存版本");
        }
        RbNoteDraftVersion latestVersion = lambdaQuery()
            .eq(RbNoteDraftVersion::getDraftId, draft.getId())
            .orderByDesc(RbNoteDraftVersion::getVersionNo)
            .last("limit 1")
            .one();
        Integer latestVersionNo = latestVersion == null || latestVersion.getVersionNo() == null ? 0 : latestVersion.getVersionNo();

        RbNoteDraftVersion version = new RbNoteDraftVersion();
        version.setDraftId(draft.getId());
        version.setVersionNo(latestVersionNo + 1);
        version.setVersionType(versionType);
        version.setTitle(draft.getTitle());
        version.setCoverCopy(draft.getCoverCopy());
        version.setBody(draft.getBody());
        version.setTags(draft.getTags());
        version.setCommentGuide(draft.getCommentGuide());
        version.setPublishTimeSuggestion(draft.getPublishTimeSuggestion());
        version.setContentType(draft.getContentType());
        version.setRiskCheckResult(draft.getRiskCheckResult());
        version.setAuditStatus(draft.getAuditStatus());
        version.setAuditOpinion(draft.getAuditOpinion());
        version.setStatus(draft.getStatus());
        version.setRemark(remark);
        save(version);
        return version;
    }

    @Override
    public List<RbNoteDraftVersion> listByDraftId(String draftId) {
        return lambdaQuery()
            .eq(RbNoteDraftVersion::getDraftId, draftId)
            .orderByDesc(RbNoteDraftVersion::getVersionNo)
            .list();
    }
}
