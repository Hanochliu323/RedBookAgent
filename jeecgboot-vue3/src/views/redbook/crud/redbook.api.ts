import { defHttp } from '/@/utils/http/axios';
import { Modal } from 'ant-design-vue';

export function buildRedbookApi(apiBase: string) {
  const listUrl = `${apiBase}/list`;
  const addUrl = `${apiBase}/add`;
  const editUrl = `${apiBase}/edit`;
  const getUrl = `${apiBase}/queryById`;
  const deleteUrl = `${apiBase}/delete`;
  const deleteBatchUrl = `${apiBase}/deleteBatch`;
  const exportXlsUrl = `${apiBase}/exportXls`;
  const importExcelUrl = `${apiBase}/importExcel`;

  return {
    getExportUrl: exportXlsUrl,
    getImportUrl: importExcelUrl,
    list(params) {
      return defHttp.get({ url: listUrl, params });
    },
    saveOrUpdate(params, isUpdate) {
      return defHttp.post({ url: isUpdate ? editUrl : addUrl, params });
    },
    queryById(params) {
      return defHttp.get({ url: getUrl, params });
    },
    action(action: string, params) {
      return defHttp.post({ url: `${apiBase}/${action}`, params });
    },
    deleteRecord(params, handleSuccess) {
      return defHttp.delete({ url: deleteUrl, data: params }, { joinParamsToUrl: true }).then(() => {
        handleSuccess();
      });
    },
    batchDelete(params, handleSuccess) {
      Modal.confirm({
        title: '确认删除',
        content: '是否删除选中数据',
        okText: '确认',
        cancelText: '取消',
        onOk: () => {
          return defHttp.delete({ url: deleteBatchUrl, data: params }, { joinParamsToUrl: true }).then(() => {
            handleSuccess();
          });
        },
      });
    },
  };
}
