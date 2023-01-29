# 车牌识别服务API


**简介**:车牌识别服务API


**HOST**:http://127.0.0.1:8080


**联系人**:


**Version**:1.0.0


**接口路径**:/v3/api-docs?group=1.0.0


[TOC]






# 01、车牌识别服务


## 1、车牌识别


**接口地址**:`/visual/plate/recognition`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "image": "",
  "limit": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|plateInfoReqVo|车牌识别参数|body|true|PlateInfoReqVo|PlateInfoReqVo|
|&emsp;&emsp;image|图像Base64编码值||true|string||
|&emsp;&emsp;limit|最大搜索条数：默认5||false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResponseInfo«List«PlateInfoRepVo»»|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code|返回代码|integer(int32)|integer(int32)|
|data|数据信息|array|PlateInfoRepVo|
|&emsp;&emsp;location|车牌位置信息|PlateLocation|PlateLocation|
|&emsp;&emsp;&emsp;&emsp;leftBottom|左下角坐标|LocationPoint|LocationPoint|
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;x|坐标X的值|integer||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;y|坐标Y的值|integer||
|&emsp;&emsp;&emsp;&emsp;leftTop|左上角坐标值|LocationPoint|LocationPoint|
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;x|坐标X的值|integer||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;y|坐标Y的值|integer||
|&emsp;&emsp;&emsp;&emsp;rightBottom|右下角坐标|LocationPoint|LocationPoint|
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;x|坐标X的值|integer||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;y|坐标Y的值|integer||
|&emsp;&emsp;&emsp;&emsp;rightTop|右上角坐标|LocationPoint|LocationPoint|
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;x|坐标X的值|integer||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;y|坐标Y的值|integer||
|&emsp;&emsp;recognition|车牌识别信息|RecognitionInfo|RecognitionInfo|
|&emsp;&emsp;&emsp;&emsp;layout|车牌布局，单排还是双排,可用值:DOUBLE,SINGLE,UNKNOWN|string||
|&emsp;&emsp;&emsp;&emsp;plateColor|车牌的颜色信息,可用值:BLACK,BLUE,GREEN,UNKNOWN,WHITE,YELLOW|string||
|&emsp;&emsp;&emsp;&emsp;plateNo|车牌文本信息|string||
|&emsp;&emsp;score|车牌置信分数:[0,100]|number(float)||
|message|返回信息|string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": [
		{
			"location": {
				"leftBottom": {
					"x": 0,
					"y": 0
				},
				"leftTop": {
					"x": 0,
					"y": 0
				},
				"rightBottom": {
					"x": 0,
					"y": 0
				},
				"rightTop": {
					"x": 0,
					"y": 0
				}
			},
			"recognition": {
				"layout": "",
				"plateColor": "",
				"plateNo": ""
			},
			"score": 0
		}
	],
	"message": ""
}
```


# 02、健康检测服务


## 1、健康检查


**接口地址**:`/common/health/check`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResponseInfo«string»|
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code|返回代码|integer(int32)|integer(int32)|
|data|数据信息|string||
|message|返回信息|string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": "",
	"message": ""
}
```