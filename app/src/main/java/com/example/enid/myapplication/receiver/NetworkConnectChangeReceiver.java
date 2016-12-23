package com.example.enid.myapplication.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;

import com.enid.library.utils.HLogUtil;

import cn.hth.igallery.util.LogUtil;

/**
 * Created by big_love on 2016/12/12.
 */

public class NetworkConnectChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //监听wifi是否打开，与是否连接成功没有关系
        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            int intExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
            switch (intExtra) {
                case WifiManager.WIFI_STATE_DISABLING:
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    HLogUtil.i("close wifi");
                    break;
                case WifiManager.WIFI_STATE_ENABLING:
                    break;
                case WifiManager.WIFI_STATE_ENABLED:
                    HLogUtil.i("open wifi");
                    break;
                case WifiManager.WIFI_STATE_UNKNOWN:
                    break;
                default:
                    break;
            }
        }

        /*监听是否连接上一个有效的无线wifi
         *当上面的WifiManager 的状态是WIFI_STATE_DISABLING 或者是 WIFI_STATE_DISABLED时，不会收到
         *该广播
         * 当上面WifiManager 的状态时是WIFI_STATE_ENABLED时才收到该广播*/
        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            Parcelable parcelableExtra = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (parcelableExtra != null) {
                NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                NetworkInfo.State state = networkInfo.getState();
                boolean connected = state == NetworkInfo.State.CONNECTED;
                LogUtil.i("connected wifi :" + connected);
            }
        }

        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            NetworkInfo networkInfo = (NetworkInfo) intent.getSerializableExtra(ConnectivityManager.EXTRA_NETWORK);
        }
    }
}
