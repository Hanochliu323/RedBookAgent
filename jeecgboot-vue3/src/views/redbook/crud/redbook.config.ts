import type { BasicColumn, FormSchema } from '/@/components/Table';

export interface RedbookModuleConfig {
  key: string;
  title: string;
  apiBase: string;
  columns: BasicColumn[];
  searchFormSchema: FormSchema[];
  formSchema: FormSchema[];
  actionColumnWidth?: number;
  rowActions?: RedbookRowActionConfig[];
}

export interface RedbookRowActionConfig {
  label: string;
  action: string;
  successMessage: string;
  actionType?: 'api' | 'copy';
  confirmTitle?: string;
  copyText?: (record: Recordable) => string;
  ifShow?: (record: Recordable) => boolean;
}

const idField: FormSchema = {
  label: 'ID',
  field: 'id',
  component: 'Input',
  show: false,
};

function col(title: string, dataIndex: string, width = 160): BasicColumn {
  return { title, dataIndex, width, align: 'left' };
}

function input(field: string, label: string, required = false): FormSchema {
  return { field, label, component: 'Input', required };
}

function textarea(field: string, label: string, required = false): FormSchema {
  return {
    field,
    label,
    component: 'InputTextArea',
    required,
    componentProps: { rows: 4 },
  };
}

function number(field: string, label: string): FormSchema {
  return { field, label, component: 'InputNumber', componentProps: { min: 0 } };
}

function dateTime(field: string, label: string): FormSchema {
  return {
    field,
    label,
    component: 'DatePicker',
    componentProps: {
      showTime: true,
      valueFormat: 'YYYY-MM-DD HH:mm:ss',
    },
  };
}

function date(field: string, label: string): FormSchema {
  return {
    field,
    label,
    component: 'DatePicker',
    componentProps: {
      valueFormat: 'YYYY-MM-DD',
    },
  };
}

function status(label = '状态'): FormSchema {
  return {
    field: 'status',
    label,
    component: 'Input',
  };
}

function search(field: string, label: string): FormSchema {
  return { field, label, component: 'Input', colProps: { span: 8 } };
}

function buildNotePublishText(record: Recordable): string {
  return [
    record.title,
    record.coverCopy ? `\n封面文案：${record.coverCopy}` : '',
    record.body ? `\n${record.body}` : '',
    record.tags ? `\n${record.tags}` : '',
    record.commentGuide ? `\n评论区引导：${record.commentGuide}` : '',
  ]
    .filter(Boolean)
    .join('\n')
    .trim();
}

