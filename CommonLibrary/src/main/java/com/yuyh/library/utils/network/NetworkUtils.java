package com.yuyh.library.utils.network;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.yuyh.library.AppUtils;
import com.yuyh.library.utils.log.LogUtils;

import java.lang.reflect.Method;

/**
 * @author yuyh.
 * @date 16/4/9.
 */
public class NetworkUtils {
    private static final String TAG = Network.class.getSimpleName();

    /**
     * 接受网络状态的广播Action
     */
    public static final String NET_BROADCAST_ACTION = "com.network.state.action";
    public static final String NET_STATE_NAME = "network_state";
    /**
     * 实时更新网络状态<br>
     * -1为网络无连接<br>
     * 1为WIFI<br>
     * 2为移动网络<br>
     */
    public static int CURRENT_NETWORK_STATE = -1;

    public enum NetType {
        None(1, "无网络连接"),
        Mobile(2, "蜂窝移动网络"),
        Wifi(4, "Wifi网络"),
        Other(8, "未知网络");

        NetType(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public int value;
        public String desc;
    }

    public enum NetWorkType {
        UnKnown(-1, "未知网络"),
        Wifi(1, "Wifi网络"),
        Net2G(2, "2G网络"),
        Net3G(3, "3G网络"),
        Net4G(4, "4G网络");

        NetWorkType(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public int value;
        public String desc;
    }

