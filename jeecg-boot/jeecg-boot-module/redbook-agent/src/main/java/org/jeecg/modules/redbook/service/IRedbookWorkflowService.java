package org.jeecg.modules.redbook.service;

import org.jeecg.modules.redbook.entity.RbHotspot;
import org.jeecg.modules.redbook.entity.RbHotspotAnalysis;
import org.jeecg.modules.redbook.entity.RbNoteDraft;
import org.jeecg.modules.redbook.entity.RbNoteMetric;
import org.jeecg.modules.redbook.entity.RbPublishPlan;
import org.jeecg.modules.redbook.entity.RbReviewReport;
import org.jeecg.modules.redbook.vo.RedbookReviewDashboardVO;
import org.jeecg.modules.redbook.vo.RedbookWorkbenchOverviewVO;

import java.util.List;

public interface IRedbookWorkflowService {
    RbHotspotAnalysis analyzeHotspot(String hotspotId);

    RbNoteDraft generateDraftByAnalysis(String analysisId);

    RbPublishPlan createPublishPlan(String draftId);

    RbPublishPlan markPublished(String publishPlanId);

    RbNoteMetric createMetricRecord(String publishPlanId);

    RbReviewReport generateReviewReport(String reviewReportId);

    List<RbHotspot> createHotspotsFromReviewReport(String reviewReportId);

    RedbookReviewDashboardVO getReviewDashboard();

    RedbookWorkbenchOverviewVO getWorkbenchOverview();
}
