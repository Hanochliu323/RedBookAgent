<template>
  <PageWrapper title="复盘看板">
    <div class="review-dashboard">
      <a-skeleton active :loading="loading" :paragraph="{ rows: 8 }">
        <section class="summary-grid">
          <button v-for="item in summaryCards" :key="item.key" class="summary-card" type="button" @click="go(item.path)">
            <div>
              <div class="summary-label">{{ item.label }}</div>
              <div class="summary-desc">{{ item.desc }}</div>
            </div>
            <div class="summary-value">{{ item.value }}</div>
          </button>
        </section>

        <section class="report-panel">
          <div class="panel-head">
            <div>
              <div class="panel-title">{{ dashboard.latestReportName || '暂无复盘报告' }}</div>
              <div class="panel-subtitle">最新报告摘要</div>
            </div>
            <div class="panel-actions">
              <a-button preIcon="ant-design:reload-outlined" @click="loadDashboard">刷新</a-button>
              <a-button type="primary" preIcon="ant-design:file-text-outlined" @click="go('/redbook/review-report')">复盘报告</a-button>
            </div>
          </div>
          <p class="report-summary">{{ dashboard.latestSummary || '先生成一份复盘报告，看板会自动展示下一轮选题、标题和发布时间建议。' }}</p>
        </section>

        <section class="rank-grid">
          <div class="panel">
            <div class="panel-head">
              <div>
                <div class="panel-title">高表现笔记榜</div>
                <div class="panel-subtitle">按阅读、收藏和互动综合排序</div>
              </div>
            </div>
            <div v-if="dashboard.highPerformList.length" class="rank-list">
              <button v-for="item in dashboard.highPerformList" :key="`high-${item.publishPlanId}`" class="rank-item" type="button" @click="openNote(item)">
                <div class="rank-main">
                  <div class="rank-title">{{ item.title }}</div>
                  <div class="rank-meta">{{ item.trackName }} / {{ item.accountName }} / {{ item.collectNode || '最新' }}</div>
                </div>
                <div class="rank-side">
                  <strong>{{ formatNumber(item.views) }}</strong>
                  <span>{{ formatPercent(item.interactionRate) }}</span>
                </div>
              </button>
            </div>
            <a-empty v-else description="暂无已回收数据" />
          </div>

          <div class="panel">
            <div class="panel-head">
              <div>
                <div class="panel-title">低表现笔记榜</div>
                <div class="panel-subtitle">优先复查标题、开头和评论引导</div>
              </div>
            </div>
            <div v-if="dashboard.lowPerformList.length" class="rank-list">
              <button v-for="item in dashboard.lowPerformList" :key="`low-${item.publishPlanId}`" class="rank-item" type="button" @click="openNote(item)">
                <div class="rank-main">
                  <div class="rank-title">{{ item.title }}</div>
                  <div class="rank-meta">{{ item.trackName }} / {{ item.accountName }} / {{ item.collectNode || '最新' }}</div>
                </div>
                <div class="rank-side danger">
                  <strong>{{ formatNumber(item.views) }}</strong>
                  <span>{{ formatPercent(item.interactionRate) }}</span>
                </div>
              </button>
            </div>
            <a-empty v-else description="暂无已回收数据" />
          </div>
        </section>

        <section class="dimension-grid">
          <DimensionBoard title="赛道表现" :items="dashboard.trackBoard" />
          <DimensionBoard title="账号表现" :items="dashboard.accountBoard" />
          <DimensionBoard title="发布时间表现" :items="dashboard.publishWindowBoard" />
        </section>

        <section class="suggestion-grid">
          <SuggestionPanel title="下一轮选题" :items="dashboard.nextTopicSuggestions" />
          <SuggestionPanel title="下一轮标题" :items="dashboard.nextTitleSuggestions" />
          <SuggestionPanel title="发布时间建议" :items="dashboard.nextPublishSuggestions" />
        </section>
      </a-skeleton>
    </div>
  </PageWrapper>
</template>

