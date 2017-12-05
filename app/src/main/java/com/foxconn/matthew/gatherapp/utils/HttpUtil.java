package com.foxconn.matthew.gatherapp.utils;



import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Matthew on 2017/11/20.
 */

public class HttpUtil {
    private static final String TAG = "HttpUtil";

    public static void getLatestNews(Callback callback){
        LogUtil.e(TAG,"getLatestNews");
        String LATEST_NEWS_ADDRESS="https://news-at.zhihu.com/api/4/news/latest";
        sendOkHttpRequest(LATEST_NEWS_ADDRESS,callback);
    }

    public static void getNewsDetailById(int id,Callback callback) {
        LogUtil.e(TAG,"getNewsDetailById");
        String DETAIL_NEWS_ADDRESS="https://news-at.zhihu.com/api/4/news/"+String.valueOf(id);
        sendOkHttpRequest(DETAIL_NEWS_ADDRESS,callback);
    }


    private static void sendOkHttpRequest(String address, Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
        LogUtil.e("httpUtil","obtain_info");
    }
}
