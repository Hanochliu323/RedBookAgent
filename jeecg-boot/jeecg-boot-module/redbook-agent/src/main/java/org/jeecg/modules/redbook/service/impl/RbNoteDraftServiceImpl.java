package org.jeecg.modules.redbook.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.jeecg.modules.redbook.constant.RedbookStatusConstant;
import org.jeecg.modules.redbook.entity.RbNoteDraft;
import org.jeecg.modules.redbook.entity.RbNoteDraftVersion;
import org.jeecg.modules.redbook.mapper.RbNoteDraftMapper;
import org.jeecg.modules.redbook.service.IRbNoteDraftService;
import org.jeecg.modules.redbook.service.IRbNoteDraftVersionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class RbNoteDraftServiceImpl extends ServiceImpl<RbNoteDraftMapper, RbNoteDraft> implements IRbNoteDraftService {
    private static final Set<String> DRAFT_STATUS_VALUES = Set.of(
        RedbookStatusConstant.DRAFT_PENDING_REVIEW,
        RedbookStatusConstant.DRAFT_PENDING_PUBLISH,
        RedbookStatusConstant.DRAFT_PUBLISHED
    );
    private static final Set<String> AUDIT_STATUS_VALUES = Set.of(
        RedbookStatusConstant.AUDIT_PENDING,
        RedbookStatusConstant.AUDIT_APPROVED,
        RedbookStatusConstant.AUDIT_REJECTED
    );

    @Resource
    private IRbNoteDraftVersionService noteDraftVersionService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RbNoteDraft createDraft(RbNoteDraft draft) {
        normalizeForCreate(draft);
        save(draft);
        noteDraftVersionService.saveDraftSnapshot(draft, "create", "创建草稿");
        return draft;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RbNoteDraft updateDraft(RbNoteDraft draft) {
        if (isBlank(draft.getId())) {
            throw new IllegalArgumentException("草稿ID不能为空");
        }
        RbNoteDraft oldDraft = getById(draft.getId());
        if (oldDraft == null) {
            throw new IllegalArgumentException("未找到对应草稿");
        }
        normalizeForUpdate(oldDraft, draft);
        validateDraftTransition(oldDraft, draft);
        updateById(draft);
        RbNoteDraft savedDraft = getById(draft.getId());
        noteDraftVersionService.saveDraftSnapshot(savedDraft, "manual_edit", "人工编辑草稿");
        return savedDraft;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RbNoteDraft saveOrUpdateDraft(RbNoteDraft draft, String versionType, String remark) {
        if (isBlank(draft.getId()) || getById(draft.getId()) == null) {
            normalizeForCreate(draft);
            save(draft);
        } else {
            RbNoteDraft oldDraft = getById(draft.getId());
            normalizeForUpdate(oldDraft, draft);
            validateDraftTransition(oldDraft, draft);
            updateById(draft);
        }
        RbNoteDraft savedDraft = getById(draft.getId());
        noteDraftVersionService.saveDraftSnapshot(savedDraft, defaultText(versionType, "system"), defaultText(remark, "系统保存草稿版本"));
        return savedDraft;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RbNoteDraft approveDraft(String draftId, String auditOpinion) {
        RbNoteDraft draft = getRequiredDraft(draftId);
        ensureNotPublished(draft);
        draft.setAuditStatus(RedbookStatusConstant.AUDIT_APPROVED);
        draft.setAuditOpinion(defaultText(auditOpinion, "审核通过"));
        draft.setStatus(RedbookStatusConstant.DRAFT_PENDING_PUBLISH);
        updateById(draft);
        noteDraftVersionService.saveDraftSnapshot(draft, "audit_approved", draft.getAuditOpinion());
        return draft;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RbNoteDraft rejectDraft(String draftId, String auditOpinion) {
        RbNoteDraft draft = getRequiredDraft(draftId);
        ensureNotPublished(draft);
        draft.setAuditStatus(RedbookStatusConstant.AUDIT_REJECTED);
        draft.setAuditOpinion(defaultText(auditOpinion, "审核退回，请修改后重新提交"));
        draft.setStatus(RedbookStatusConstant.DRAFT_PENDING_REVIEW);
        updateById(draft);
        noteDraftVersionService.saveDraftSnapshot(draft, "audit_rejected", draft.getAuditOpinion());
        return draft;
    }

    @Override
    public List<RbNoteDraftVersion> listVersions(String draftId) {
        getRequiredDraft(draftId);
        return noteDraftVersionService.listByDraftId(draftId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RbNoteDraft restoreVersion(String versionId) {
        RbNoteDraftVersion version = noteDraftVersionService.getById(versionId);
        if (version == null) {
            throw new IllegalArgumentException("未找到对应草稿版本");
        }
        RbNoteDraft draft = getRequiredDraft(version.getDraftId());
        ensureNotPublished(draft);
        draft.setTitle(version.getTitle());
        draft.setCoverCopy(version.getCoverCopy());
        draft.setBody(version.getBody());
        draft.setTags(version.getTags());
        draft.setCommentGuide(version.getCommentGuide());
        draft.setPublishTimeSuggestion(version.getPublishTimeSuggestion());
        draft.setContentType(version.getContentType());
        draft.setRiskCheckResult(version.getRiskCheckResult());
        draft.setAuditStatus(RedbookStatusConstant.AUDIT_PENDING);
        draft.setAuditOpinion("已恢复到 v" + version.getVersionNo() + "，请重新审核");
        draft.setStatus(RedbookStatusConstant.DRAFT_PENDING_REVIEW);
        draft.setManualVersion("restore-v" + version.getVersionNo());
        updateById(draft);
        noteDraftVersionService.saveDraftSnapshot(draft, "restore", "恢复到版本 v" + version.getVersionNo());
        return draft;
    }

    private RbNoteDraft getRequiredDraft(String draftId) {
        if (isBlank(draftId)) {
            throw new IllegalArgumentException("草稿ID不能为空");
        }
        RbNoteDraft draft = getById(draftId);
        if (draft == null) {
            throw new IllegalArgumentException("未找到对应草稿");
        }
        return draft;
    }

    private void normalizeForCreate(RbNoteDraft draft) {
        if (isBlank(draft.getStatus())) {
            draft.setStatus(RedbookStatusConstant.DRAFT_PENDING_REVIEW);
        }
        if (isBlank(draft.getAuditStatus())) {
            draft.setAuditStatus(RedbookStatusConstant.AUDIT_PENDING);
        }
        validateStatusValue(draft.getStatus(), DRAFT_STATUS_VALUES, "草稿状态");
        validateStatusValue(draft.getAuditStatus(), AUDIT_STATUS_VALUES, "审核状态");
    }

    private void normalizeForUpdate(RbNoteDraft oldDraft, RbNoteDraft draft) {
        if (isBlank(draft.getStatus())) {
            draft.setStatus(oldDraft.getStatus());
        }
        if (isBlank(draft.getAuditStatus())) {
            draft.setAuditStatus(oldDraft.getAuditStatus());
        }
        validateStatusValue(draft.getStatus(), DRAFT_STATUS_VALUES, "草稿状态");
        validateStatusValue(draft.getAuditStatus(), AUDIT_STATUS_VALUES, "审核状态");
    }

    private void validateDraftTransition(RbNoteDraft oldDraft, RbNoteDraft draft) {
        String oldStatus = oldDraft.getStatus();
        String newStatus = draft.getStatus();
        if (RedbookStatusConstant.DRAFT_PUBLISHED.equals(oldStatus) && !RedbookStatusConstant.DRAFT_PUBLISHED.equals(newStatus)) {
            throw new IllegalArgumentException("已发布草稿不允许回退状态");
        }
        if (RedbookStatusConstant.DRAFT_PENDING_REVIEW.equals(oldStatus)
            && RedbookStatusConstant.DRAFT_PUBLISHED.equals(newStatus)) {
            throw new IllegalArgumentException("待审核草稿不能直接改为已发布，请先审核通过并加入排期");
        }
    }

    private void ensureNotPublished(RbNoteDraft draft) {
        if (RedbookStatusConstant.DRAFT_PUBLISHED.equals(draft.getStatus())) {
            throw new IllegalArgumentException("已发布草稿不能再审核或恢复版本");
        }
    }

    private void validateStatusValue(String status, Set<String> allowedValues, String label) {
        if (!allowedValues.contains(status)) {
            throw new IllegalArgumentException(label + "非法：" + status);
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private String defaultText(String value, String fallback) {
        return isBlank(value) ? fallback : value.trim();
    }
}
