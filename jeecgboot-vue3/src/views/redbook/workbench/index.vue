<template>
  <PageWrapper title="小红书运营工作台">
    <div class="redbook-workbench">
      <a-skeleton active :loading="loading" :paragraph="{ rows: 6 }">
        <section class="metrics-grid">
          <div v-for="item in metricCards" :key="item.key" class="metric-card" @click="go(item.path)">
            <div class="metric-head">
              <span class="metric-label">{{ item.label }}</span>
              <Icon :icon="item.icon" class="metric-icon" />
            </div>
            <div class="metric-value">{{ item.value }}</div>
            <div class="metric-desc">{{ item.desc }}</div>
          </div>
        </section>

        <section class="mid-grid">
          <div class="section-panel">
            <div class="section-head">
              <div>
                <div class="section-title">闭环推进</div>
                <div class="section-subtitle">把热点、草稿、排期压成可执行节奏</div>
              </div>
            </div>
            <div class="pipeline-list">
              <button
                v-for="step in pipelineSteps"
                :key="step.key"
                class="pipeline-item"
                type="button"
                @click="go(step.path)"
              >
                <div class="pipeline-left">
                  <div class="pipeline-index">{{ step.index }}</div>
                  <div>
                    <div class="pipeline-title">{{ step.title }}</div>
                    <div class="pipeline-desc">{{ step.desc }}</div>
                  </div>
                </div>
                <div class="pipeline-right">
                  <div class="pipeline-count">{{ step.count }}</div>
                  <div class="pipeline-label">{{ step.unit }}</div>
                </div>
              </button>
            </div>
          </div>

          <div class="section-panel">
            <div class="section-head">
              <div>
                <div class="section-title">当前待办</div>
                <div class="section-subtitle">优先清掉最影响闭环的节点</div>
              </div>
            </div>
            <div v-if="overview.todoList.length" class="todo-list">
              <button
                v-for="todo in overview.todoList"
                :key="`${todo.moduleTitle}-${todo.id}`"
                class="todo-item"
                type="button"
                @click="go(todo.routePath)"
              >
                <div class="todo-main">
                  <div class="todo-title">{{ todo.title }}</div>
                  <div class="todo-summary">{{ todo.summary }}</div>
                </div>
                <div class="todo-side">
                  <a-tag color="blue">{{ todo.moduleTitle }}</a-tag>
                  <span class="todo-status">{{ todo.status }}</span>
                </div>
              </button>
            </div>
            <a-empty v-else description="当前没有待办项" />
          </div>
        </section>

        <section class="lower-grid">
          <div class="section-panel">
            <div class="section-head">
              <div>
                <div class="section-title">赛道表现榜</div>
                <div class="section-subtitle">先看哪条赛道更值得继续加码</div>
              </div>
            </div>
            <div v-if="overview.trackBoard.length" class="track-list">
              <div v-for="item in overview.trackBoard" :key="item.trackId || item.trackName" class="track-item">
                <div class="track-row">
                  <div class="track-name">{{ item.trackName }}</div>
                  <div class="track-meta">
                    <span>热点 {{ item.hotspotCount }}</span>
                    <span>草稿 {{ item.draftCount }}</span>
                    <span>已发 {{ item.publishedCount }}</span>
                  </div>
                </div>
                <a-progress
                  :percent="buildTrackPercent(item)"
                  :show-info="false"
                  stroke-color="#2563eb"
                  trail-color="#dbeafe"
                />
              </div>
            </div>
            <a-empty v-else description="先补充赛道与热点数据" />
          </div>

          <div class="section-panel">
            <div class="section-head">
              <div>
                <div class="section-title">模块导航</div>
                <div class="section-subtitle">日常运营入口都在这里</div>
              </div>
            </div>
            <div class="module-grid">
              <button v-for="item in modules" :key="item.path" class="module-card" type="button" @click="go(item.path)">
                <div class="module-title-row">
                  <div class="module-title">{{ item.title }}</div>
                  <span v-if="item.badge" class="module-badge">{{ item.badge }}</span>
                </div>
                <div class="module-desc">{{ item.desc }}</div>
              </button>
            </div>
          </div>
        </section>
      </a-skeleton>
    </div>
  </PageWrapper>
