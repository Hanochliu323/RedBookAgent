<template>
  <PageWrapper title="发布日历">
    <div class="publish-calendar">
      <section class="calendar-toolbar">
        <div class="month-switcher">
          <a-button preIcon="ant-design:left-outlined" @click="shiftMonth(-1)" />
          <div class="month-title">{{ monthTitle }}</div>
          <a-button preIcon="ant-design:right-outlined" @click="shiftMonth(1)" />
          <a-button @click="backToday">本月</a-button>
        </div>
        <div class="toolbar-actions">
          <a-select v-model:value="statusFilter" class="status-select" :options="statusOptions" />
          <a-button preIcon="ant-design:reload-outlined" @click="loadPlans">刷新</a-button>
          <a-button type="primary" preIcon="ant-design:table-outlined" @click="goPublishPlan">列表</a-button>
        </div>
      </section>

      <section class="summary-grid">
        <button v-for="item in summaryCards" :key="item.key" class="summary-card" type="button" @click="statusFilter = item.filter">
          <span class="summary-label">{{ item.label }}</span>
          <span class="summary-value">{{ item.value }}</span>
        </button>
      </section>

      <section class="calendar-shell">
        <div class="week-grid">
          <div v-for="week in weeks" :key="week" class="week-cell">{{ week }}</div>
        </div>
        <a-spin :spinning="loading">
          <div class="day-grid">
            <div v-for="day in calendarDays" :key="day.key" class="day-cell" :class="{ muted: !day.isCurrentMonth, today: day.isToday }">
              <div class="day-head">
                <span>{{ day.label }}</span>
                <a-tag v-if="day.items.length" color="blue">{{ day.items.length }}</a-tag>
              </div>
              <div v-if="day.items.length" class="plan-list">
                <button v-for="item in day.items" :key="item.id" class="plan-item" type="button" @click="copyPlan(item)">
                  <div class="plan-time">{{ formatTime(item) }}</div>
                  <div class="plan-main">
                    <div class="plan-title">{{ item.draftId || '未绑定草稿' }}</div>
                    <div class="plan-meta">{{ item.accountId || '未绑定账号' }}</div>
                  </div>
                  <a-tag :color="statusMeta(item.publishStatus).color">{{ statusMeta(item.publishStatus).label }}</a-tag>
                </button>
              </div>
              <div v-else class="empty-day">-</div>
            </div>
          </div>
        </a-spin>
      </section>

      <a-empty v-if="!loading && filteredPlans.length === 0" class="empty-state" description="当前月份暂无发布排期" />
    </div>
  </PageWrapper>
</template>

