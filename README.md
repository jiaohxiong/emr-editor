# emr-editor
EMR电子病历编辑器，护理文书编辑器，医联体医共体结构化电子病历。
##### 在线地址：http://emr.xdawo.com/index.html?mode=2&simple=true&showToolbar=true


# install & start demo

此demo包含两部分内容，
1、`html`文件夹内的是前端资源文件，由`js+css+html`代码编写，扩展与`ueditor`
2、`api`是后台存储服务，由java代码编写

# start
1、前端环境`nodejs`
执行`node server.js`即可

`server.js`配置的端口信息
```
const port = 8088;
const app = Server.app();

app.listen(port);
console.log(`Listening at http://localhost:${port}`);
```

2、后台接口环境

i. 数据库`MySQL`
ii. JDK版本：`v11`

# 配置

前端配置文件:`gloves.config.js`
Spring配置文件:`application.properties`

# 
