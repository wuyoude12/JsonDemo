package com.test.jsondemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.test.jsonbean.CityBean;
import com.test.jsonbean.ProvinceBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private HashMap<String, String> map = new HashMap<>();
    private HashMap<String, String> map1 = new HashMap<>();
    ArrayList list = new ArrayList();
    ArrayList list1 = new ArrayList();
    Handler handler;

    private TextView tv_show;
    private TextView tv_show1;
    private Spinner spin_province;

    String jsonId;
    String jsonName;

    Request request;
    String getResponse;
    String url;

    public boolean isFirst = true;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_show = (TextView) findViewById(R.id.tv_show);
        tv_show1 = (TextView) findViewById(R.id.tv_show1);
        spin_province = (Spinner) findViewById(R.id.spin_province);

        new Thread(new Runnable() {
            @Override
            public void run() {

                url = "http://p.apix.cn/apixlife/pay/utility/query_province";
                setRequest(url);

                ProvinceBean province = gson.fromJson(getResponse, ProvinceBean.class);
                ProvinceBean.Data myProvinceData = province.getData();
                List<ProvinceBean.Data.Province> myProvinceList = myProvinceData.getProvince();
                Log.i("ProvinceData", "run: "+myProvinceList.size());
                for (int i = 0; i < myProvinceList.size(); i++) {
                    list.add(myProvinceList.get(i).getProvinceName().toString());
                    jsonId = myProvinceList.get(i).getProvinceId().toString();
                    jsonName = myProvinceList.get(i).getProvinceName().toString();
                    map.put(jsonName, jsonId);
                }

                Message msg = new Message();
                msg.what = 1;
                msg.obj = map;
                handler.sendMessage(msg);

                Message message = new Message();
                message.what = 2;
                message.obj = list;
                handler.sendMessage(message);


            }
        }).start();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    tv_show.setText(msg.obj.toString());
                    tv_show.append("\n");
                }
                if (msg.what == 2) {
//                    tv_show1.append(list.toString());
//                    tv_show1.append(list.toString());

                    ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, list);
                    spin_province.setAdapter(adapter);


                }

                if (msg.what == 3) {

                    tv_show1.setText(msg.obj.toString());
                }

            }
        };



        spin_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(isFirst){
                    isFirst = false;
                    return;
                }

                Toast.makeText(MainActivity.this, "" + list.get(position) + map.get(list.get(position)) + "被选中了", Toast.LENGTH_SHORT).show();
                url = "http://p.apix.cn/apixlife/pay/utility/query_city?provid=" + map.get(list.get(position));


                new Thread(new Runnable() {
                    @Override
                    public void run() {


                        setRequest(url);
                        Log.i("URL", "run: " + url);
                        CityBean city = gson.fromJson(getResponse, CityBean.class);

                        CityBean.Data myCityData = city.getData();

                        List<CityBean.Data.City> myCityList = myCityData.getCity();
                        Log.i("cityData", "run: " + myCityList.size());
                        map1.clear();
                        list1.clear();
                        for (int i = 0; i < myCityList.size(); i++) {
                            jsonId = myCityList.get(i).getCityId().toString();
                            jsonName = myCityList.get(i).getCityName().toString();
                            map1.put(jsonName, jsonId);
                            list1.add(myCityList.get(i).getCityName().toString());
                        }
                        Message msg = new Message();
                        msg.what = 3;
                        msg.obj = map1;
                        handler.sendMessage(msg);
                        Log.i("City", "run: " + map1.toString());

                    }
                }).start();





            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                setRequest(url);
//                Log.i("URL", "run: "+url);
//                CityBean city = gson.fromJson(getResponse, CityBean.class);
//
//                CityBean.Data myCityData = city.getData();
//
////                                    List<CityBean.Data.City> myCityList = myCityData.getCity();
//                List<CityBean.Data.City> myCityList = myCityData.getCity();
//                Log.i("cityData", "run: "+myCityList.size());
//                map1.clear();
//                for (int i = 0; i < myCityList.size(); i++) {
//
//                    jsonId = myCityList.get(i).getCityId().toString();
//                    jsonName = myCityList.get(i).getCityName().toString();
//                    map1.put(jsonName,jsonId);
//                    list1.add(myCityList.get(i).getCityName().toString());
//                }
//                Log.i("City", "run: "+map1.toString());
//            }
//        }).start();


    }

    public void setRequest(String url) {

        try {
            OkHttpClient client = new OkHttpClient();
            request = new Request.Builder()
                    .url(url)
                    .get().addHeader("accept", "application/json")
                    .addHeader("content-type", "application/json")
                    .addHeader("apix-key", "f59248f2375d499c4082005703c6ae01")
                    .build();

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response);

            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }
            getResponse = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