    /**
     * 获取ConnectivityManager
     */
    public static ConnectivityManager getConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /**
     * 获取TelephonyManager
     */
    public static TelephonyManager getTelephonyManager(Context context) {
        return (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }

    /**
     * 判断网络是否连接(包含MobileNet，Wifi)
     *
     * @return
     */
    public static boolean isConnected(Context context) {
        NetworkInfo net = getConnectivityManager(context).getActiveNetworkInfo();
        return net != null && net.isConnected();
    }

    /**
     * 判断有无网络正在连接中或已连接（查找网络、校验、获取IP等）(包含MobileNet，Wifi)
     *
     * @return
     */
    public static boolean isConnectedOrConnecting(Context context) {
        NetworkInfo[] nets = getConnectivityManager(context).getAllNetworkInfo();
        if (nets != null) {
            for (NetworkInfo net : nets) {
                if (net.isConnectedOrConnecting()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取当前网络类型(Wifi或移动网络)
     *
     * @param context
     * @return
     */
    public static NetType getConnectedType(Context context) {
        NetworkInfo net = getConnectivityManager(context).getActiveNetworkInfo();
        if (net != null) {
            switch (net.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    return NetType.Wifi;
                case ConnectivityManager.TYPE_MOBILE:
                    return NetType.Mobile;
                default:
                    return NetType.Other;
            }
        }
        return NetType.None;
    }

    /**
     * 是否存在有效的WIFI连接
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        NetworkInfo net = getConnectivityManager(context).getActiveNetworkInfo();
        return net != null && net.getType() == ConnectivityManager.TYPE_WIFI && net.isConnected();
    }

    /**
     * 是否存在有效的移动连接
     *
     * @param context
     * @return
     */
    public static boolean isMobileConnected(Context context) {
        NetworkInfo net = getConnectivityManager(context).getActiveNetworkInfo();
        return net != null && net.getType() == ConnectivityManager.TYPE_MOBILE && net.isConnected();
    }

    /**
     * 检测当前网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isAvailable(Context context) {
        return isWifiAvailable(context) || (isMobileAvailable(context) && isMobileEnabled(context));
    }

    /**
     * 判断是否有可用状态的Wifi，以下情况返回false：
     * 1. 设备wifi开关关掉;
     * 2. 已经打开飞行模式；
     * 3. 设备所在区域没有信号覆盖；
     * 4. 设备在漫游区域，且关闭了网络漫游。
     *
     * @param context
     * @return boolean wifi为可用状态（不一定成功连接，即Connected）即返回ture
     */
    public static boolean isWifiAvailable(Context context) {
        NetworkInfo[] nets = getConnectivityManager(context).getAllNetworkInfo();
        if (nets != null) {
            for (NetworkInfo net : nets) {
                if (net.getType() == ConnectivityManager.TYPE_WIFI) {
                    return net.isAvailable();
                }
            }
        }
        return false;
    }

    /**
     * 判断有无可用状态的移动网络，注意关掉设备移动网络直接不影响此函数。
     * 也就是即使关掉移动网络，那么移动网络也可能是可用的(彩信等服务)，即返回true。
     * 以下情况它是不可用的，将返回false：
     * 1. 设备打开飞行模式；
     * 2. 设备所在区域没有信号覆盖；
     * 3. 设备在漫游区域，且关闭了网络漫游。
     *
     * @param context
     * @return boolean
     */
    public static boolean isMobileAvailable(Context context) {
        NetworkInfo[] nets = getConnectivityManager(context).getAllNetworkInfo();
        if (nets != null) {
            for (NetworkInfo net : nets) {
                if (net.getType() == ConnectivityManager.TYPE_MOBILE) {
                    return net.isAvailable();
                }
            }
        }
        return false;
    }

    /**
     * 设备是否打开蜂窝移动网络开关
     *
     * @param context
     * @return
     */
    public static boolean isMobileEnabled(Context context) {
        try {
            Method getMobileDataEnabledMethod = ConnectivityManager.class.getDeclaredMethod("getMobileDataEnabled");
            getMobileDataEnabledMethod.setAccessible(true);
            return (Boolean) getMobileDataEnabledMethod.invoke(getConnectivityManager(context));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;// 反射失败，默认开启
    }

    /**
     * 打印当前各种网络状态
     *
     * @return boolean
     */
    public static boolean printNetworkInfo(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    LogUtils.i(TAG, "NetworkInfo[" + i + "]isAvailable : " + info[i].isAvailable());
                    LogUtils.i(TAG, "NetworkInfo[" + i + "]isConnected : " + info[i].isConnected());
                    LogUtils.i(TAG, "NetworkInfo[" + i + "]isConnectedOrConnecting : " + info[i].isConnectedOrConnecting());
                    LogUtils.i(TAG, "NetworkInfo[" + i + "]: " + info[i]);
                }
                LogUtils.i(TAG, "\n");
            } else {
                LogUtils.i(TAG, "getAllNetworkInfo is null");
            }
        }
        return false;
    }

    /**
     * get connected network type by {@link ConnectivityManager}
     * <p/>
     * such as WIFI, MOBILE, ETHERNET, BLUETOOTH, etc.
     *
     * @return {@link ConnectivityManager#TYPE_WIFI}, {@link ConnectivityManager#TYPE_MOBILE},
     * {@link ConnectivityManager#TYPE_ETHERNET}...
     */
    public static int getConnectedTypeINT(Context context) {
        NetworkInfo net = getConnectivityManager(context).getActiveNetworkInfo();
        if (net != null) {
            return net.getType();
        }
        return -1;
    }

    /**
     * 获取网络连接类型
     * <p/>
     * GPRS    2G(2.5) General Packet Radia Service 114kbps
     * EDGE    2G(2.75G) Enhanced Data Rate for GSM Evolution 384kbps
     * UMTS    3G WCDMA 联通3G Universal Mobile Telecommunication System 完整的3G移动通信技术标准
     * CDMA    2G 电信 Code Division Multiple Access 码分多址
     * EVDO_0  3G (EVDO 全程 CDMA2000 1xEV-DO) Evolution - Data Only (Data Optimized) 153.6kps - 2.4mbps 属于3G
     * EVDO_A  3G 1.8mbps - 3.1mbps 属于3G过渡，3.5G
     * 1xRTT   2G CDMA2000 1xRTT (RTT - 无线电传输技术) 144kbps 2G的过渡,
     * HSDPA   3.5G 高速下行分组接入 3.5G WCDMA High Speed Downlink Packet Access 14.4mbps
     * HSUPA   3.5G High Speed Uplink Packet Access 高速上行链路分组接入 1.4 - 5.8 mbps
     * HSPA    3G (分HSDPA,HSUPA) High Speed Packet Access
     * IDEN    2G Integrated Dispatch Enhanced Networks 集成数字增强型网络 （属于2G，来自维基百科）
     * EVDO_B  3G EV-DO Rev.B 14.7Mbps 下行 3.5G
     * LTE     4G Long Term Evolution FDD-LTE 和 TDD-LTE , 3G过渡，升级版 LTE Advanced 才是4G
     * EHRPD   3G CDMA2000向LTE 4G的中间产物 Evolved High Rate Packet Data HRPD的升级
     * HSPAP   3G HSPAP 比 HSDPA 快些
     *
     * @return {@link  NetWorkType}
     */
    public static NetWorkType getNetworkType(Context context) {
        int type = getConnectedTypeINT(context);
        switch (type) {
            case ConnectivityManager.TYPE_WIFI:
                return NetWorkType.Wifi;
            case ConnectivityManager.TYPE_MOBILE:
            case ConnectivityManager.TYPE_MOBILE_DUN:
            case ConnectivityManager.TYPE_MOBILE_HIPRI:
            case ConnectivityManager.TYPE_MOBILE_MMS:
            case ConnectivityManager.TYPE_MOBILE_SUPL:
                int teleType = getTelephonyManager(context).getNetworkType();
                switch (teleType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        return NetWorkType.Net2G;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        return NetWorkType.Net3G;
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        return NetWorkType.Net4G;
                    default:
                        return NetWorkType.UnKnown;
                }
            default:
                return NetWorkType.UnKnown;
        }
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @return true 表示开启
     */
    public static final boolean isGpsEnabled() {
        LocationManager locationManager = (LocationManager) AppUtils.getAppContext().getSystemService(Context.LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER); // GPS
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER); // WLAN或移动网络(3G/2G)
        if (gps || network) {
            Log.i("demo", "GPS Ensable");
            return true;
        }
        return false;
    }

    /**
     * 强制帮用户打开GPS
     */
    public static final void openGPS() {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(AppUtils.getAppContext(), 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {
        Intent intent = null;
        //判断手机系统的版本  即API大于10 就是3.0或以上版本
        if (android.os.Build.VERSION.SDK_INT > 10) {
            intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
        } else {
            intent = new Intent();
            ComponentName component = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
            intent.setComponent(component);
            intent.setAction("android.intent.action.VIEW");
        }
        activity.startActivity(intent);
    }

    /**
     * 开启服务,实时监听网络变化（需要在清单文件配置Service）
     *
     * @param context
     */
    public static void startNetService(final Context context) {
        //注册广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(NET_BROADCAST_ACTION);
        context.registerReceiver(mReceiver, intentFilter);
        //开启服务
        Intent intent = new Intent(context, NetworkService.class);
        context.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceDisconnected(ComponentName name) {
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
            }
        }, Context.BIND_AUTO_CREATE);
    }

    /**
     * 接受服务上发过来的广播
     */
    private static BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                CURRENT_NETWORK_STATE = (Integer) intent.getExtras().get(NET_STATE_NAME);
                switch (CURRENT_NETWORK_STATE) {
                    case -1:
                        LogUtils.d("网络更改为 无网络  CURRENT_NETWORK_STATE =" + CURRENT_NETWORK_STATE);
                        break;
                    case 1:
                        LogUtils.d("网络更改为 WIFI网络  CURRENT_NETWORK_STATE=" + CURRENT_NETWORK_STATE);
                        break;
                    case 2:
                        LogUtils.d("网络更改为 移动网络  CURRENT_NETWORK_STATE =" + CURRENT_NETWORK_STATE);
                        break;

                    default:
                        break;
                }
            }
        }
    };

}
