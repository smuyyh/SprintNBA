package com.yuyh.library.utils.network;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;

/**
 * 网络状态监听服务
 *
 * @author yuyh.
 * @date 16/4/9.
 */
public class NetworkService extends Service {

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager == null) {
                networkBroadCast(context, intent, -1);
                return;
            }
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info == null) {
                networkBroadCast(context, intent, -1);
                return;
            }
            int type = info.getType();
            switch (type) {
                case ConnectivityManager.TYPE_WIFI:
                    networkBroadCast(context, intent, 1);
                    break;
                case ConnectivityManager.TYPE_MOBILE:
                    networkBroadCast(context, intent, 2);
                    break;
                default:
                    break;
            }
        }
    };

    private void networkBroadCast(Context context, Intent intent, int netState) {
        intent.setAction(NetworkUtils.NET_BROADCAST_ACTION);
        intent.putExtra(NetworkUtils.NET_STATE_NAME, netState);
        context.sendBroadcast(intent);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, mFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
