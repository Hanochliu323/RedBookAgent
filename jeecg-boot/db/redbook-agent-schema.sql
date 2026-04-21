CREATE DATABASE IF NOT EXISTS `jeecg-boot` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `jeecg-boot`;

CREATE TABLE IF NOT EXISTS `rb_account` (
  `id` varchar(36) NOT NULL COMMENT 'ID',
  `account_name` varchar(100) DEFAULT NULL COMMENT '账号名称',
  `platform` varchar(50) DEFAULT '小红书' COMMENT '平台',
  `positioning` varchar(500) DEFAULT NULL COMMENT '账号定位',
  `target_audience` varchar(500) DEFAULT NULL COMMENT '目标人群',
  `content_style` varchar(500) DEFAULT NULL COMMENT '内容风格',
  `primary_track_id` varchar(36) DEFAULT NULL COMMENT '主要赛道ID',
  `status` varchar(32) DEFAULT 'active' COMMENT '状态',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_rb_account_track` (`primary_track_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='小红书账号';

CREATE TABLE IF NOT EXISTS `rb_track` (
  `id` varchar(36) NOT NULL COMMENT 'ID',
  `track_name` varchar(100) DEFAULT NULL COMMENT '赛道名称',
  `keywords` varchar(1000) DEFAULT NULL COMMENT '关键词',
  `target_audience` varchar(500) DEFAULT NULL COMMENT '目标人群',
  `content_direction` varchar(1000) DEFAULT NULL COMMENT '内容方向',
  `competitor_accounts` varchar(1000) DEFAULT NULL COMMENT '竞品账号',
  `priority` int DEFAULT 10 COMMENT '优先级',
  `status` varchar(32) DEFAULT 'active' COMMENT '状态',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_rb_track_name` (`track_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='运营赛道';

CREATE TABLE IF NOT EXISTS `rb_persona` (
  `id` varchar(36) NOT NULL COMMENT 'ID',
  `persona_name` varchar(100) DEFAULT NULL COMMENT '人设名称',
  `tone_style` varchar(500) DEFAULT NULL COMMENT '语气风格',
  `professionalism` varchar(100) DEFAULT NULL COMMENT '专业程度',
  `target_audience` varchar(500) DEFAULT NULL COMMENT '目标用户',
  `forbidden_expressions` text COMMENT '禁止表达',
  `common_expressions` text COMMENT '常用表达',
  `status` varchar(32) DEFAULT 'active' COMMENT '状态',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号人设';

CREATE TABLE IF NOT EXISTS `rb_product` (
  `id` varchar(36) NOT NULL COMMENT 'ID',
  `product_name` varchar(100) DEFAULT NULL COMMENT '产品/服务名称',
  `core_selling_points` text COMMENT '核心卖点',
  `target_audience` varchar(500) DEFAULT NULL COMMENT '适用人群',
  `scenarios` varchar(1000) DEFAULT NULL COMMENT '使用场景',
  `advantages` varchar(1000) DEFAULT NULL COMMENT '差异化优势',
  `forbidden_promises` text COMMENT '禁止承诺',
  `status` varchar(32) DEFAULT 'active' COMMENT '状态',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品卖点';

CREATE TABLE IF NOT EXISTS `rb_prompt_template` (
  `id` varchar(36) NOT NULL COMMENT 'ID',
  `template_name` varchar(100) DEFAULT NULL COMMENT '模板名称',
  `template_code` varchar(100) DEFAULT NULL COMMENT '模板编码',
  `workflow_type` varchar(50) DEFAULT NULL COMMENT '工作流类型',
  `model_provider` varchar(50) DEFAULT NULL COMMENT '模型提供方',
  `prompt_content` text COMMENT '提示词内容',
  `output_schema` text COMMENT '输出格式',
  `status` varchar(32) DEFAULT 'active' COMMENT '状态',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_rb_prompt_template_code` (`template_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='提示词模板';

CREATE TABLE IF NOT EXISTS `rb_sensitive_word` (
  `id` varchar(36) NOT NULL COMMENT 'ID',
  `word` varchar(100) DEFAULT NULL COMMENT '词条',
  `category` varchar(50) DEFAULT NULL COMMENT '分类',
  `risk_level` varchar(32) DEFAULT NULL COMMENT '风险等级',
  `replacement_suggestion` varchar(500) DEFAULT NULL COMMENT '替换建议',
  `status` varchar(32) DEFAULT 'active' COMMENT '状态',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_rb_sensitive_word_word` (`word`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='敏感词/禁用词';

CREATE TABLE IF NOT EXISTS `rb_hotspot` (
  `id` varchar(36) NOT NULL COMMENT 'ID',
  `track_id` varchar(36) DEFAULT NULL COMMENT '赛道ID',
  `source_platform` varchar(50) DEFAULT NULL COMMENT '来源平台',
  `title` varchar(300) DEFAULT NULL COMMENT '热点标题',
  `summary` text COMMENT '热点摘要',
  `original_url` varchar(1000) DEFAULT NULL COMMENT '原文链接',
  `author_name` varchar(100) DEFAULT NULL COMMENT '作者/账号',
  `like_count` bigint DEFAULT 0 COMMENT '点赞数',
  `collect_count` bigint DEFAULT 0 COMMENT '收藏数',
  `comment_count` bigint DEFAULT 0 COMMENT '评论数',
  `share_count` bigint DEFAULT 0 COMMENT '分享数',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  `collect_time` datetime DEFAULT NULL COMMENT '采集时间',
  `tags` varchar(1000) DEFAULT NULL COMMENT '标签',
  `heat_score` decimal(10,2) DEFAULT NULL COMMENT '热度评分',
  `remix_score` decimal(10,2) DEFAULT NULL COMMENT '可二创评分',
  `risk_level` varchar(32) DEFAULT NULL COMMENT '风险等级',
  `status` varchar(32) DEFAULT 'pending_analysis' COMMENT '状态',
  `create_by` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_rb_hotspot_track` (`track_id`),
  KEY `idx_rb_hotspot_status` (`status`),
  KEY `idx_rb_hotspot_collect_time` (`collect_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='热点池';

CREATE TABLE IF NOT EXISTS `rb_hotspot_analysis` (
  `id` varchar(36) NOT NULL COMMENT 'ID',
  `hotspot_id` varchar(36) DEFAULT NULL COMMENT '热点ID',
  `pain_points` text COMMENT '用户痛点',
  `hook_analysis` text COMMENT '爆点拆解',
  `content_angle` text COMMENT '内容角度',
  `title_directions` text COMMENT '标题方向',
  `outline_suggestion` text COMMENT '正文结构建议',
  `cover_copy_suggestion` text COMMENT '封面文案建议',
  `tag_suggestion` varchar(1000) DEFAULT NULL COMMENT '标签建议',
  `product_fit` text COMMENT '产品植入建议',
  `risk_warning` text COMMENT '风险提示',
  `originality_suggestion` text COMMENT '原创化建议',
  `score` decimal(10,2) DEFAULT NULL COMMENT '综合评分',
  `raw_result` longtext COMMENT 'AI 原始输出',
  `status` varchar(32) DEFAULT 'analyzed' COMMENT '状态',
  `create_by` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_rb_analysis_hotspot` (`hotspot_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='热点分析结果';

CREATE TABLE IF NOT EXISTS `rb_note_draft` (
  `id` varchar(36) NOT NULL COMMENT 'ID',
  `hotspot_id` varchar(36) DEFAULT NULL COMMENT '热点ID',
  `analysis_id` varchar(36) DEFAULT NULL COMMENT '分析ID',
  `track_id` varchar(36) DEFAULT NULL COMMENT '赛道ID',
  `account_id` varchar(36) DEFAULT NULL COMMENT '账号ID',
  `title` varchar(300) DEFAULT NULL COMMENT '标题',
  `cover_copy` varchar(300) DEFAULT NULL COMMENT '封面文案',
  `body` longtext COMMENT '正文',
  `tags` varchar(1000) DEFAULT NULL COMMENT '标签',
  `comment_guide` varchar(500) DEFAULT NULL COMMENT '评论区引导语',
  `publish_time_suggestion` varchar(500) DEFAULT NULL COMMENT '发布时间建议',
  `content_type` varchar(50) DEFAULT NULL COMMENT '内容类型',
  `ai_version` varchar(100) DEFAULT NULL COMMENT 'AI版本',
  `manual_version` varchar(100) DEFAULT NULL COMMENT '人工修改版本',
  `risk_check_result` text COMMENT '风险检测结果',
  `audit_status` varchar(32) DEFAULT 'pending' COMMENT '审核状态',
  `audit_opinion` varchar(1000) DEFAULT NULL COMMENT '审核意见',
  `status` varchar(32) DEFAULT 'pending_review' COMMENT '状态',
  `create_by` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_rb_draft_hotspot` (`hotspot_id`),
  KEY `idx_rb_draft_account` (`account_id`),
  KEY `idx_rb_draft_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='笔记草稿';

CREATE TABLE IF NOT EXISTS `rb_publish_plan` (
  `id` varchar(36) NOT NULL COMMENT 'ID',
  `draft_id` varchar(36) DEFAULT NULL COMMENT '草稿ID',
  `account_id` varchar(36) DEFAULT NULL COMMENT '账号ID',
  `planned_publish_time` datetime DEFAULT NULL COMMENT '计划发布时间',
  `actual_publish_time` datetime DEFAULT NULL COMMENT '实际发布时间',
  `publish_status` varchar(32) DEFAULT 'pending' COMMENT '发布状态',
  `note_url` varchar(1000) DEFAULT NULL COMMENT '小红书笔记链接',
  `publisher` varchar(100) DEFAULT NULL COMMENT '发布人',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_rb_publish_draft` (`draft_id`),
  KEY `idx_rb_publish_account` (`account_id`),
  KEY `idx_rb_publish_time` (`planned_publish_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='发布计划';

CREATE TABLE IF NOT EXISTS `rb_note_metric` (
  `id` varchar(36) NOT NULL COMMENT 'ID',
  `publish_plan_id` varchar(36) DEFAULT NULL COMMENT '发布计划ID',
  `note_draft_id` varchar(36) DEFAULT NULL COMMENT '草稿ID',
  `collect_node` varchar(32) DEFAULT NULL COMMENT '采集节点',
  `impressions` bigint DEFAULT 0 COMMENT '曝光量',
  `views` bigint DEFAULT 0 COMMENT '阅读/播放量',
  `likes` bigint DEFAULT 0 COMMENT '点赞数',
  `collects` bigint DEFAULT 0 COMMENT '收藏数',
  `comments` bigint DEFAULT 0 COMMENT '评论数',
  `shares` bigint DEFAULT 0 COMMENT '分享数',
  `followers` bigint DEFAULT 0 COMMENT '关注数',
  `messages` bigint DEFAULT 0 COMMENT '私信数',
  `leads` bigint DEFAULT 0 COMMENT '线索数',
  `conversions` bigint DEFAULT 0 COMMENT '转化数',
  `collect_time` datetime DEFAULT NULL COMMENT '采集时间',
  `interaction_rate` decimal(10,4) DEFAULT NULL COMMENT '互动率',
  `collect_rate` decimal(10,4) DEFAULT NULL COMMENT '收藏率',
  `comment_rate` decimal(10,4) DEFAULT NULL COMMENT '评论率',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_rb_metric_publish` (`publish_plan_id`),
  KEY `idx_rb_metric_node` (`collect_node`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='笔记数据回收';

CREATE TABLE IF NOT EXISTS `rb_review_report` (
  `id` varchar(36) NOT NULL COMMENT 'ID',
  `report_name` varchar(200) DEFAULT NULL COMMENT '报告名称',
  `track_id` varchar(36) DEFAULT NULL COMMENT '赛道ID',
  `account_id` varchar(36) DEFAULT NULL COMMENT '账号ID',
  `period_start` date DEFAULT NULL COMMENT '开始日期',
  `period_end` date DEFAULT NULL COMMENT '结束日期',
  `summary` text COMMENT '表现总结',
  `high_performing_factors` text COMMENT '高表现因素',
  `low_performing_reasons` text COMMENT '低表现原因',
  `reusable_topics` text COMMENT '可复用选题',
  `stopped_directions` text COMMENT '停止方向',
  `next_topic_suggestions` text COMMENT '下一轮选题建议',
  `next_title_suggestions` text COMMENT '下一轮标题建议',
  `next_publish_suggestions` text COMMENT '下一轮发布时间建议',
  `raw_result` longtext COMMENT 'AI 原始输出',
  `status` varchar(32) DEFAULT 'draft' COMMENT '状态',
  `create_by` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_rb_review_track` (`track_id`),
  KEY `idx_rb_review_account` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='复盘报告';

INSERT INTO `rb_prompt_template` (`id`, `template_name`, `template_code`, `workflow_type`, `model_provider`, `prompt_content`, `output_schema`, `status`, `create_time`)
VALUES
('rb_prompt_hotspot_analysis', '热点分析模板', 'hotspot_analysis', 'hotspot_analysis', 'auto', '根据赛道、账号信息和热点内容，输出结构化的小红书热点分析结果。请严格按 JSON 返回，不要输出解释，不要使用 Markdown。', '{"pain_points":"","hook_analysis":"","content_angle":"","title_directions":[""],"outline_suggestion":"","cover_copy_suggestion":"","tag_suggestion":[""],"product_fit":"","risk_warning":"","originality_suggestion":"","score":0}', 'active', NOW()),
('rb_prompt_note_draft', '笔记草稿生成模板', 'note_draft', 'note_draft', 'auto', '根据热点分析结果生成小红书原创化笔记草稿。请严格按 JSON 返回，不要输出解释，不要使用 Markdown。', '{"title":"","cover_copy":"","body":"","tags":[""],"comment_guide":"","publish_time_suggestion":"","content_type":"","risk_check_result":""}', 'active', NOW()),
('rb_prompt_review_report', '复盘分析模板', 'review_report', 'review_report', 'auto', '根据发布内容、发布时间和数据回收结果，输出结构化复盘结论与下一轮建议。请严格按 JSON 返回，不要输出解释，不要使用 Markdown。', '{"summary":"","high_performing_factors":[""],"low_performing_reasons":[""],"reusable_topics":[""],"stopped_directions":[""],"next_topic_suggestions":[""],"next_title_suggestions":[""],"next_publish_suggestions":[""]}', 'active', NOW())
ON DUPLICATE KEY UPDATE `template_name` = VALUES(`template_name`), `prompt_content` = VALUES(`prompt_content`), `output_schema` = VALUES(`output_schema`);

-- ------------------------------------------------------------------
-- Jeecg 菜单初始化
-- 说明：
-- 1. 当前 JeecgBoot Vue3 项目运行在 BACK 后台权限模式。
-- 2. 左侧菜单来自 sys_permission，而不是前端本地路由文件。
-- 3. 这里会补齐“小红书运营”菜单，并默认授权给 admin / vue3 角色。
-- ------------------------------------------------------------------

INSERT INTO `sys_permission`
(`id`, `parent_id`, `name`, `url`, `component`, `is_route`, `component_name`, `redirect`, `menu_type`, `perms`, `perms_type`, `sort_no`, `always_show`, `icon`, `is_leaf`, `keep_alive`, `hidden`, `hide_tab`, `description`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`, `rule_flag`, `status`, `internal_or_external`)
VALUES
('9b0b9b0b000000000000000000000001', NULL, '小红书运营', '/redbook', 'layouts/default/index', 1, 'RedbookRoot', '/redbook/workbench', 0, NULL, '0', 2.50, 0, 'ant-design:fire-outlined', 0, 0, 0, 0, 'RedBook Agent 顶级菜单', 'admin', '2026-04-20 00:00:00', 'admin', '2026-04-20 00:00:00', 0, 0, '1', 0),
('9b0b9b0b000000000000000000000002', '9b0b9b0b000000000000000000000001', '运营工作台', '/redbook/workbench', 'redbook/workbench/index', 1, 'RedbookWorkbench', NULL, 1, NULL, '0', 0.00, 0, NULL, 1, 0, 0, 0, '小红书运营工作台', 'admin', '2026-04-20 00:00:00', 'admin', '2026-04-20 00:00:00', 0, 0, '1', 0),
('9b0b9b0b000000000000000000000003', '9b0b9b0b000000000000000000000001', '账号管理', '/redbook/account', 'redbook/crud/RedbookCrudPage', 1, 'RedbookAccount', NULL, 1, NULL, '0', 1.00, 0, NULL, 1, 0, 0, 0, '小红书账号管理', 'admin', '2026-04-20 00:00:00', 'admin', '2026-04-20 00:00:00', 0, 0, '1', 0),
('9b0b9b0b000000000000000000000004', '9b0b9b0b000000000000000000000001', '赛道管理', '/redbook/track', 'redbook/crud/RedbookCrudPage', 1, 'RedbookTrack', NULL, 1, NULL, '0', 2.00, 0, NULL, 1, 0, 0, 0, '小红书赛道管理', 'admin', '2026-04-20 00:00:00', 'admin', '2026-04-20 00:00:00', 0, 0, '1', 0),
('9b0b9b0b000000000000000000000005', '9b0b9b0b000000000000000000000001', '人设管理', '/redbook/persona', 'redbook/crud/RedbookCrudPage', 1, 'RedbookPersona', NULL, 1, NULL, '0', 3.00, 0, NULL, 1, 0, 0, 0, '小红书人设管理', 'admin', '2026-04-20 00:00:00', 'admin', '2026-04-20 00:00:00', 0, 0, '1', 0),
('9b0b9b0b000000000000000000000006', '9b0b9b0b000000000000000000000001', '产品卖点', '/redbook/product', 'redbook/crud/RedbookCrudPage', 1, 'RedbookProduct', NULL, 1, NULL, '0', 4.00, 0, NULL, 1, 0, 0, 0, '小红书产品卖点管理', 'admin', '2026-04-20 00:00:00', 'admin', '2026-04-20 00:00:00', 0, 0, '1', 0),
('9b0b9b0b000000000000000000000007', '9b0b9b0b000000000000000000000001', '提示词模板', '/redbook/prompt-template', 'redbook/crud/RedbookCrudPage', 1, 'RedbookPromptTemplate', NULL, 1, NULL, '0', 5.00, 0, NULL, 1, 0, 0, 0, '小红书提示词模板', 'admin', '2026-04-20 00:00:00', 'admin', '2026-04-20 00:00:00', 0, 0, '1', 0),
('9b0b9b0b000000000000000000000008', '9b0b9b0b000000000000000000000001', '敏感词', '/redbook/sensitive-word', 'redbook/crud/RedbookCrudPage', 1, 'RedbookSensitiveWord', NULL, 1, NULL, '0', 6.00, 0, NULL, 1, 0, 0, 0, '小红书敏感词管理', 'admin', '2026-04-20 00:00:00', 'admin', '2026-04-20 00:00:00', 0, 0, '1', 0),
('9b0b9b0b000000000000000000000009', '9b0b9b0b000000000000000000000001', '热点池', '/redbook/hotspot', 'redbook/crud/RedbookCrudPage', 1, 'RedbookHotspot', NULL, 1, NULL, '0', 7.00, 0, NULL, 1, 0, 0, 0, '小红书热点池', 'admin', '2026-04-20 00:00:00', 'admin', '2026-04-20 00:00:00', 0, 0, '1', 0),
('9b0b9b0b00000000000000000000000a', '9b0b9b0b000000000000000000000001', '热点分析', '/redbook/hotspot-analysis', 'redbook/crud/RedbookCrudPage', 1, 'RedbookHotspotAnalysis', NULL, 1, NULL, '0', 8.00, 0, NULL, 1, 0, 0, 0, '小红书热点分析', 'admin', '2026-04-20 00:00:00', 'admin', '2026-04-20 00:00:00', 0, 0, '1', 0),
('9b0b9b0b00000000000000000000000b', '9b0b9b0b000000000000000000000001', '笔记草稿', '/redbook/note-draft', 'redbook/crud/RedbookCrudPage', 1, 'RedbookNoteDraft', NULL, 1, NULL, '0', 9.00, 0, NULL, 1, 0, 0, 0, '小红书笔记草稿', 'admin', '2026-04-20 00:00:00', 'admin', '2026-04-20 00:00:00', 0, 0, '1', 0),
('9b0b9b0b00000000000000000000000c', '9b0b9b0b000000000000000000000001', '发布计划', '/redbook/publish-plan', 'redbook/crud/RedbookCrudPage', 1, 'RedbookPublishPlan', NULL, 1, NULL, '0', 10.00, 0, NULL, 1, 0, 0, 0, '小红书发布计划', 'admin', '2026-04-20 00:00:00', 'admin', '2026-04-20 00:00:00', 0, 0, '1', 0),
('9b0b9b0b00000000000000000000000f', '9b0b9b0b000000000000000000000001', '发布日历', '/redbook/publish-calendar', 'redbook/publish-calendar/index', 1, 'RedbookPublishCalendar', NULL, 1, NULL, '0', 10.50, 0, NULL, 1, 0, 0, 0, '小红书发布日历', 'admin', '2026-04-20 00:00:00', 'admin', '2026-04-20 00:00:00', 0, 0, '1', 0),
('9b0b9b0b00000000000000000000000d', '9b0b9b0b000000000000000000000001', '数据回收', '/redbook/note-metric', 'redbook/crud/RedbookCrudPage', 1, 'RedbookNoteMetric', NULL, 1, NULL, '0', 11.00, 0, NULL, 1, 0, 0, 0, '小红书数据回收', 'admin', '2026-04-20 00:00:00', 'admin', '2026-04-20 00:00:00', 0, 0, '1', 0),
('9b0b9b0b000000000000000000000010', '9b0b9b0b000000000000000000000001', '复盘看板', '/redbook/review-dashboard', 'redbook/review-dashboard/index', 1, 'RedbookReviewDashboard', NULL, 1, NULL, '0', 11.50, 0, NULL, 1, 0, 0, 0, '小红书复盘看板', 'admin', '2026-04-20 00:00:00', 'admin', '2026-04-20 00:00:00', 0, 0, '1', 0),
('9b0b9b0b00000000000000000000000e', '9b0b9b0b000000000000000000000001', '复盘报告', '/redbook/review-report', 'redbook/crud/RedbookCrudPage', 1, 'RedbookReviewReport', NULL, 1, NULL, '0', 12.00, 0, NULL, 1, 0, 0, 0, '小红书复盘报告', 'admin', '2026-04-20 00:00:00', 'admin', '2026-04-20 00:00:00', 0, 0, '1', 0)
ON DUPLICATE KEY UPDATE
`parent_id` = VALUES(`parent_id`),
`name` = VALUES(`name`),
`url` = VALUES(`url`),
`component` = VALUES(`component`),
`is_route` = VALUES(`is_route`),
`component_name` = VALUES(`component_name`),
`redirect` = VALUES(`redirect`),
`menu_type` = VALUES(`menu_type`),
`perms` = VALUES(`perms`),
`perms_type` = VALUES(`perms_type`),
`sort_no` = VALUES(`sort_no`),
`always_show` = VALUES(`always_show`),
`icon` = VALUES(`icon`),
`is_leaf` = VALUES(`is_leaf`),
`keep_alive` = VALUES(`keep_alive`),
`hidden` = VALUES(`hidden`),
`hide_tab` = VALUES(`hide_tab`),
`description` = VALUES(`description`),
`update_by` = 'admin',
`update_time` = NOW(),
`del_flag` = VALUES(`del_flag`),
`rule_flag` = VALUES(`rule_flag`),
`status` = VALUES(`status`),
`internal_or_external` = VALUES(`internal_or_external`);

-- 默认授权给管理员和 Vue3 角色，避免菜单入库后仍看不到入口。
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`, `data_rule_ids`, `operate_date`, `operate_ip`)
SELECT LOWER(CONCAT('9b', SUBSTRING(MD5(CONCAT(r.role_code, '-', '9b0b9b0b000000000000000000000001')), 1, 30))), r.id, '9b0b9b0b000000000000000000000001', NULL, NOW(), '127.0.0.1'
FROM `sys_role` r
WHERE r.role_code IN ('admin', 'vue3')
  AND NOT EXISTS (
    SELECT 1 FROM `sys_role_permission` srp WHERE srp.role_id = r.id AND srp.permission_id = '9b0b9b0b000000000000000000000001'
  );
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`, `data_rule_ids`, `operate_date`, `operate_ip`)
SELECT LOWER(CONCAT('9b', SUBSTRING(MD5(CONCAT(r.role_code, '-', '9b0b9b0b000000000000000000000002')), 1, 30))), r.id, '9b0b9b0b000000000000000000000002', NULL, NOW(), '127.0.0.1'
FROM `sys_role` r
WHERE r.role_code IN ('admin', 'vue3')
  AND NOT EXISTS (
    SELECT 1 FROM `sys_role_permission` srp WHERE srp.role_id = r.id AND srp.permission_id = '9b0b9b0b000000000000000000000002'
  );
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`, `data_rule_ids`, `operate_date`, `operate_ip`)
SELECT LOWER(CONCAT('9b', SUBSTRING(MD5(CONCAT(r.role_code, '-', '9b0b9b0b000000000000000000000003')), 1, 30))), r.id, '9b0b9b0b000000000000000000000003', NULL, NOW(), '127.0.0.1'
FROM `sys_role` r
WHERE r.role_code IN ('admin', 'vue3')
  AND NOT EXISTS (
    SELECT 1 FROM `sys_role_permission` srp WHERE srp.role_id = r.id AND srp.permission_id = '9b0b9b0b000000000000000000000003'
  );
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`, `data_rule_ids`, `operate_date`, `operate_ip`)
SELECT LOWER(CONCAT('9b', SUBSTRING(MD5(CONCAT(r.role_code, '-', '9b0b9b0b000000000000000000000004')), 1, 30))), r.id, '9b0b9b0b000000000000000000000004', NULL, NOW(), '127.0.0.1'
FROM `sys_role` r
WHERE r.role_code IN ('admin', 'vue3')
  AND NOT EXISTS (
    SELECT 1 FROM `sys_role_permission` srp WHERE srp.role_id = r.id AND srp.permission_id = '9b0b9b0b000000000000000000000004'
  );
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`, `data_rule_ids`, `operate_date`, `operate_ip`)
SELECT LOWER(CONCAT('9b', SUBSTRING(MD5(CONCAT(r.role_code, '-', '9b0b9b0b000000000000000000000005')), 1, 30))), r.id, '9b0b9b0b000000000000000000000005', NULL, NOW(), '127.0.0.1'
FROM `sys_role` r
WHERE r.role_code IN ('admin', 'vue3')
  AND NOT EXISTS (
    SELECT 1 FROM `sys_role_permission` srp WHERE srp.role_id = r.id AND srp.permission_id = '9b0b9b0b000000000000000000000005'
  );
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`, `data_rule_ids`, `operate_date`, `operate_ip`)
SELECT LOWER(CONCAT('9b', SUBSTRING(MD5(CONCAT(r.role_code, '-', '9b0b9b0b000000000000000000000006')), 1, 30))), r.id, '9b0b9b0b000000000000000000000006', NULL, NOW(), '127.0.0.1'
FROM `sys_role` r
WHERE r.role_code IN ('admin', 'vue3')
  AND NOT EXISTS (
    SELECT 1 FROM `sys_role_permission` srp WHERE srp.role_id = r.id AND srp.permission_id = '9b0b9b0b000000000000000000000006'
  );
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`, `data_rule_ids`, `operate_date`, `operate_ip`)
SELECT LOWER(CONCAT('9b', SUBSTRING(MD5(CONCAT(r.role_code, '-', '9b0b9b0b000000000000000000000007')), 1, 30))), r.id, '9b0b9b0b000000000000000000000007', NULL, NOW(), '127.0.0.1'
FROM `sys_role` r
WHERE r.role_code IN ('admin', 'vue3')
  AND NOT EXISTS (
    SELECT 1 FROM `sys_role_permission` srp WHERE srp.role_id = r.id AND srp.permission_id = '9b0b9b0b000000000000000000000007'
  );
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`, `data_rule_ids`, `operate_date`, `operate_ip`)
SELECT LOWER(CONCAT('9b', SUBSTRING(MD5(CONCAT(r.role_code, '-', '9b0b9b0b000000000000000000000008')), 1, 30))), r.id, '9b0b9b0b000000000000000000000008', NULL, NOW(), '127.0.0.1'
FROM `sys_role` r
WHERE r.role_code IN ('admin', 'vue3')
  AND NOT EXISTS (
    SELECT 1 FROM `sys_role_permission` srp WHERE srp.role_id = r.id AND srp.permission_id = '9b0b9b0b000000000000000000000008'
  );
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`, `data_rule_ids`, `operate_date`, `operate_ip`)
SELECT LOWER(CONCAT('9b', SUBSTRING(MD5(CONCAT(r.role_code, '-', '9b0b9b0b000000000000000000000009')), 1, 30))), r.id, '9b0b9b0b000000000000000000000009', NULL, NOW(), '127.0.0.1'
FROM `sys_role` r
WHERE r.role_code IN ('admin', 'vue3')
  AND NOT EXISTS (
    SELECT 1 FROM `sys_role_permission` srp WHERE srp.role_id = r.id AND srp.permission_id = '9b0b9b0b000000000000000000000009'
  );
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`, `data_rule_ids`, `operate_date`, `operate_ip`)
SELECT LOWER(CONCAT('9b', SUBSTRING(MD5(CONCAT(r.role_code, '-', '9b0b9b0b00000000000000000000000a')), 1, 30))), r.id, '9b0b9b0b00000000000000000000000a', NULL, NOW(), '127.0.0.1'
FROM `sys_role` r
WHERE r.role_code IN ('admin', 'vue3')
  AND NOT EXISTS (
    SELECT 1 FROM `sys_role_permission` srp WHERE srp.role_id = r.id AND srp.permission_id = '9b0b9b0b00000000000000000000000a'
  );
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`, `data_rule_ids`, `operate_date`, `operate_ip`)
SELECT LOWER(CONCAT('9b', SUBSTRING(MD5(CONCAT(r.role_code, '-', '9b0b9b0b00000000000000000000000b')), 1, 30))), r.id, '9b0b9b0b00000000000000000000000b', NULL, NOW(), '127.0.0.1'
FROM `sys_role` r
WHERE r.role_code IN ('admin', 'vue3')
  AND NOT EXISTS (
    SELECT 1 FROM `sys_role_permission` srp WHERE srp.role_id = r.id AND srp.permission_id = '9b0b9b0b00000000000000000000000b'
  );
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`, `data_rule_ids`, `operate_date`, `operate_ip`)
SELECT LOWER(CONCAT('9b', SUBSTRING(MD5(CONCAT(r.role_code, '-', '9b0b9b0b00000000000000000000000c')), 1, 30))), r.id, '9b0b9b0b00000000000000000000000c', NULL, NOW(), '127.0.0.1'
FROM `sys_role` r
WHERE r.role_code IN ('admin', 'vue3')
  AND NOT EXISTS (
    SELECT 1 FROM `sys_role_permission` srp WHERE srp.role_id = r.id AND srp.permission_id = '9b0b9b0b00000000000000000000000c'
  );
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`, `data_rule_ids`, `operate_date`, `operate_ip`)
SELECT LOWER(CONCAT('9b', SUBSTRING(MD5(CONCAT(r.role_code, '-', '9b0b9b0b00000000000000000000000d')), 1, 30))), r.id, '9b0b9b0b00000000000000000000000d', NULL, NOW(), '127.0.0.1'
FROM `sys_role` r
WHERE r.role_code IN ('admin', 'vue3')
  AND NOT EXISTS (
    SELECT 1 FROM `sys_role_permission` srp WHERE srp.role_id = r.id AND srp.permission_id = '9b0b9b0b00000000000000000000000d'
  );
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`, `data_rule_ids`, `operate_date`, `operate_ip`)
SELECT LOWER(CONCAT('9b', SUBSTRING(MD5(CONCAT(r.role_code, '-', '9b0b9b0b00000000000000000000000e')), 1, 30))), r.id, '9b0b9b0b00000000000000000000000e', NULL, NOW(), '127.0.0.1'
FROM `sys_role` r
WHERE r.role_code IN ('admin', 'vue3')
  AND NOT EXISTS (
    SELECT 1 FROM `sys_role_permission` srp WHERE srp.role_id = r.id AND srp.permission_id = '9b0b9b0b00000000000000000000000e'
  );
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`, `data_rule_ids`, `operate_date`, `operate_ip`)
SELECT LOWER(CONCAT('9b', SUBSTRING(MD5(CONCAT(r.role_code, '-', '9b0b9b0b00000000000000000000000f')), 1, 30))), r.id, '9b0b9b0b00000000000000000000000f', NULL, NOW(), '127.0.0.1'
FROM `sys_role` r
WHERE r.role_code IN ('admin', 'vue3')
  AND NOT EXISTS (
    SELECT 1 FROM `sys_role_permission` srp WHERE srp.role_id = r.id AND srp.permission_id = '9b0b9b0b00000000000000000000000f'
  );
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`, `data_rule_ids`, `operate_date`, `operate_ip`)
SELECT LOWER(CONCAT('9b', SUBSTRING(MD5(CONCAT(r.role_code, '-', '9b0b9b0b000000000000000000000010')), 1, 30))), r.id, '9b0b9b0b000000000000000000000010', NULL, NOW(), '127.0.0.1'
FROM `sys_role` r
WHERE r.role_code IN ('admin', 'vue3')
  AND NOT EXISTS (
    SELECT 1 FROM `sys_role_permission` srp WHERE srp.role_id = r.id AND srp.permission_id = '9b0b9b0b000000000000000000000010'
  );
