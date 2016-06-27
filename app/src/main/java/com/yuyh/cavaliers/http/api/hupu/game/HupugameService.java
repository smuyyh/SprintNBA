package com.yuyh.cavaliers.http.api.hupu.game;

import com.yuyh.cavaliers.BuildConfig;
import com.yuyh.cavaliers.http.api.RequestCallback;
import com.yuyh.cavaliers.http.bean.cookie.UserData;
import com.yuyh.cavaliers.http.okhttp.OkHttpHelper;
import com.yuyh.cavaliers.http.utils.RequestHelper;
import com.yuyh.library.AppUtils;
import com.yuyh.library.utils.DeviceUtils;
import com.yuyh.library.utils.data.safe.MD5;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author yuyh.
 * @date 16/6/25.
 */
public class HupugameService {

    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BuildConfig.HUPU_GAMES_SERVER)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpHelper.getAppClient())
            .build();

    public static HupuGameApi apiStr = retrofit.create(HupuGameApi.class);

    /**
     * 登录
     *
     * @param userName 用户名
     * @param passWord 密码
     */
    public static void login(String userName, String passWord, final RequestCallback<UserData> cbk) {
        HashMap<String, String> params = new HashMap<>();
        String deviceId = DeviceUtils.getIMEI(AppUtils.getAppContext());
        params.put("client", deviceId);
        params.put("username", userName);
        params.put("password", MD5.getMD5(passWord));
        String sign = RequestHelper.getRequestSign(params);
        params.put("sign", sign);

        Call<UserData> call = apiStr.login(params, deviceId);
        call.enqueue(new retrofit2.Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, retrofit2.Response<UserData> response) {
                UserData data = response.body();
                cbk.onSuccess(data);
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                cbk.onFailure(t.getMessage());
            }
        });
    }
}
