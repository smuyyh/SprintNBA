package com.yuyh.sprintnba.http.bean.match;

import com.google.gson.annotations.SerializedName;
import com.yuyh.sprintnba.http.bean.base.Base;

import java.io.Serializable;

/**
 * @author yuyh.
 * @date 16/6/4.
 */
public class MatchCalendar extends Base {

    /**
     * startTime : 1384012800
     * endTime : 1467216000
     * matchNum :{"1":"1",2:"5"...}
     */

    public MatchCalendarBean data;

    public static class MatchCalendarBean implements Serializable {
        public String startTime;
        public String endTime;
        public MatchNum matchNum;

        public static class MatchNum implements Serializable{

            @SerializedName("1")
            public String num1;
            @SerializedName("2")
            public String num2;
            @SerializedName("3")
            public String num3;
            @SerializedName("4")
            public String num4;
            @SerializedName("5")
            public String num5;
            @SerializedName("6")
            public String num6;
            @SerializedName("7")
            public String num7;
            @SerializedName("8")
            public String num8;
            @SerializedName("9")
            public String num9;
            @SerializedName("10")
            public String num10;
            @SerializedName("11")
            public String num11;
            @SerializedName("12")
            public String num12;
            @SerializedName("13")
            public String num13;
            @SerializedName("14")
            public String num14;
            @SerializedName("15")
            public String num15;
            @SerializedName("16")
            public String num16;
            @SerializedName("17")
            public String num17;
            @SerializedName("18")
            public String num18;
            @SerializedName("19")
            public String num19;
            @SerializedName("20")
            public String num20;
            @SerializedName("21")
            public String num21;
            @SerializedName("22")
            public String num22;
            @SerializedName("23")
            public String num23;
            @SerializedName("24")
            public String num24;
            @SerializedName("25")
            public String num25;
            @SerializedName("26")
            public String num26;
            @SerializedName("27")
            public String num27;
            @SerializedName("28")
            public String num28;
            @SerializedName("29")
            public String num29;
            @SerializedName("30")
            public String num30;
            @SerializedName("31")
            public String num31;
        }
    }

}