</template>

<script lang="ts" setup>
  import { computed, onMounted, reactive, ref } from 'vue';
  import { useRouter } from 'vue-router';
  import { PageWrapper } from '/@/components/Page';
  import { Icon } from '/@/components/Icon';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { getRedbookWorkbenchOverview } from './workbench.api';

  interface TodoItem {
    id: string;
    title: string;
    moduleTitle: string;
    routePath: string;
    status: string;
    summary: string;
    updateTime?: string;
  }

  interface TrackBoardItem {
    trackId: string;
    trackName: string;
    hotspotCount: number;
    draftCount: number;
    publishedCount: number;
  }

  interface OverviewState {
    trackCount: number;
    hotspotCount: number;
    analysisCount: number;
    draftCount: number;
    publishPlanCount: number;
    metricCount: number;
    reviewReportCount: number;
    pendingHotspotCount: number;
    pendingReviewCount: number;
    pendingPublishCount: number;
    publishedCount: number;
    avgHeatScore: number;
    avgInteractionRate: number;
    todoList: TodoItem[];
    trackBoard: TrackBoardItem[];
  }

  const router = useRouter();
  const { createMessage } = useMessage();
  const loading = ref(false);
  const overview = reactive<OverviewState>({
    trackCount: 0,
    hotspotCount: 0,
    analysisCount: 0,
    draftCount: 0,
    publishPlanCount: 0,
    metricCount: 0,
    reviewReportCount: 0,
    pendingHotspotCount: 0,
    pendingReviewCount: 0,
    pendingPublishCount: 0,
    publishedCount: 0,
    avgHeatScore: 0,
    avgInteractionRate: 0,
    todoList: [],
    trackBoard: [],
  });

  const metricCards = computed(() => [
    {
      key: 'pendingHotspot',
      label: '待分析热点',
      value: overview.pendingHotspotCount,
      desc: `${overview.hotspotCount} 条热点已入池`,
      icon: 'ant-design:fire-outlined',
      path: '/redbook/hotspot',
    },
    {
      key: 'pendingReview',
      label: '待审核草稿',
      value: overview.pendingReviewCount,
      desc: `${overview.draftCount} 条草稿可继续流转`,
      icon: 'ant-design:file-text-outlined',
      path: '/redbook/note-draft',
    },
    {
      key: 'pendingPublish',
      label: '待发布排期',
      value: overview.pendingPublishCount,
      desc: `${overview.publishPlanCount} 条排期已创建`,
      icon: 'ant-design:calendar-outlined',
      path: '/redbook/publish-plan',
    },
    {
      key: 'published',
      label: '已发布内容',
      value: overview.publishedCount,
      desc: `${overview.metricCount} 条数据回收记录`,
      icon: 'ant-design:check-circle-outlined',
      path: '/redbook/publish-plan',
    },
    {
      key: 'heat',
      label: '平均热度',
      value: Number(overview.avgHeatScore || 0).toFixed(1),
      desc: '热点池整体质量基准线',
      icon: 'ant-design:line-chart-outlined',
      path: '/redbook/hotspot',
    },
    {
      key: 'interaction',
      label: '平均互动率',
      value: `${Number(overview.avgInteractionRate || 0).toFixed(2)}`,
      desc: '基于已回收内容数据',
      icon: 'ant-design:bar-chart-outlined',
      path: '/redbook/note-metric',
    },
  ]);

  const pipelineSteps = computed(() => [
    {
      key: 'hotspot',
      index: '01',
      title: '热点入池',
      desc: '人工录入或 Excel 导入热点候选',
      count: overview.hotspotCount,
      unit: '累计',
      path: '/redbook/hotspot',
    },
    {
      key: 'analysis',
      index: '02',
      title: '热点分析',
      desc: '基于热点生成选题判断与风险提示',
      count: overview.analysisCount,
      unit: '已分析',
      path: '/redbook/hotspot-analysis',
    },
    {
      key: 'draft',
      index: '03',
      title: '笔记草稿',
      desc: '沉淀标题、正文、封面和标签',
      count: overview.draftCount,
      unit: '草稿',
      path: '/redbook/note-draft',
    },
    {
      key: 'publish',
      index: '04',
      title: '发布排期',
      desc: '安排发布时间并跟踪发布状态',
      count: overview.publishPlanCount,
      unit: '排期',
      path: '/redbook/publish-plan',
    },
  ]);

  const modules = computed(() => [
    { title: '账号管理', path: '/redbook/account', desc: '维护账号定位、人群和内容风格', badge: '' },
    { title: '赛道管理', path: '/redbook/track', desc: '配置固定赛道、关键词和竞品账号', badge: `${overview.trackCount}` },
    { title: '热点池', path: '/redbook/hotspot', desc: '沉淀热点素材并推进分析', badge: overview.pendingHotspotCount ? `待分析 ${overview.pendingHotspotCount}` : '' },
    { title: '热点分析', path: '/redbook/hotspot-analysis', desc: '输出选题角度、风险提示与评分', badge: `${overview.analysisCount}` },
    { title: '笔记草稿', path: '/redbook/note-draft', desc: '沉淀标题、正文、封面文案和标签', badge: overview.pendingReviewCount ? `待审 ${overview.pendingReviewCount}` : '' },
    { title: '发布计划', path: '/redbook/publish-plan', desc: '安排半自动发布排期与执行节奏', badge: overview.pendingPublishCount ? `待发 ${overview.pendingPublishCount}` : '' },
    { title: '发布日历', path: '/redbook/publish-calendar', desc: '按月份查看待发布和已发布内容', badge: overview.pendingPublishCount ? `${overview.pendingPublishCount}` : '' },
    { title: '数据回收', path: '/redbook/note-metric', desc: '录入 2h、24h、72h、7d 运营数据', badge: `${overview.metricCount}` },
    { title: '复盘看板', path: '/redbook/review-dashboard', desc: '查看高低表现、赛道和账号表现', badge: '' },
    { title: '复盘报告', path: '/redbook/review-report', desc: '沉淀表现分析和下一轮选题建议', badge: `${overview.reviewReportCount}` },
    { title: '提示词模板', path: '/redbook/prompt-template', desc: '维护 Dify / FastGPT 工作流提示词', badge: '' },
  ]);

  onMounted(() => {
    loadOverview();
  });

  async function loadOverview() {
    loading.value = true;
    try {
      const data = await getRedbookWorkbenchOverview();
      Object.assign(overview, data || {});
    } catch (error) {
      createMessage.error('工作台概览加载失败，请稍后重试');
    } finally {
      loading.value = false;
    }
  }

  function buildTrackPercent(item: TrackBoardItem) {
    const score = item.publishedCount * 100 + item.draftCount * 20 + item.hotspotCount * 5;
    return Math.min(100, Math.max(8, score));
  }

  function go(path: string) {
    router.push(path);
  }
