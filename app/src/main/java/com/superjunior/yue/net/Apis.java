package com.superjunior.yue.net;

/**
 * Created by lqynydyxf on 2016/10/20.
 */

public class Apis {
    private static final String juheKey = "b74b0d9e84d744c3fedb76b476b8e03e";
    private static final String juheNewsBaseUrl = "http://v.juhe.cn/toutiao/index?type=";
    private static final String[] newsTypes = new String[]{
            "top", //头条
            "shehui", //社会
            "guonei", //国内
            "guoji", //国际
            "yule", //娱乐
            "tiyu", //体育
            "junshi", //军事
            "keji", //科技
            "caijing", //财经
            "shishang" //时尚
    };
    public static final String TOP_URL = juheNewsBaseUrl + newsTypes[0] + "&key=" + juheKey;
    public static final String SHEHUI_URL = juheNewsBaseUrl + newsTypes[1] + "&key=" + juheKey;
    public static final String GUONEI_URL = juheNewsBaseUrl + newsTypes[2] + "&key=" + juheKey;
    public static final String GUOJI_URL = juheNewsBaseUrl + newsTypes[3] + "&key=" + juheKey;
    public static final String YULE_URL = juheNewsBaseUrl + newsTypes[4] + "&key=" + juheKey;
    public static final String TIYU_URL = juheNewsBaseUrl + newsTypes[5] + "&key=" + juheKey;
    public static final String JUNSHI_URL = juheNewsBaseUrl + newsTypes[6] + "&key=" + juheKey;
    public static final String KEJI_URL = juheNewsBaseUrl + newsTypes[7] + "&key=" + juheKey;
    public static final String CAIJING_URL = juheNewsBaseUrl + newsTypes[8] + "&key=" + juheKey;
    public static final String SHISHANG_URL = juheNewsBaseUrl + newsTypes[9] + "&key=" + juheKey;
}
