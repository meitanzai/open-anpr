package com.visual.open.anpr.common;

public class Api {

    public static final String plate_recognition = "/visual/plate/recognition";

    public static String getUrl(String host, String uri){
        host = host.replaceAll ("/+$", "");
        return host + uri;
    }

}
