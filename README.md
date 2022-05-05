### 项目介绍

#### 背景介绍
回馈github，我也把毕设放上来吧，给没有思路写毕设的同学一个思路，自己也是忙于实习没时间写毕设，最后也是拿出来点零碎时间写了下，服务设计和表设计都存在问题，职责划分不太明确，需要注意一下，salute~

毕设原型：根据b站视频教程所写，链接：https://www.bilibili.com/video/BV1dQ4y1A75e

**上述视频只是提供基本在线观看视频及后台管理功能，本项目额外实现了个人中心模块、问答模块和培训计划模块相关逻辑，以及优化了观看视频体验，记录播放进度，播放完毕打勾，课程学习进度等等**

项目前台前端代码地址：https://github.com/hczs/training-front

项目后台管理前端代码地址：https://github.com/hczs/training-admin

效果展示（服务器已到期，请下拉查看截图）：~http://81.70.19.46/~
#### 开发环境

jdk1.8+MySQL5.7+maven3.6.3

#### 技术栈

SpringCloud、Nacos、SpringBoot、Redis、MyBatis-Plus、Vue、element-ui、Nuxt

#### 主要功能介绍

1. 在线视频观看：播放进度记录、学习时长记录、学习课程记录
2. 培训计划：后台规划计划课程和学习人员，相关学习人员学习计划课程并总结，后台评分
3. 问答：简单的提问回答模块，回答实现楼中楼回复，问题按照最新提问和回答来排序
4. 热门课程：按照学习人数和点击次数来排序
5. 个人中心：我学习的课程、个人资料修改、我的提问、我的回答

#### 各个服务模块介绍

1. training-gateway：网关服务，统一入口，目前就做了统一跨域处理和swagger整合
2. service-home：首页微服务，提供首页轮播图查询
3. service-learning：学习中心，提供课程、章节相关功能
4. service-oss：与阿里云oss对接，提供文件上传功能
5. service-plan：培训计划相关功能
6. service-qa：问答相关功能
7. service-ucenter：用户中心，登录、注册相关功能
8. service-video：对接阿里云视频点播服务，提供视频上传、播放凭证获取，视频信息获取相关功能

#### 主要界面展示
##### 课程列表
![课程列表](https://user-images.githubusercontent.com/43227582/138546766-1e8f1ee6-2a1d-4b57-a20b-da92df4fd741.png)
##### 课程观看
![课程观看](https://user-images.githubusercontent.com/43227582/138546773-5f579e57-e5cc-493d-9b2b-4db3130909eb.png)
##### 课程学习进度
![课程学习进度](https://user-images.githubusercontent.com/43227582/138546791-af9855fb-3bad-4281-9e45-a9ee144ce678.png)
##### 提问列表
![提问列表](https://user-images.githubusercontent.com/43227582/138546800-3f0cb337-e777-4ed8-9ca9-e3a990c2c36d.png)
##### 问题查看
![问题回答](https://user-images.githubusercontent.com/43227582/138546806-9a8ad8f9-b76c-4224-8717-3549c9b54b33.png)
##### 资料修改
![资料修改](https://user-images.githubusercontent.com/43227582/138546819-d6538583-642e-41f5-a70d-01841fe9a262.png)
##### 培训计划
![培训计划](https://user-images.githubusercontent.com/43227582/138546830-c29a2e3d-dadf-423a-a583-ab1c6f853453.png)

### 安装教程

#### 需要准备什么

1. 电脑需要安装jdk1.8、mysql5.7、maven3.6.3基本环境

2. nacos注册中心，本地启动即可，nacos安装启动教程自行百度

3. 开通阿里云oss服务，不用不花钱，花钱也花的是几分钱，可以放心开通，充一块钱可以用好久，然后自行根据官方教程使用，并在service-oss的application.yml中修改为自己的相关配置

4. redis，可以准备云服务器或虚拟机来装一个redis，redis在本项目中有两个作用，首页优化和邮箱验证码存储

5. 邮箱，本项目使用发验证码使用了发送邮件的形式，所以需要准备一个邮箱并且开通POP3/IMAP/SMTP服务，配置单独的授权密码，然后将邮箱和授权密码填进service-ucenter服务中的resources/config/mail.setting文件中
6. 开通阿里云视频点播服务，根据官方或网上的教程创建key配置，并且配置到service-video服务的application.yml中

#### 怎么使用

1. 打开项目，下载相关依赖包到本地，并且将lib文件夹中的aliyun-java-vod-upload-1.4.14.jar单独引入到service-video中，因为maven中没有这个jar包，所以需要手动引入，然后项目基本不会报包找不到这些错误了
2. 然后创建数据库，字符集：utf8mb4 -- UTF-8 Unicode，排序规则：utf8mb4_general_ci，运行项目中的staff_training.sql文件，导入相关表，表设计有不合理的地方（但是能用），请后续自行修改
3. 依次检查每个服务的配置文件，修改为你的配置
4. 运行使用，访问http://localhost:8888/swagger-ui.html?urls.primaryName=service-learning 这个是学习中心的接口文档，右上角可以切换多个服务，查看各个服务的接口，与前端项目对接接口即可使用

#### 部署及遇到的问题
具体过程可以参考图片【redis一定要设置密码，更改默认端口，还有就是注意服务器配置】
![项目部署](https://user-images.githubusercontent.com/43227582/130637833-758b9af8-a356-415c-bd6e-e3f7f4258cf4.png)
思维导图xmind文件：链接：https://pan.baidu.com/s/1Za2TYUwGVOuE2pdK460ptQ 提取码：5run

