package com.visual.open.anpr;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.visual.open.anpr.handle.RecognitionHandler;

public class PlateRecognition {
    /**服务地址**/
    private String serverHost;
    /**实例对象**/
    private final static Map<String, PlateRecognition> ins = new ConcurrentHashMap<>();

    /**
     * 构建集合对象
     * @param serverHost        服务地址
     * @return
     */
    private PlateRecognition(String serverHost){
        this.serverHost = serverHost;
    }

    /**
     * 构建集合对象
     * @param serverHost        服务地址
     * @return
     */
    public static PlateRecognition build (String serverHost){
        String key = serverHost;
        if(!ins.containsKey(key)){
            synchronized (PlateRecognition.class){
                if(!ins.containsKey(key)){
                    ins.put(key, new PlateRecognition(serverHost));
                }
            }
        }
        return ins.get(key);
    }
    /**
     * 人脸比对操作对象
     * @return CollectHandler
     */
    public RecognitionHandler detection(){
        return RecognitionHandler.build(serverHost);
    }


}
