package com.yuyh.cavaliers.http.api.hupu.game;

import com.yuyh.cavaliers.BuildConfig;
import com.yuyh.cavaliers.http.bean.cookie.UserData;
import com.yuyh.cavaliers.http.util.GetBeanCallback;
import com.yuyh.cavaliers.http.util.HupuReqHelper;
import com.yuyh.cavaliers.http.util.JsonParser;
import com.yuyh.cavaliers.http.util.StringConverter;
import com.yuyh.library.AppUtils;
import com.yuyh.library.utils.DeviceUtils;
import com.yuyh.library.utils.data.safe.MD5;
import com.yuyh.library.utils.log.LogUtils;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author yuyh.
 * @date 16/6/25.
 */
public class HupugameService {
    public static RestAdapter restStr = new RestAdapter.Builder().setEndpoint(BuildConfig.HUPU_GAMES_SERVER).setConverter(new StringConverter()).build();

    public static HupuGameApi apiStr = restStr.create(HupuGameApi.class);

    /**
     * 登录
     *
     * @param userName 用户名
     * @param passWord 密码
     */
    public static void login(String userName, String passWord, final GetBeanCallback<UserData> cbk) {
        HashMap<String, String> params = new HashMap<>();
        String deviceId = DeviceUtils.getIMEI(AppUtils.getAppContext());
        params.put("client", deviceId);
        params.put("username", userName);
        params.put("password", MD5.getMD5(passWord));
        String sign = HupuReqHelper.getRequestSign(params);
        params.put("sign", sign);

        apiStr.login(params, deviceId, new Callback<String>() {
            @Override
            public void success(String jsonStr, Response response) {
                UserData data = JsonParser.parseWithGson(UserData.class, jsonStr);
                cbk.onSuccess(data);
                LogUtils.i(jsonStr);
            }

            @Override
            public void failure(RetrofitError error) {
                cbk.onFailure(error.getMessage());
            }
        });
    }
}
