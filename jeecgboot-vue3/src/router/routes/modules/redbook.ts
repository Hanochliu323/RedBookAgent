import type { AppRouteModule } from '/@/router/types';
import { LAYOUT } from '/@/router/constant';

const redbook: AppRouteModule = {
  path: '/redbook',
  name: 'Redbook',
  component: LAYOUT,
  redirect: '/redbook/workbench',
  meta: {
    orderNo: 15,
    icon: 'ant-design:fire-outlined',
    title: '小红书运营',
  },
  children: [
    {
      path: 'workbench',
      name: 'RedbookWorkbench',
      component: () => import('/@/views/redbook/workbench/index.vue'),
      meta: { title: '运营工作台' },
    },
    {
      path: 'account',
      name: 'RedbookAccount',
      component: () => import('/@/views/redbook/crud/RedbookCrudPage.vue'),
      meta: { title: '账号管理', moduleKey: 'account' },
    },
    {
      path: 'track',
      name: 'RedbookTrack',
      component: () => import('/@/views/redbook/crud/RedbookCrudPage.vue'),
      meta: { title: '赛道管理', moduleKey: 'track' },
    },
    {
      path: 'persona',
      name: 'RedbookPersona',
      component: () => import('/@/views/redbook/crud/RedbookCrudPage.vue'),
      meta: { title: '人设管理', moduleKey: 'persona' },
    },
    {
      path: 'product',
      name: 'RedbookProduct',
      component: () => import('/@/views/redbook/crud/RedbookCrudPage.vue'),
      meta: { title: '产品卖点', moduleKey: 'product' },
    },
    {
      path: 'prompt-template',
      name: 'RedbookPromptTemplate',
      component: () => import('/@/views/redbook/crud/RedbookCrudPage.vue'),
      meta: { title: '提示词模板', moduleKey: 'promptTemplate' },
    },
    {
      path: 'sensitive-word',
      name: 'RedbookSensitiveWord',
      component: () => import('/@/views/redbook/crud/RedbookCrudPage.vue'),
      meta: { title: '敏感词', moduleKey: 'sensitiveWord' },
    },
    {
      path: 'hotspot',
      name: 'RedbookHotspot',
      component: () => import('/@/views/redbook/crud/RedbookCrudPage.vue'),
      meta: { title: '热点池', moduleKey: 'hotspot' },
    },
    {
      path: 'hotspot-analysis',
      name: 'RedbookHotspotAnalysis',
      component: () => import('/@/views/redbook/crud/RedbookCrudPage.vue'),
      meta: { title: '热点分析', moduleKey: 'hotspotAnalysis' },
    },
    {
      path: 'note-draft',
      name: 'RedbookNoteDraft',
      component: () => import('/@/views/redbook/crud/RedbookCrudPage.vue'),
      meta: { title: '笔记草稿', moduleKey: 'noteDraft' },
    },
    {
      path: 'publish-plan',
      name: 'RedbookPublishPlan',
      component: () => import('/@/views/redbook/crud/RedbookCrudPage.vue'),
      meta: { title: '发布计划', moduleKey: 'publishPlan' },
    },
    {
      path: 'note-metric',
      name: 'RedbookNoteMetric',
      component: () => import('/@/views/redbook/crud/RedbookCrudPage.vue'),
      meta: { title: '数据回收', moduleKey: 'noteMetric' },
    },
    {
      path: 'review-report',
      name: 'RedbookReviewReport',
      component: () => import('/@/views/redbook/crud/RedbookCrudPage.vue'),
      meta: { title: '复盘报告', moduleKey: 'reviewReport' },
    },
  ],
};

export default redbook;
