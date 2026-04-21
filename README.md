# RedBookAgent

小红书半自动运营闭环系统，基于 `JeecgBoot + Dify/FastGPT + MySQL + Excel 导入导出` 进行开发。

V1 目标是先跑通：

```text
赛道配置 -> 热点录入/导入 -> AI 选题分析 -> AI 二创草稿 -> 人工审核 -> 发布排期 -> 数据回收 -> 复盘分析 -> 下一轮选题建议
```

## 文档

- [V1 开发计划](doc/development-plan.md)
- [本地开发说明](doc/local-dev.md)

## 当前进度

- 已合入 JeecgBoot 后端和 Vue3 前端基础项目。
- 已新增 `redbook-agent` 后端业务模块。
- 已完成账号、赛道、人设、产品卖点、提示词模板、敏感词、热点池、热点分析、笔记草稿、发布计划、数据回收、复盘报告的基础 CRUD API。
- 已提供 RedBook Agent 业务表初始化 SQL。

## V1 边界

本项目 V1 不做自动爬取小红书、模拟登录、自动批量发布、自动评论/私信等高风险能力，先实现稳定可控的人审半自动运营工作台。
