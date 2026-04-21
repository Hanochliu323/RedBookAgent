package org.jeecg.modules.redbook.constant;

public final class RedbookStatusConstant {
    private RedbookStatusConstant() {
    }

    public static final String ACTIVE = "active";

    public static final String HOTSPOT_PENDING_ANALYSIS = "pending_analysis";
    public static final String HOTSPOT_ANALYZED = "analyzed";
    public static final String HOTSPOT_DRAFT_GENERATED = "draft_generated";
    public static final String HOTSPOT_DISCARDED = "discarded";

    public static final String ANALYSIS_ANALYZED = "analyzed";
    public static final String ANALYSIS_ADOPTED = "adopted";
    public static final String ANALYSIS_DISCARDED = "discarded";

    public static final String DRAFT_PENDING_REVIEW = "pending_review";
    public static final String DRAFT_PENDING_PUBLISH = "pending_publish";
    public static final String DRAFT_PUBLISHED = "published";

    public static final String AUDIT_PENDING = "pending";
    public static final String AUDIT_APPROVED = "approved";
    public static final String AUDIT_REJECTED = "rejected";

    public static final String PUBLISH_PENDING = "pending";
    public static final String PUBLISH_PUBLISHED = "published";
    public static final String PUBLISH_DELAYED = "delayed";
    public static final String PUBLISH_CANCELED = "canceled";
    public static final String PUBLISH_DATA_COLLECTED = "data_collected";

    public static final String REVIEW_DRAFT = "draft";
    public static final String REVIEW_GENERATED = "generated";
}
