# ADocNote

adoc笔记软件

使用SwingBuilder实现, Groovy和Java混编


- 需求 \
~~include指向文件不存在时,不能报错~~ \
~~编辑区面板和布局替换为选项卡~~ \
文件夹目录导航 \
标题导航 \
在标题列表定位到该文件 \
点击include进入目标文件 \
点击标题进入文件时, 定位到标题所在行 \
文字特效显示


- 项目结构设计 \
builder groovy直接生成控件类 \
component 自定义控件类 \
view 控件业务类 \
file 文件业务逻辑类 \
grammar 语法逻辑类 \
action swing和service的聚合层 \
frame 主窗口启动类 \
event 事件触发类 \
config 构建对象，管理对象依赖 \
register 注册groovy控件 \
api 各模块业务聚合层 \


`todo` 拓展标签
-[ ] 读取文件标题列表
-[ ] 全局保存文件


`todo` 待确认需求
-[ ] 语法块显示效果


约定
- 所有的地址都必须以地址分隔符结尾


项目结构
- builder 用来直接创建swing控件, 订阅监听事件event
- event 响应监听事件, 直接调用action, 分发事件
- action 用来聚合一个完整的view和file的交互操作
- api 聚合view或file单一模块的操作
- view 用来直接查询和修改控件数据