<script lang="ts" setup>
  import { computed, defineComponent, h, onMounted, reactive, ref } from 'vue';
  import { useRouter } from 'vue-router';
  import { PageWrapper } from '/@/components/Page';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { getRedbookReviewDashboard, type RedbookReviewDashboard, type RedbookReviewDimension, type RedbookReviewRankItem } from './review-dashboard.api';

  const DimensionBoard = defineComponent({
    name: 'DimensionBoard',
    props: {
      title: { type: String, required: true },
      items: { type: Array as () => RedbookReviewDimension[], default: () => [] },
    },
    setup(props) {
      return () =>
        h('div', { class: 'panel' }, [
          h('div', { class: 'panel-head' }, [
            h('div', [h('div', { class: 'panel-title' }, props.title), h('div', { class: 'panel-subtitle' }, '发布量、回收量和平均互动率')]),
          ]),
          props.items.length
            ? h(
                'div',
                { class: 'dimension-list' },
                props.items.map((item) =>
                  h('div', { class: 'dimension-item', key: item.dimensionId }, [
                    h('div', { class: 'dimension-row' }, [
                      h('strong', item.dimensionName),
                      h('span', `${formatNumber(item.avgViews)} / ${formatPercent(item.avgInteractionRate)}`),
                    ]),
                    h('div', { class: 'dimension-bar' }, [h('span', { style: { width: `${dimensionPercent(item)}%` } })]),
                    h('div', { class: 'dimension-meta' }, `发布 ${item.publishCount} / 回收 ${item.collectedCount} / 总阅读 ${formatNumber(item.totalViews)}`),
                  ])
                )
              )
            : h('div', { class: 'empty-lite' }, '暂无数据'),
        ]);
    },
  });

  const SuggestionPanel = defineComponent({
    name: 'SuggestionPanel',
    props: {
      title: { type: String, required: true },
      items: { type: Array as () => string[], default: () => [] },
    },
    setup(props) {
      return () =>
        h('div', { class: 'panel' }, [
          h('div', { class: 'panel-head' }, [h('div', [h('div', { class: 'panel-title' }, props.title), h('div', { class: 'panel-subtitle' }, '来自最新复盘报告')])]),
          props.items.length
            ? h(
                'div',
                { class: 'suggestion-list' },
                props.items.map((item, index) => h('div', { class: 'suggestion-item', key: `${props.title}-${index}` }, [h('span', `${index + 1}`), h('p', item)]))
              )
            : h('div', { class: 'empty-lite' }, '暂无建议'),
        ]);
    },
  });

  const router = useRouter();
  const { createMessage } = useMessage();
  const loading = ref(false);
  const dashboard = reactive<RedbookReviewDashboard>({
    publishCount: 0,
    collectedPublishCount: 0,
    uncollectedPublishCount: 0,
    metricCount: 0,
    reviewReportCount: 0,
    avgViews: 0,
    avgInteractionRate: 0,
    avgCollectRate: 0,
    avgCommentRate: 0,
    latestReportName: '',
    latestSummary: '',
    highPerformList: [],
    lowPerformList: [],
    trackBoard: [],
    accountBoard: [],
    publishWindowBoard: [],
    nextTopicSuggestions: [],
    nextTitleSuggestions: [],
    nextPublishSuggestions: [],
  });

  const summaryCards = computed(() => [
    { key: 'publish', label: '发布总数', value: dashboard.publishCount, desc: '进入复盘样本池', path: '/redbook/publish-plan' },
    { key: 'collected', label: '已回收', value: dashboard.collectedPublishCount, desc: `未回收 ${dashboard.uncollectedPublishCount}`, path: '/redbook/note-metric' },
    { key: 'views', label: '平均阅读', value: formatNumber(dashboard.avgViews), desc: `${dashboard.metricCount} 条回收记录`, path: '/redbook/note-metric' },
    { key: 'interaction', label: '平均互动率', value: formatPercent(dashboard.avgInteractionRate), desc: `收藏率 ${formatPercent(dashboard.avgCollectRate)}`, path: '/redbook/review-report' },
  ]);

  onMounted(() => {
    loadDashboard();
  });

  async function loadDashboard() {
    loading.value = true;
    try {
      const data = await getRedbookReviewDashboard();
      Object.assign(dashboard, data || {});
    } catch (error) {
      createMessage.error('复盘看板加载失败');
    } finally {
      loading.value = false;
    }
  }

  function go(path: string) {
    router.push(path);
  }

  function openNote(item: RedbookReviewRankItem) {
    if (item.noteUrl) {
      window.open(item.noteUrl, '_blank');
      return;
    }
    router.push('/redbook/note-metric');
  }

  function formatNumber(value?: number) {
    const numericValue = Number(value || 0);
    if (numericValue >= 10000) {
      return `${(numericValue / 10000).toFixed(1)}w`;
    }
    return numericValue.toFixed(numericValue % 1 === 0 ? 0 : 1);
  }

  function formatPercent(value?: number) {
    return `${(Number(value || 0) * 100).toFixed(2)}%`;
  }

  function dimensionPercent(item: RedbookReviewDimension) {
    return Math.min(100, Math.max(8, Number(item.score || 0) / 100));
  }
