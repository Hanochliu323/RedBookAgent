import { defHttp } from '/@/utils/http/axios';

export interface RedbookPublishPlanItem {
  id: string;
  draftId?: string;
  accountId?: string;
  plannedPublishTime?: string;
  actualPublishTime?: string;
  publishStatus?: string;
  noteUrl?: string;
  publisher?: string;
  remark?: string;
}

export function listPublishPlans(params: Recordable = {}) {
  return defHttp.get({
    url: '/redbook/publishPlan/list',
    params: {
      pageNo: 1,
      pageSize: 500,
      ...params,
    },
  });
}
