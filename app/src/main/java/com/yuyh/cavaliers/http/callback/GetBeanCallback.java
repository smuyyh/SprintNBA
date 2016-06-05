package com.yuyh.cavaliers.http.callback;

/**
 * @author yuyh.
 * @date 16/6/4.
 */
public interface GetBeanCallback<T> {

    void onSuccess(T t);

    void onFailure(String message);

}
