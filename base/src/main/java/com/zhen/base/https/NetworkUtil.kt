package com.zhen.base.https

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkUtil {
    companion object {
        @SuppressLint("MissingPermission")
        fun isNetworkAvailable(context: Context) : Boolean{

            val manager : ConnectivityManager= context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val info = manager.activeNetworkInfo
            return info?.state == NetworkInfo.State.CONNECTED

        }
    }

}