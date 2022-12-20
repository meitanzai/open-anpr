package com.visual.open.anpr.core.utils;

public class ThreadUtil {

    public static void run(Runnable runnable){
        new Thread(runnable).start();
    }

}
