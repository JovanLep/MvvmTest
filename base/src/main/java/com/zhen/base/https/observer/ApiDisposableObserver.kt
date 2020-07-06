package com.zhen.base.https.observer

import com.zhen.base.http.ResponseThrowable
import com.zhen.base.https.ApiException
import com.zhen.base.https.BaseBean
import com.zhen.base.https.Constant.BUSINESS_ERROR
import com.zhen.base.https.Constant.CODE_10134
import com.zhen.base.https.Constant.CODE_108
import com.zhen.base.https.Constant.CODE_200
import com.zhen.base.https.Constant.ERROR_MSG_BUSINESS
import com.zhen.base.https.Constant.ERROR_MSG_CONNECT
import com.zhen.base.https.Constant.ERROR_MSG_TIMEOUT
import com.zhen.base.https.NetworkUtil
import com.zhen.base.net.ApiRequestBean
import com.zhen.base.net.ZpNetSubscriber.CONNECT_ERROR
import com.zhen.base.net.ZpNetSubscriber.TIMEOUT_ERROR
import com.zhen.mvvm.utils.KLog
import com.zhen.mvvm.utils.Utils
import io.reactivex.observers.DisposableObserver
import java.net.ConnectException
import java.net.SocketTimeoutException

abstract class ApiDisposableObserver<T> : DisposableObserver<T>() {

    abstract fun onSuccess(result: T)
    abstract fun onError(errorCode: Int, errorMsg: String);

    override fun onStart() {
        super.onStart()

        if (!NetworkUtil.isNetworkAvailable(Utils.getContext())) {
            KLog.d("无网络")
        }

    }

    override fun onNext(t: T) {

        val data = (t as BaseBean<*>?)

        when (data?.code) {
            CODE_200 -> {
                onSuccess(t)
            }
            CODE_108 -> {
                KLog.d("参数错误")
            }
            CODE_10134 -> {
                KLog.d("未注册")
            }

        }

    }

    override fun onError(e: Throwable) {
        e.printStackTrace()

        if (e is ApiException) {
            val zpApiException = e
            val resultCode = zpApiException.resultCode;
            onRequestError(resultCode, zpApiException.message.toString());
        } else if (e is ConnectException || !NetworkUtil.isNetworkAvailable(Utils.getContext())) {
            onRequestError(CONNECT_ERROR, ERROR_MSG_CONNECT);
        } else if (e is SocketTimeoutException) {
            onRequestError(TIMEOUT_ERROR, ERROR_MSG_TIMEOUT);
        } else if (e is ResponseThrowable) {
            val mError = e
            KLog.d("错误 $mError")
        } else {
            onRequestError(BUSINESS_ERROR, ERROR_MSG_BUSINESS);
        }

    }

    /**
     * 请求失败
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    private fun onRequestError(errorCode: Int, errorMsg: String) {
        onError(errorCode, errorMsg);
    }

}