package com.yuyh.cavaliers.http.api.hupu.login;

import com.yuyh.cavaliers.BuildConfig;
import com.yuyh.cavaliers.http.utils.StringConverter;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author yuyh.
 * @date 16/6/25.
 */
public class HupuLoginService {
    public static RestAdapter rest = new RestAdapter.Builder().setEndpoint(BuildConfig.HUPU_LOGIN_SERVER).build();
    public static RestAdapter restStr = new RestAdapter.Builder().setEndpoint(BuildConfig.HUPU_LOGIN_SERVER).setConverter(new StringConverter()).build();

    public static HupuLoginApi api = rest.create(HupuLoginApi.class);
    public static HupuLoginApi apiStr = restStr.create(HupuLoginApi.class);

    public static void login() {
        apiStr.login("", "", new Callback<String>() {
            @Override
            public void success(String s, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
