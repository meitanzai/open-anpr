package com.visual.open.anpr.handle;

import com.alibaba.fastjson.TypeReference;
import com.visual.open.anpr.common.Api;
import com.visual.open.anpr.http.HttpClient;
import com.visual.open.anpr.model.Recognition;
import com.visual.open.anpr.model.RecognitionRep;
import com.visual.open.anpr.model.RecognitionReq;
import com.visual.open.anpr.model.Response;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RecognitionHandler {

    /**服务地址**/
    protected String serverHost;
    /**实例对象**/
    private final static Map<String, RecognitionHandler> ins = new ConcurrentHashMap<>();

    public String getServerHost() {
        return serverHost;
    }

    public RecognitionHandler setServerHost(String serverHost) {
        this.serverHost = serverHost;
        return this;
    }

    /**
     * 构建车牌检测对象
     * @param serverHost        服务地址
     * @return
     */
    public static RecognitionHandler build(String serverHost){
        String key = serverHost;
        if(!ins.containsKey(key)){
            synchronized (RecognitionHandler.class){
                if(!ins.containsKey(key)){
                    ins.put(key, new RecognitionHandler().setServerHost(serverHost));
                }
            }
        }
        return ins.get(key);
    }

    /**
     * 车牌检测
     * @param recognition 待检测的数据信息
     * @return  车牌检测结果
     */
    public Response<List<RecognitionRep>> recognition(Recognition recognition){
        RecognitionReq compareReq = RecognitionReq.build()
                .setImage(recognition.getImage())
                .setLimit(recognition.getLimit());
        return HttpClient.post(
                Api.getUrl(this.serverHost, Api.plate_recognition),
                compareReq,
                new TypeReference<Response<List<RecognitionRep>>>() {});
    }

}