<script lang="ts" setup>
  import dayjs, { Dayjs } from 'dayjs';
  import { computed, onMounted, ref } from 'vue';
  import { useRouter } from 'vue-router';
  import { PageWrapper } from '/@/components/Page';
  import { copyTextToClipboard } from '/@/hooks/web/useCopyToClipboard';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { listPublishPlans, type RedbookPublishPlanItem } from './publish-calendar.api';

  interface CalendarDay {
    key: string;
    label: string;
    isCurrentMonth: boolean;
    isToday: boolean;
    items: RedbookPublishPlanItem[];
  }

  const router = useRouter();
  const { createMessage } = useMessage();
  const loading = ref(false);
  const currentMonth = ref(dayjs().startOf('month'));
  const statusFilter = ref('all');
  const plans = ref<RedbookPublishPlanItem[]>([]);
  const weeks = ['日', '一', '二', '三', '四', '五', '六'];
  const statusOptions = [
    { label: '全部状态', value: 'all' },
    { label: '待发布', value: 'pending' },
    { label: '已发布', value: 'published' },
    { label: '延期', value: 'delayed' },
    { label: '取消', value: 'canceled' },
  ];

  const monthTitle = computed(() => currentMonth.value.format('YYYY年MM月'));

  const monthPlans = computed(() => {
    const monthStart = currentMonth.value.startOf('month');
    const monthEnd = currentMonth.value.endOf('month');
    return plans.value.filter((item) => {
      const date = resolvePlanDate(item);
      return date && !date.isBefore(monthStart, 'day') && !date.isAfter(monthEnd, 'day');
    });
  });

  const filteredPlans = computed(() => {
    if (statusFilter.value === 'all') {
      return monthPlans.value;
    }
    return monthPlans.value.filter((item) => item.publishStatus === statusFilter.value);
  });

  const summaryCards = computed(() => [
    { key: 'all', label: '本月排期', value: monthPlans.value.length, filter: 'all' },
    { key: 'pending', label: '待发布', value: countByStatus('pending'), filter: 'pending' },
    { key: 'published', label: '已发布', value: countByStatus('published'), filter: 'published' },
    { key: 'delayed', label: '延期', value: countByStatus('delayed'), filter: 'delayed' },
  ]);

  const calendarDays = computed<CalendarDay[]>(() => {
    const firstDay = currentMonth.value.startOf('month');
    const gridStart = firstDay.subtract(firstDay.day(), 'day');
    return Array.from({ length: 42 }).map((_, index) => {
      const day = gridStart.add(index, 'day');
      const key = day.format('YYYY-MM-DD');
      return {
        key,
        label: day.format('D'),
        isCurrentMonth: day.isSame(currentMonth.value, 'month'),
        isToday: day.isSame(dayjs(), 'day'),
        items: filteredPlans.value
          .filter((item) => resolvePlanDate(item)?.format('YYYY-MM-DD') === key)
          .sort((left, right) => sortByTime(left, right)),
      };
    });
  });

  onMounted(() => {
    loadPlans();
  });

  async function loadPlans() {
    loading.value = true;
    try {
      const data = await listPublishPlans();
      plans.value = data?.records || [];
    } catch (error) {
      createMessage.error('发布日历加载失败');
    } finally {
      loading.value = false;
    }
  }

  function shiftMonth(step: number) {
    currentMonth.value = currentMonth.value.add(step, 'month');
  }

  function backToday() {
    currentMonth.value = dayjs().startOf('month');
  }

  function countByStatus(status: string) {
    return monthPlans.value.filter((item) => item.publishStatus === status).length;
  }

  function resolvePlanDate(item: RedbookPublishPlanItem): Dayjs | null {
    const raw = item.actualPublishTime || item.plannedPublishTime;
    if (!raw) {
      return null;
    }
    const parsed = dayjs(raw);
    return parsed.isValid() ? parsed : null;
  }

  function sortByTime(left: RedbookPublishPlanItem, right: RedbookPublishPlanItem) {
    const leftDate = resolvePlanDate(left)?.valueOf() || 0;
    const rightDate = resolvePlanDate(right)?.valueOf() || 0;
    return leftDate - rightDate;
  }

  function formatTime(item: RedbookPublishPlanItem) {
    return resolvePlanDate(item)?.format('HH:mm') || '--:--';
  }

  function statusMeta(status?: string) {
    const map = {
      pending: { label: '待发布', color: 'orange' },
      published: { label: '已发布', color: 'green' },
      delayed: { label: '延期', color: 'red' },
      canceled: { label: '取消', color: 'default' },
      data_collected: { label: '已回收', color: 'blue' },
    };
    return map[status || ''] || { label: status || '未知', color: 'default' };
  }

  function copyPlan(item: RedbookPublishPlanItem) {
    const text = [
      `发布状态：${statusMeta(item.publishStatus).label}`,
      `计划时间：${item.plannedPublishTime || '未设置'}`,
      item.actualPublishTime ? `实际时间：${item.actualPublishTime}` : '',
      `草稿ID：${item.draftId || '未绑定'}`,
      `账号ID：${item.accountId || '未绑定'}`,
      item.noteUrl ? `笔记链接：${item.noteUrl}` : '',
      item.remark ? `备注：${item.remark}` : '',
    ]
      .filter(Boolean)
      .join('\n');
    copyTextToClipboard(text);
    createMessage.success('排期信息已复制');
  }

  function goPublishPlan() {
    router.push('/redbook/publish-plan');
  }
