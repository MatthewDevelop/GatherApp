package com.foxconn.matthew.gatherapp.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.foxconn.matthew.gatherapp.R;
import com.foxconn.matthew.gatherapp.base.BaseFragment;
import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * @author:Matthew
 * @date:2018/1/24
 * @email:guocheng0816@163.com
 */

public class AndroidServerFragment extends BaseFragment{
    private AsyncHttpServer mServer=new AsyncHttpServer();
    private AsyncServer mAsyncServer=new AsyncServer();


    @Override
    protected void init(Bundle savedInstanceState) {

        mServer.get("/hello", new HttpServerRequestCallback() {
            @Override
            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
                try {
                    Log.e(TAG, "onRequest: html" );
                    response.send(getIndexContent());
                } catch (IOException e) {
                    e.printStackTrace();
                    response.code(500).end();
                }
            }
        });

        mServer.get("/files", new HttpServerRequestCallback() {
            @Override
            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
                JSONArray jsonArray=new JSONArray();
                File dir=new File(Environment.getExternalStorageDirectory().getPath());
                System.out.println(dir.exists());
                System.out.println(dir.isDirectory());
                String[] fileNames=dir.list();
                System.out.println(fileNames==null);
                if(fileNames!=null){
                    for (String fileName :
                            fileNames) {
                        File file=new File(dir,fileName);
                        if (file.exists()&&file.isFile()){
                            JSONObject jsonObject=new JSONObject();
                            try {
                                jsonObject.put("name",fileName);
                                jsonObject.put("path",file.getAbsolutePath());
                                jsonArray.put(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                System.out.println(jsonArray.toString());
                response.send(jsonArray.toString());
            }
        });

        mServer.listen(mAsyncServer,8888);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mServer!=null){
            mServer.stop();
        }
        if(mAsyncServer!=null){
            mAsyncServer.stop();
        }
    }

    private String getIndexContent() throws IOException {
        BufferedInputStream bis=null;
        try {
            bis=new BufferedInputStream(getActivity().getAssets().open("index.html"));
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            int len=0;
            byte[] buff=new byte[1024];
            while ((len=bis.read(buff))!=-1){
                baos.write(buff,0,len);
            }
            return new String(baos.toByteArray(),"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }finally {
            if(bis!=null){
                bis.close();
            }
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_android_server;
    }
}
