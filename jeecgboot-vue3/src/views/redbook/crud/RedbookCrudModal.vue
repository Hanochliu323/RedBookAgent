<template>
  <BasicModal v-bind="$attrs" @register="registerModal" :title="modalTitle" :width="860" @ok="handleSubmit">
    <BasicForm @register="registerForm" />
  </BasicModal>
</template>

<script lang="ts" setup>
  import { computed, ref, unref } from 'vue';
  import { BasicForm, useForm } from '/@/components/Form';
  import { BasicModal, useModalInner } from '/@/components/Modal';
  import type { RedbookModuleConfig } from './redbook.config';

  const props = defineProps<{
    config: RedbookModuleConfig;
    moduleApi: Recordable;
  }>();
  const emit = defineEmits(['success', 'register']);
  const isUpdate = ref(false);

  const [registerForm, { resetFields, setFieldsValue, validate }] = useForm({
    schemas: props.config.formSchema,
    showActionButtonGroup: false,
    labelWidth: 130,
  });

  const [registerModal, { setModalProps, closeModal }] = useModalInner(async (data) => {
    await resetFields();
    setModalProps({ confirmLoading: false });
    isUpdate.value = !!data?.isUpdate;
    if (unref(isUpdate)) {
      const record = await props.moduleApi.queryById({ id: data.record.id });
      await setFieldsValue({ ...record });
    }
  });

  const modalTitle = computed(() => `${unref(isUpdate) ? '编辑' : '新增'}${props.config.title}`);

  async function handleSubmit() {
    try {
      const values = await validate();
      setModalProps({ confirmLoading: true });
      await props.moduleApi.saveOrUpdate(values, isUpdate.value);
      closeModal();
      emit('success');
    } finally {
      setModalProps({ confirmLoading: false });
    }
  }
</script>

<style lang="less" scoped>
  :deep(.ant-input-number) {
    width: 100%;
  }
</style>
