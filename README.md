## 人脸搜索M:N

* 最全车牌识别算法，支持14种中文车牌类型。

* 项目使用纯Java开发，免去使用Python带来的服务不稳定性。

* 项目模型来源：
* 欢迎大家贡献代码，如果你觉得项目还不错，请给个star。

### 项目简介

* 项目使用组件

&ensp; &ensp; 1、spring boot

&ensp; &ensp; 2、[onnx](https://github.com/onnx/onnx)

* 深度学习模型

&ensp; &ensp; 1、[Chinese_license_plate_detection_recognition](https://github.com/we0091234/Chinese_license_plate_detection_recognition)


### 项目文档

* 在线文档：[文档-1.0.0](scripts/docs/doc-1.0.0.md)

* swagger文档：启动项目且开启swagger，访问：host:port/doc.html, 如 http://127.0.0.1:8080/doc.html

### 搜索客户端

* Java依赖,未发布到中央仓库，需要自行编译发布到私有仓库
```
<dependency>
    <groupId>com.visual.open.anpr</groupId>
    <artifactId>open-anpr-client</artifactId>
    <version>1.0.0</version>
</dependency>
```
* 其他语言依赖

&ensp; &ensp;使用restful接口：[文档-1.0.0](scripts/docs/doc-1.0.0.md)


### 项目部署

* docker部署
```
1、直接docker部署项目：
docker run -d --name "open-anpr" -p 56790:8080 divenswu/open-anpr:1.0.0

2、若想禁用swagger：
docker run -d --name "open-anpr" -p 56790:8080 -e VISUAL_SWAGGER_ENABLE=false divenswu/open-anpr:1.0.0

3、若想挂载日志目录到本地：
docker run -d --name "open-anpr" -p 56790:8080 -v open-anpr/logs:/app/open-anpr/logs divenswu/open-anpr:1.0.0

4、服务访问：
  swagger借口文档： http://127.0.0.1:56790/doc.html
```

* 项目编译，并打包为docker镜像
```
1、java版本最低为：1.8；安装maven编译工具。安装docker。
2、克隆项目
  git clone https://gitee.com/open-visual/open-anpr.git
3、项目打包
   cd open-anpr && sh scripts/docker_build.sh
```

* 部署参数

| 参数                     |            描述 |  默认值  | 可选值        |
|------------------------|--------------:| :----:  |------------|
| VISUAL_SWAGGER_ENABLE  | 是否开启swagger   |   true  | true/false |


### 项目演示

* 1.0.0 测试用例：open-anpr-test[测试用例-PlateRecognitionExample](https://gitee.com/open-visual/open-anpr/blob/master/open-anpr-test/src/main/java/com/visual/open/anpr/exps/PlateRecognitionExample.java)

* ![输入图片说明](scripts%2Fimages%2Fvalidate-1.0.0.jpg)

### 交流群

* 微信交流群

    关注微信公众号回复：微信群

* 微信公众号：关注一下，是对我最大的支持

![微信公众号](scripts/images/%E5%85%AC%E4%BC%97%E5%8F%B7-%E5%BE%AE%E4%BF%A1.jpg)
