package com.mwg.goupon.util;

import android.util.Log;

import com.mwg.goupon.constant.Constant;
import com.mwg.goupon.ui.HttpUtil;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by mwg on 2018/1/15.
 */

public class RetrofitClient {

    private static RetrofitClient INSTANCE;

    public static RetrofitClient getInstance() {
        if (INSTANCE == null) {
            synchronized (RetrofitClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RetrofitClient();
                }
            }
        }
        return INSTANCE;
    }


    private Retrofit retrofit;
    private NetService netService;

    private RetrofitClient() {
        retrofit = new Retrofit.Builder().baseUrl(Constant.BASEURL).addConverterFactory(ScalarsConverterFactory.create()).build();
        netService = retrofit.create(NetService.class);
    }

    public void test() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("city", "北京");
        params.put("category", "美食");
        String sign = HttpUtil.getSign(HttpUtil.APPKEY, HttpUtil.APPSECRET, params);
        Call<String> call = netService.test(HttpUtil.APPKEY, sign, params);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String string = response.body();
                Log.i("TAG:", "利用封装后的Retrofit获得响应");
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {

            }
        });
    }
}