</script>

<style lang="less" scoped>
  .publish-calendar {
    padding: 16px;
    background: #f5f7fb;
  }

  .calendar-toolbar,
  .month-switcher,
  .toolbar-actions,
  .summary-card,
  .day-head,
  .plan-item {
    display: flex;
    align-items: center;
  }

  .calendar-toolbar {
    justify-content: space-between;
    gap: 16px;
  }

  .month-switcher,
  .toolbar-actions {
    gap: 8px;
  }

  .month-title {
    min-width: 132px;
    text-align: center;
    font-size: 20px;
    font-weight: 700;
    color: #0f172a;
  }

  .status-select {
    width: 128px;
  }

  .summary-grid {
    display: grid;
    grid-template-columns: repeat(4, minmax(0, 1fr));
    gap: 12px;
    margin-top: 16px;
  }

  .summary-card {
    justify-content: space-between;
    min-height: 72px;
    padding: 14px 16px;
    border: 1px solid #e5e7eb;
    border-radius: 8px;
    background: #fff;
    text-align: left;
    cursor: pointer;
  }

  .summary-label {
    color: #64748b;
  }

  .summary-value {
    font-size: 26px;
    font-weight: 700;
    color: #0f172a;
  }

  .calendar-shell {
    margin-top: 16px;
    overflow: hidden;
    border: 1px solid #e5e7eb;
    border-radius: 8px;
    background: #fff;
  }

  .week-grid,
  .day-grid {
    display: grid;
    grid-template-columns: repeat(7, minmax(0, 1fr));
  }

  .week-cell {
    padding: 12px;
    border-bottom: 1px solid #e5e7eb;
    background: #f8fafc;
    color: #475569;
    font-weight: 600;
    text-align: center;
  }

  .day-cell {
    min-height: 144px;
    padding: 10px;
    border-right: 1px solid #eef2f7;
    border-bottom: 1px solid #eef2f7;
    background: #fff;
  }

  .day-cell:nth-child(7n) {
    border-right: 0;
  }

  .day-cell.muted {
    background: #f8fafc;
    color: #94a3b8;
  }

  .day-cell.today {
    box-shadow: inset 0 0 0 2px #2563eb;
  }

  .day-head {
    justify-content: space-between;
    margin-bottom: 8px;
    font-weight: 600;
  }

  .plan-list {
    display: grid;
    gap: 6px;
  }

  .plan-item {
    width: 100%;
    gap: 8px;
    padding: 8px;
    border: 1px solid #dbeafe;
    border-radius: 8px;
    background: #eff6ff;
    color: #0f172a;
    text-align: left;
    cursor: pointer;
  }

  .plan-time {
    width: 42px;
    flex: 0 0 auto;
    color: #2563eb;
    font-weight: 700;
  }

  .plan-main {
    min-width: 0;
    flex: 1;
  }

  .plan-title,
  .plan-meta {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  .plan-title {
    font-weight: 600;
  }

  .plan-meta,
  .empty-day {
    color: #94a3b8;
    font-size: 12px;
  }

  .empty-day {
    padding-top: 18px;
    text-align: center;
  }

  .empty-state {
    margin-top: 24px;
  }

  @media (max-width: 960px) {
    .calendar-toolbar {
      align-items: stretch;
      flex-direction: column;
    }

    .summary-grid {
      grid-template-columns: repeat(2, minmax(0, 1fr));
    }

    .calendar-shell {
      overflow-x: auto;
    }

    .week-grid,
    .day-grid {
      min-width: 920px;
    }
  }
</style>
