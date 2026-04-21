import { defHttp } from '/@/utils/http/axios';

export interface RedbookReviewRankItem {
  publishPlanId: string;
  draftId: string;
  title: string;
  trackId: string;
  trackName: string;
  accountId: string;
  accountName: string;
  publishTime: string;
  collectNode: string;
  views: number;
  likes: number;
  collects: number;
  comments: number;
  shares: number;
  interactionRate: number;
  collectRate: number;
  score: number;
  noteUrl: string;
}

export interface RedbookReviewDimension {
  dimensionId: string;
  dimensionName: string;
  publishCount: number;
  collectedCount: number;
  totalViews: number;
  avgViews: number;
  avgInteractionRate: number;
  score: number;
}

export interface RedbookReviewDashboard {
  publishCount: number;
  collectedPublishCount: number;
  uncollectedPublishCount: number;
  metricCount: number;
  reviewReportCount: number;
  avgViews: number;
  avgInteractionRate: number;
  avgCollectRate: number;
  avgCommentRate: number;
  latestReportName: string;
  latestSummary: string;
  highPerformList: RedbookReviewRankItem[];
  lowPerformList: RedbookReviewRankItem[];
  trackBoard: RedbookReviewDimension[];
  accountBoard: RedbookReviewDimension[];
  publishWindowBoard: RedbookReviewDimension[];
  nextTopicSuggestions: string[];
  nextTitleSuggestions: string[];
  nextPublishSuggestions: string[];
}

export function getRedbookReviewDashboard() {
  return defHttp.get<RedbookReviewDashboard>({
    url: '/redbook/workbench/reviewDashboard',
  });
}