</script>

<style lang="less" scoped>
  .review-dashboard {
    padding: 16px;
    background: #f5f7fb;
  }

  .summary-grid,
  .rank-grid,
  .dimension-grid,
  .suggestion-grid {
    display: grid;
    gap: 16px;
  }

  .summary-grid {
    grid-template-columns: repeat(4, minmax(0, 1fr));
  }

  .rank-grid {
    margin-top: 16px;
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }

  .dimension-grid,
  .suggestion-grid {
    margin-top: 16px;
    grid-template-columns: repeat(3, minmax(0, 1fr));
  }

  .summary-card,
  .report-panel,
  .panel {
    border: 1px solid #e5e7eb;
    border-radius: 8px;
    background: #fff;
  }

  .summary-card {
    display: flex;
    min-height: 104px;
    justify-content: space-between;
    align-items: center;
    padding: 18px;
    text-align: left;
    cursor: pointer;
  }

  .summary-label,
  .panel-subtitle,
  .rank-meta,
  .dimension-meta {
    color: #64748b;
  }

  .summary-label {
    font-size: 14px;
    font-weight: 600;
  }

  .summary-value {
    color: #0f172a;
    font-size: 28px;
    font-weight: 800;
  }

  .summary-desc {
    margin-top: 8px;
    color: #94a3b8;
  }

  .report-panel,
  .panel {
    padding: 18px;
  }

  .report-panel {
    margin-top: 16px;
  }

  .panel-head {
    display: flex;
    justify-content: space-between;
    gap: 12px;
    align-items: flex-start;
  }

  .panel-title {
    color: #0f172a;
    font-size: 17px;
    font-weight: 800;
  }

  .panel-actions {
    display: flex;
    gap: 8px;
  }

  .report-summary {
    margin: 14px 0 0;
    color: #334155;
    line-height: 1.8;
    white-space: pre-wrap;
  }

  .rank-list,
  .dimension-list,
  .suggestion-list {
    display: grid;
    gap: 10px;
    margin-top: 14px;
  }

  .rank-item {
    display: flex;
    width: 100%;
    justify-content: space-between;
    gap: 12px;
    padding: 12px;
    border: 1px solid #e5e7eb;
    border-radius: 8px;
    background: #f8fafc;
    text-align: left;
    cursor: pointer;
  }

  .rank-title {
    color: #0f172a;
    font-weight: 700;
  }

  .rank-side {
    display: grid;
    min-width: 88px;
    justify-items: end;
    color: #0f766e;
  }

  .rank-side.danger {
    color: #b91c1c;
  }

  .dimension-item {
    padding: 12px;
    border: 1px solid #e5e7eb;
    border-radius: 8px;
    background: #f8fafc;
  }

  .dimension-row {
    display: flex;
    justify-content: space-between;
    gap: 12px;
    color: #0f172a;
  }

  .dimension-bar {
    height: 8px;
    margin: 10px 0;
    overflow: hidden;
    border-radius: 999px;
    background: #e2e8f0;
  }

  .dimension-bar span {
    display: block;
    height: 100%;
    border-radius: inherit;
    background: #2563eb;
  }

  .suggestion-item {
    display: grid;
    grid-template-columns: 28px minmax(0, 1fr);
    gap: 10px;
    align-items: flex-start;
    padding: 12px;
    border: 1px solid #e5e7eb;
    border-radius: 8px;
    background: #f8fafc;
  }

  .suggestion-item span {
    display: grid;
    width: 28px;
    height: 28px;
    place-items: center;
    border-radius: 50%;
    background: #dbeafe;
    color: #1d4ed8;
    font-weight: 700;
  }

  .suggestion-item p {
    margin: 0;
    color: #334155;
    line-height: 1.7;
  }

  .empty-lite {
    margin-top: 14px;
    color: #94a3b8;
  }

  @media only screen and (max-width: 1200px) {
    .summary-grid,
    .rank-grid,
    .dimension-grid,
    .suggestion-grid {
      grid-template-columns: repeat(2, minmax(0, 1fr));
    }
  }

  @media only screen and (max-width: 720px) {
    .summary-grid,
    .rank-grid,
    .dimension-grid,
    .suggestion-grid {
      grid-template-columns: 1fr;
    }

    .panel-head,
    .summary-card {
      flex-direction: column;
      align-items: flex-start;
    }
  }
</style>