export const redbookModuleConfigs: Record<string, RedbookModuleConfig> = {
  account: {
    key: 'account',
    title: '账号管理',
    apiBase: '/redbook/account',
    columns: [col('账号名称', 'accountName'), col('平台', 'platform', 100), col('账号定位', 'positioning', 220), col('目标人群', 'targetAudience', 220), col('内容风格', 'contentStyle', 180), col('状态', 'status', 100)],
    searchFormSchema: [search('accountName', '账号名称'), search('positioning', '账号定位')],
    formSchema: [idField, input('accountName', '账号名称', true), input('platform', '平台'), textarea('positioning', '账号定位'), textarea('targetAudience', '目标人群'), textarea('contentStyle', '内容风格'), input('primaryTrackId', '主要赛道ID'), status(), textarea('remark', '备注')],
  },
  track: {
    key: 'track',
    title: '赛道管理',
    apiBase: '/redbook/track',
    columns: [col('赛道名称', 'trackName'), col('关键词', 'keywords', 260), col('目标人群', 'targetAudience', 220), col('内容方向', 'contentDirection', 260), col('优先级', 'priority', 100), col('状态', 'status', 100)],
    searchFormSchema: [search('trackName', '赛道名称'), search('keywords', '关键词')],
    formSchema: [idField, input('trackName', '赛道名称', true), textarea('keywords', '关键词'), textarea('targetAudience', '目标人群'), textarea('contentDirection', '内容方向'), textarea('competitorAccounts', '竞品账号'), number('priority', '优先级'), status(), textarea('remark', '备注')],
  },
  persona: {
    key: 'persona',
    title: '人设管理',
    apiBase: '/redbook/persona',
    columns: [col('人设名称', 'personaName'), col('语气风格', 'toneStyle', 220), col('专业程度', 'professionalism', 120), col('目标用户', 'targetAudience', 220), col('状态', 'status', 100)],
    searchFormSchema: [search('personaName', '人设名称')],
    formSchema: [idField, input('personaName', '人设名称', true), textarea('toneStyle', '语气风格'), input('professionalism', '专业程度'), textarea('targetAudience', '目标用户'), textarea('forbiddenExpressions', '禁止表达'), textarea('commonExpressions', '常用表达'), status(), textarea('remark', '备注')],
  },
  product: {
    key: 'product',
    title: '产品卖点',
    apiBase: '/redbook/product',
    columns: [col('产品/服务名称', 'productName'), col('核心卖点', 'coreSellingPoints', 260), col('适用人群', 'targetAudience', 220), col('使用场景', 'scenarios', 220), col('状态', 'status', 100)],
    searchFormSchema: [search('productName', '产品/服务名称')],
    formSchema: [idField, input('productName', '产品/服务名称', true), textarea('coreSellingPoints', '核心卖点'), textarea('targetAudience', '适用人群'), textarea('scenarios', '使用场景'), textarea('advantages', '差异化优势'), textarea('forbiddenPromises', '禁止承诺'), status(), textarea('remark', '备注')],
  },
  promptTemplate: {
    key: 'promptTemplate',
    title: '提示词模板',
    apiBase: '/redbook/promptTemplate',
    columns: [col('模板名称', 'templateName'), col('模板编码', 'templateCode', 180), col('工作流类型', 'workflowType', 160), col('模型提供方', 'modelProvider', 140), col('状态', 'status', 100)],
    searchFormSchema: [search('templateName', '模板名称'), search('templateCode', '模板编码')],
    formSchema: [idField, input('templateName', '模板名称', true), input('templateCode', '模板编码', true), input('workflowType', '工作流类型'), input('modelProvider', '模型提供方'), textarea('promptContent', '提示词内容'), textarea('outputSchema', '输出格式'), status(), textarea('remark', '备注')],
  },
  sensitiveWord: {
    key: 'sensitiveWord',
    title: '敏感词',
    apiBase: '/redbook/sensitiveWord',
    columns: [col('词条', 'word'), col('分类', 'category', 120), col('风险等级', 'riskLevel', 120), col('替换建议', 'replacementSuggestion', 240), col('状态', 'status', 100)],
    searchFormSchema: [search('word', '词条'), search('category', '分类')],
    formSchema: [idField, input('word', '词条', true), input('category', '分类'), input('riskLevel', '风险等级'), textarea('replacementSuggestion', '替换建议'), status(), textarea('remark', '备注')],
  },
  hotspot: {
    key: 'hotspot',
    title: '热点池',
    apiBase: '/redbook/hotspot',
    columns: [col('热点标题', 'title', 280), col('赛道ID', 'trackId', 160), col('来源平台', 'sourcePlatform', 120), col('点赞数', 'likeCount', 100), col('收藏数', 'collectCount', 100), col('热度评分', 'heatScore', 110), col('状态', 'status', 150)],
    searchFormSchema: [search('title', '热点标题'), search('trackId', '赛道ID'), search('status', '状态')],
    formSchema: [idField, input('trackId', '赛道ID'), input('sourcePlatform', '来源平台'), input('title', '热点标题', true), textarea('summary', '热点摘要'), input('originalUrl', '原文链接'), input('authorName', '作者/账号'), number('likeCount', '点赞数'), number('collectCount', '收藏数'), number('commentCount', '评论数'), number('shareCount', '分享数'), dateTime('publishTime', '发布时间'), dateTime('collectTime', '采集时间'), textarea('tags', '标签'), number('heatScore', '热度评分'), number('remixScore', '可二创评分'), input('riskLevel', '风险等级'), status()],
    actionColumnWidth: 220,
    rowActions: [{ label: 'AI分析', action: 'analyze', successMessage: '热点分析已生成，可到热点分析查看结果' }],
  },
  hotspotAnalysis: {
    key: 'hotspotAnalysis',
    title: '热点分析',
    apiBase: '/redbook/hotspotAnalysis',
    columns: [col('热点ID', 'hotspotId', 180), col('用户痛点', 'painPoints', 240), col('内容角度', 'contentAngle', 240), col('综合评分', 'score', 110), col('状态', 'status', 120)],
    searchFormSchema: [search('hotspotId', '热点ID'), search('status', '状态')],
    formSchema: [idField, input('hotspotId', '热点ID', true), textarea('painPoints', '用户痛点'), textarea('hookAnalysis', '爆点拆解'), textarea('contentAngle', '内容角度'), textarea('titleDirections', '标题方向'), textarea('outlineSuggestion', '正文结构建议'), textarea('coverCopySuggestion', '封面文案建议'), textarea('tagSuggestion', '标签建议'), textarea('productFit', '产品植入建议'), textarea('riskWarning', '风险提示'), textarea('originalitySuggestion', '原创化建议'), number('score', '综合评分'), textarea('rawResult', 'AI原始输出'), status()],
    actionColumnWidth: 220,
    rowActions: [{ label: '生成草稿', action: 'generateDraft', successMessage: '笔记草稿已生成，可到笔记草稿继续处理' }],
  },
  noteDraft: {
    key: 'noteDraft',
    title: '笔记草稿',
    apiBase: '/redbook/noteDraft',
    columns: [col('标题', 'title', 280), col('赛道ID', 'trackId', 160), col('账号ID', 'accountId', 160), col('内容类型', 'contentType', 120), col('审核状态', 'auditStatus', 120), col('状态', 'status', 140)],
    searchFormSchema: [search('title', '标题'), search('trackId', '赛道ID'), search('accountId', '账号ID')],
    formSchema: [idField, input('hotspotId', '热点ID'), input('analysisId', '分析ID'), input('trackId', '赛道ID'), input('accountId', '账号ID'), input('title', '标题', true), input('coverCopy', '封面文案'), textarea('body', '正文'), textarea('tags', '标签'), textarea('commentGuide', '评论区引导语'), input('publishTimeSuggestion', '发布时间建议'), input('contentType', '内容类型'), input('aiVersion', 'AI版本'), input('manualVersion', '人工修改版本'), textarea('riskCheckResult', '风险检测结果'), input('auditStatus', '审核状态'), textarea('auditOpinion', '审核意见'), status()],
    actionColumnWidth: 220,
    rowActions: [
      {
        label: '复制文案',
        action: 'copyPublishText',
        actionType: 'copy',
        successMessage: '发布文案已复制',
        copyText: buildNotePublishText,
      },
      {
        label: '加入排期',
        action: 'createPublishPlan',
        successMessage: '发布计划已生成，可到发布计划安排发布时间',
        ifShow: (record) => record.status !== 'published',
      },
    ],
  },
  publishPlan: {
    key: 'publishPlan',
    title: '发布计划',
    apiBase: '/redbook/publishPlan',
    columns: [col('草稿ID', 'draftId', 180), col('账号ID', 'accountId', 180), col('计划发布时间', 'plannedPublishTime', 180), col('实际发布时间', 'actualPublishTime', 180), col('发布状态', 'publishStatus', 120), col('小红书笔记链接', 'noteUrl', 240)],
    searchFormSchema: [search('draftId', '草稿ID'), search('accountId', '账号ID'), search('publishStatus', '发布状态')],
    formSchema: [idField, input('draftId', '草稿ID', true), input('accountId', '账号ID'), dateTime('plannedPublishTime', '计划发布时间'), dateTime('actualPublishTime', '实际发布时间'), input('publishStatus', '发布状态'), input('noteUrl', '小红书笔记链接'), input('publisher', '发布人'), textarea('remark', '备注')],
    actionColumnWidth: 260,
    rowActions: [
      {
        label: '标记已发布',
        action: 'markPublished',
        successMessage: '发布状态已更新为已发布',
        confirmTitle: '确认将当前排期标记为已发布吗？',
        ifShow: (record) => !['published', 'data_collected'].includes(record.publishStatus),
      },
      {
        label: '生成回收记录',
        action: 'createMetric',
        successMessage: '数据回收记录已生成，可到数据回收页录入表现数据',
        ifShow: (record) => ['published', 'data_collected'].includes(record.publishStatus),
      },
    ],
  },
  noteMetric: {
    key: 'noteMetric',
    title: '数据回收',
    apiBase: '/redbook/noteMetric',
    columns: [col('发布计划ID', 'publishPlanId', 180), col('采集节点', 'collectNode', 100), col('曝光量', 'impressions', 100), col('阅读/播放量', 'views', 120), col('点赞数', 'likes', 100), col('收藏数', 'collects', 100), col('互动率', 'interactionRate', 100)],
    searchFormSchema: [search('publishPlanId', '发布计划ID'), search('collectNode', '采集节点')],
    formSchema: [idField, input('publishPlanId', '发布计划ID', true), input('noteDraftId', '草稿ID'), input('collectNode', '采集节点'), number('impressions', '曝光量'), number('views', '阅读/播放量'), number('likes', '点赞数'), number('collects', '收藏数'), number('comments', '评论数'), number('shares', '分享数'), number('followers', '关注数'), number('messages', '私信数'), number('leads', '线索数'), number('conversions', '转化数'), dateTime('collectTime', '采集时间'), number('interactionRate', '互动率'), number('collectRate', '收藏率'), number('commentRate', '评论率'), textarea('remark', '备注')],
  },
  reviewReport: {
    key: 'reviewReport',
    title: '复盘报告',
    apiBase: '/redbook/reviewReport',
    columns: [col('报告名称', 'reportName', 220), col('赛道ID', 'trackId', 160), col('账号ID', 'accountId', 160), col('开始日期', 'periodStart', 120), col('结束日期', 'periodEnd', 120), col('状态', 'status', 100)],
    searchFormSchema: [search('reportName', '报告名称'), search('trackId', '赛道ID')],
    formSchema: [idField, input('reportName', '报告名称', true), input('trackId', '赛道ID'), input('accountId', '账号ID'), date('periodStart', '开始日期'), date('periodEnd', '结束日期'), textarea('summary', '表现总结'), textarea('highPerformingFactors', '高表现因素'), textarea('lowPerformingReasons', '低表现原因'), textarea('reusableTopics', '可复用选题'), textarea('stoppedDirections', '停止方向'), textarea('nextTopicSuggestions', '下一轮选题建议'), textarea('nextTitleSuggestions', '下一轮标题建议'), textarea('nextPublishSuggestions', '下一轮发布时间建议'), textarea('rawResult', 'AI原始输出'), status()],
    actionColumnWidth: 260,
    rowActions: [
      { label: '生成复盘', action: 'generate', successMessage: '复盘报告已生成，可继续编辑和导出' },
      { label: '回流热点池', action: 'createHotspots', successMessage: '下一轮选题已回流热点池，可继续做热点分析' },
    ],
  },
};

export function getRedbookModuleConfig(key: string): RedbookModuleConfig {
  return redbookModuleConfigs[key] || redbookModuleConfigs.hotspot;
}

const routeKeyMap: Record<string, string> = {
  account: 'account',
  track: 'track',
  persona: 'persona',
  product: 'product',
  'prompt-template': 'promptTemplate',
  'sensitive-word': 'sensitiveWord',
  hotspot: 'hotspot',
  'hotspot-analysis': 'hotspotAnalysis',
  'note-draft': 'noteDraft',
  'publish-plan': 'publishPlan',
  'note-metric': 'noteMetric',
  'review-report': 'reviewReport',
};

export function resolveRedbookModuleKey(routePath: string, metaModuleKey?: string): string {
  if (metaModuleKey && redbookModuleConfigs[metaModuleKey]) {
    return metaModuleKey;
  }
  const matchedKey = routePath.split('/').filter(Boolean).pop() || 'hotspot';
  return routeKeyMap[matchedKey] || 'hotspot';
}
