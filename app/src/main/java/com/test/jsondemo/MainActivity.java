package com.test.jsondemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private HashMap<String,String>map = new HashMap<>();
    ArrayList list = new ArrayList();

    Handler handler;

    private TextView tv_show;
    private TextView tv_show1;
    private Spinner spin_province;

    String id;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_show = (TextView)findViewById(R.id.tv_show);
        tv_show1 = (TextView)findViewById(R.id.tv_show1);
        spin_province = (Spinner)findViewById(R.id.spin_province);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url("http://p.apix.cn/apixlife/pay/utility/query_province")
                            .get().addHeader("accept", "application/json")
                            .addHeader("content-type", "application/json")
                            .addHeader("apix-key", "f59248f2375d499c4082005703c6ae01")
                            .build();

                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0; i < responseHeaders.size(); i++){
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }
                    String getResponse = response.body().string();

                    Gson gson =new Gson();
                    ProvinceID provinceID = gson.fromJson(getResponse,ProvinceID.class);
                    ProvinceID.Data myData = provinceID.getData();
                    List<ProvinceID.Data.Province>myList = myData.getProvince();
                    for (int i = 0; i < myList.size(); i++){
                        list.add(myList.get(i).getProvinceName().toString());
                        id = myList.get(i).getProvinceId().toString();
                        name = myList.get(i).getProvinceName().toString();
                        map.put(name,id);

                    }

                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = map;
                    handler.sendMessage(msg);

                    Message message = new Message();
                    message.what = 2;
                    message.obj = list;
                    handler.sendMessage(message);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1){
                    tv_show.setText(msg.obj.toString());
                    tv_show.append("\n");
                }
                if (msg.what == 2){
                    tv_show1.append(msg.obj.toString());
                }
            }
        };

    }
}
