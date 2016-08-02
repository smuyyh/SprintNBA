package com.yuyh.sprintnba.http.bean.base;

import java.io.Serializable;

/**
 * @author yuyh.
 * @date 2016/6/27.
 */
public class BaseData implements Serializable {

    /**
     * uid : 4847679
     * status : 200
     * data :
     * msg : 发表成功
     */
    public int uid;
    public int status;
    public String data;
    public String msg;
    public String result;
    public BaseError error;
}
