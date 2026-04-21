# 本地开发说明

## 1. 当前项目结构

```text
RedBoolAgent
├── jeecg-boot          # JeecgBoot 后端
├── jeecgboot-vue3      # JeecgBoot Vue3 前端
├── jeecg-boot/db       # 数据库初始化脚本
└── doc                 # 项目文档
```

## 2. 数据库配置

本地 MySQL 使用：

```text
host: 127.0.0.1
port: 3306
database: jeecg-boot
username: root
password: root
```

JeecgBoot 的开发环境配置文件已经默认使用 `root/root`：

```text
jeecg-boot/jeecg-module-system/jeecg-system-start/src/main/resources/application-dev.yml
```

## 3. 初始化数据库

先导入 JeecgBoot 基础表：

```bash
mysql -uroot -proot < jeecg-boot/db/jeecgboot-mysql-5.7.sql
```

再导入 RedBook Agent 业务表：

```bash
mysql -uroot -proot < jeecg-boot/db/redbook-agent-schema.sql
```

注意：`redbook-agent-schema.sql` 现在除了创建 `rb_*` 业务表，也会同步初始化 Jeecg 的 `sys_permission` 菜单，并默认授权给 `admin` / `vue3` 角色。导入后如果左侧还没出现“小红书运营”，请先退出重新登录，或刷新前端页面。

## 4. 启动后端

```bash
cd jeecg-boot
mvn -pl jeecg-module-system/jeecg-system-start -am spring-boot:run
```

启动后访问：

```text
http://localhost:8080/jeecg-boot/doc.html
```

## 5. 启动前端

```bash
cd jeecgboot-vue3
npm install -g pnpm
pnpm install
pnpm dev
```

如果是在导入菜单 SQL 之前就已经启动了前端开发服务，建议重新执行一次 `pnpm dev`，避免新页面组件未被当前开发进程纳入动态路由扫描。

## 6. RedBook Agent 后端接口

当前已提供以下 Jeecg 风格标准接口：

```text
/redbook/account
/redbook/track
/redbook/persona
/redbook/product
/redbook/promptTemplate
/redbook/sensitiveWord
/redbook/hotspot
/redbook/hotspotAnalysis
/redbook/noteDraft
/redbook/publishPlan
/redbook/noteMetric
/redbook/reviewReport
```

每个模块默认支持：

```text
GET    /list
POST   /add
POST   /edit
PUT    /edit
DELETE /delete
DELETE /deleteBatch
GET    /queryById
GET    /exportXls
POST   /importExcel
```

## 7. V1 边界

当前实现保持 V1 的半自动闭环边界：

- 支持人工录入和 Excel 导入热点。
- 支持结构化保存 AI 分析结果和草稿。
- 支持发布计划和发布后数据回收。
- 不实现自动爬取小红书、模拟登录、自动批量发布、自动评论私信等高风险能力。

## 8. Dify / FastGPT 接入

当前后端已经补上统一 AI 接入层，热点分析、草稿生成、复盘报告都会优先走远程 AI；如果未配置，则自动降级到本地模板逻辑，保证闭环可以继续跑通。

本地开发配置位置：

```text
jeecg-boot/jeecg-module-system/jeecg-system-start/src/main/resources/application-dev.yml
```

新增配置：

```yaml
redbook:
  ai:
    enabled: false
    provider: local
    base-url:
    api-key:
    timeout-seconds: 60
    user-prefix: redbook-agent
```

说明：

- `provider=local`：只走本地降级逻辑，适合先联调页面和数据库闭环。
- `provider=dify`：请求地址默认按 `.../v1/workflows/run` 组装。
- `provider=fastgpt`：请求地址默认按 `.../api/v1/chat/completions` 组装。
- `base-url` 和 `api-key` 为空时，不会调用远程 AI，会自动回退到本地模板结果。

提示词模板表：

```text
rb_prompt_template
```

当前默认内置 3 个模板编码：

- `hotspot_analysis`
- `note_draft`
- `review_report`

如果你要切到真实 Dify / FastGPT，请在“提示词模板”页面维护：

- `template_code`
- `prompt_content`
- `output_schema`
- `model_provider`

建议：

- `model_provider` 填 `dify` 或 `fastgpt`
- 输出严格使用 JSON，不要返回 Markdown 代码块
- 字段名尽量与当前后端解析字段保持一致，例如：
  - 热点分析：`pain_points` `hook_analysis` `content_angle` `score`
  - 草稿生成：`title` `cover_copy` `body` `tags` `comment_guide`
  - 复盘报告：`summary` `high_performing_factors` `next_topic_suggestions`

## 9. 当前闭环进度

当前已经打通：

- 菜单与权限初始化
- 基础配置模块 CRUD
- 热点池 CRUD + Excel 导入导出
- 工作台概览
- 热点 -> 分析 -> 草稿 -> 发布计划 -> 已发布 的闭环动作
- 笔记草稿一键复制发布文案
- 发布日历
- 发布计划一键生成 2h / 24h / 72h / 7d 数据回收记录
- 数据回收指标自动计算
- 复盘看板：总览、高低表现榜、赛道/账号/发布时间表现
- 复盘报告生成入口
- 复盘报告下一轮选题建议可回流到热点池
- Dify / FastGPT 可配置接入，未配置时自动降级

注意：

- JeecgBoot 当前项目运行在 `BACK` 菜单模式，左侧菜单来自数据库 `sys_permission`。
- 如果重新导入 `redbook-agent-schema.sql` 后仍未看到“小红书运营”，请退出重登或刷新前端。