</script>

<style lang="less" scoped>
  .redbook-workbench {
    padding: 16px;
    background: #f5f7fb;
  }

  .metrics-grid,
  .mid-grid,
  .lower-grid {
    display: grid;
    gap: 16px;
  }

  .metrics-grid {
    grid-template-columns: repeat(6, minmax(0, 1fr));
  }

  .mid-grid,
  .lower-grid {
    margin-top: 16px;
    grid-template-columns: minmax(0, 1.2fr) minmax(0, 1fr);
  }

  .metric-card,
  .section-panel,
  .module-card {
    border: 1px solid #e5e7eb;
    border-radius: 8px;
    background: #fff;
  }

  .metric-card {
    padding: 18px;
    text-align: left;
    cursor: pointer;
    transition: transform 0.18s ease, box-shadow 0.18s ease;
  }

  .metric-card:hover,
  .module-card:hover,
  .pipeline-item:hover,
  .todo-item:hover {
    transform: translateY(-2px);
    box-shadow: 0 10px 24px rgb(15 23 42 / 8%);
  }

  .metric-head,
  .track-row,
  .module-title-row,
  .section-head,
  .todo-item,
  .pipeline-item {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }

  .metric-label,
  .section-subtitle,
  .metric-desc,
  .todo-summary,
  .pipeline-desc {
    color: #64748b;
  }

  .metric-icon {
    font-size: 18px;
    color: #2563eb;
  }

  .metric-value {
    margin-top: 14px;
    font-size: 30px;
    font-weight: 700;
    color: #0f172a;
    line-height: 1;
  }

  .metric-desc {
    margin-top: 10px;
    font-size: 13px;
    line-height: 1.5;
  }

  .section-panel {
    padding: 18px;
  }

  .section-head {
    margin-bottom: 16px;
  }

  .section-title {
    font-size: 18px;
    font-weight: 600;
    color: #0f172a;
  }

  .section-subtitle {
    margin-top: 4px;
    font-size: 13px;
  }

  .pipeline-list,
  .todo-list,
  .track-list,
  .module-grid {
    display: grid;
    gap: 12px;
  }

  .pipeline-item,
  .todo-item,
  .module-card {
    width: 100%;
    padding: 14px 16px;
    text-align: left;
    cursor: pointer;
    transition: transform 0.18s ease, box-shadow 0.18s ease;
  }

  .pipeline-item,
  .todo-item,
  .module-card {
    border: 1px solid #e5e7eb;
    border-radius: 8px;
    background: #fff;
  }

  .pipeline-left,
  .todo-main {
    display: flex;
    gap: 12px;
    min-width: 0;
  }

  .pipeline-index {
    width: 32px;
    height: 32px;
    border-radius: 8px;
    display: inline-flex;
    align-items: center;
    justify-content: center;
    background: #eff6ff;
    color: #2563eb;
    font-weight: 600;
    flex-shrink: 0;
  }

  .pipeline-title,
  .todo-title,
  .track-name,
  .module-title {
    font-size: 15px;
    font-weight: 600;
    color: #0f172a;
  }

  .pipeline-desc,
  .todo-summary,
  .module-desc {
    margin-top: 4px;
    font-size: 13px;
    line-height: 1.6;
  }

  .pipeline-right {
    text-align: right;
    flex-shrink: 0;
  }

  .pipeline-count {
    font-size: 22px;
    font-weight: 700;
    color: #111827;
    line-height: 1;
  }

  .pipeline-label,
  .todo-status,
  .track-meta {
    margin-top: 6px;
    font-size: 12px;
    color: #64748b;
  }

  .todo-item {
    gap: 16px;
  }

  .todo-main {
    min-width: 0;
  }

  .todo-title,
  .todo-summary {
    overflow: hidden;
    text-overflow: ellipsis;
  }

  .todo-side {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    gap: 8px;
    flex-shrink: 0;
  }

  .track-meta {
    display: flex;
    gap: 14px;
    margin-top: 0;
  }

  .track-item + .track-item {
    margin-top: 4px;
  }

  .module-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }

  .module-badge {
    min-width: 52px;
    height: 24px;
    padding: 0 8px;
    border-radius: 999px;
    background: #eff6ff;
    color: #1d4ed8;
    font-size: 12px;
    line-height: 24px;
    text-align: center;
    flex-shrink: 0;
  }

  @media (max-width: 1440px) {
    .metrics-grid {
      grid-template-columns: repeat(3, minmax(0, 1fr));
    }
  }

  @media (max-width: 992px) {
    .mid-grid,
    .lower-grid,
    .module-grid {
      grid-template-columns: 1fr;
    }
  }

  @media (max-width: 768px) {
    .redbook-workbench {
      padding: 12px;
    }

    .metrics-grid {
      grid-template-columns: repeat(2, minmax(0, 1fr));
    }

    .todo-item,
    .pipeline-item {
      align-items: flex-start;
      flex-direction: column;
    }

    .todo-side,
    .pipeline-right {
      align-items: flex-start;
      text-align: left;
    }
  }
</style>
