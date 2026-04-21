import { defHttp } from '/@/utils/http/axios';

export function getRedbookWorkbenchOverview() {
  return defHttp.get({
    url: '/redbook/workbench/overview',
  });
}
