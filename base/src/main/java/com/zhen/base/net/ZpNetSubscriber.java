package com.zhen.base.net;

import android.text.TextUtils;
import android.util.Log;
import com.zhen.mvvm.utils.Utils;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.zhen.base.http.NetworkUtil.isNetworkAvailable;

public class ZpNetSubscriber<T> implements Observer<T> {
    private IRequestListener<T> handler;
    private Disposable disposable;
    // 网络异常(无网络)
    public static final int CONNECT_ERROR = -600;
    // 网络连接超时
    public static final int TIMEOUT_ERROR = -601;
    // 404
    public static final int NOPAGE_REFUND_ERROR = -602;
    // 其他异常
    public static final int BUSINESS_ERROR = -610;
    /**
     * 未注册code10134
     */
    public static final int UNREGISTER_CODE = 10134;
    /**
     * 验证码错误
     */
    public static final int CODE_ERROR = 1015;
    /**
     * 参数错误
     */
    public static final int PARAME_ERROR = 108;

    public static final int PARAME_MORE_THAN_FIVE = 10137;
    public static final String ERROR_MSG_CONNECT = "网络连接失败，请检查网络后重试！";
    public static final String ERROR_MSG_TIMEOUT = "网络连接超时，请检查网络后重试！";
    public static final String ERROR_MSG_NOPAGE_FOUND = "您的app可能不是最新版本，请升级至最新版本！";
    public static final String ERROR_MSG_BUSINESS = "数据异常，请稍后重试！";

    public ZpNetSubscriber(IRequestListener<T> handler) {
        this.handler = handler;
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        //添加业务处理
        this.disposable = disposable;
    }

    /**
     * 返回结果
     *
     * @param t T
     */
    @Override
    public void onNext(T t) {
        onRequestSuccess(t);
        cancelRequest();
    }

    /**
     * 对错误进行统一处理
     *
     * @param e Throwable
     */
    @Override
    public void onError(Throwable e) {
        Log.e("onError:" , e.getMessage());

        if (e instanceof ApiException) {
            if (handler != null) {
                ApiException zpApiException = ((ApiException) e);
                int resultCode = zpApiException.getResultCode();
                // token过期异常
//                if (resultCode == 116) {
//                    new Handler().postDelayed(() -> {
//                        Session mSession = Session.get();
//                        if (mSession.isLogin() && !BaseApp.getInstance().isLoginActivityLaunch) {
//                            mSession.setLogin(false);
//                            mSession.setLoginStatusChange(true);
//                            Intent intent = new Intent(BaseApp.getInstance(), LoginActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            BaseApp.getInstance().startActivity(intent);
//                            BaseApp.getInstance().isLoginActivityLaunch = true;
//                        }
//                    }, 200);
//                }
                onRequestError(resultCode, zpApiException.getMessage());
            }
        } else if (e instanceof ConnectException || !isNetworkAvailable(Utils.getContext())) {
            onRequestError(ZpNetSubscriber.CONNECT_ERROR, ERROR_MSG_CONNECT);
        } else if (e instanceof SocketTimeoutException) {
            onRequestError(ZpNetSubscriber.TIMEOUT_ERROR, ERROR_MSG_TIMEOUT);
        }
//        else if ((e instanceof HttpException) && ((HttpException) e).code() == 404) {
//            onRequestError(ZpNetSubscriber.NOPAGE_REFUND_ERROR, ERROR_MSG_NOPAGE_FOUND);
//        }
        else {
            onRequestError(ZpNetSubscriber.BUSINESS_ERROR, ERROR_MSG_BUSINESS);
        }
        cancelRequest();
    }

    @Override
    public void onComplete() {
        cancelRequest();
    }

    /**
     * 请求失败
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    private void onRequestError(int errorCode, String errorMsg) {
        if (handler != null) {
            if (TextUtils.isEmpty(errorMsg) || "ok".equals(errorMsg)) {
                errorCode = ZpNetSubscriber.BUSINESS_ERROR;
                errorMsg = ERROR_MSG_BUSINESS;
            }
            handler.onError(errorCode, errorMsg);
        }
    }

    /**
     * 请求成功
     *
     * @param t T
     */
    private void onRequestSuccess(T t) {
        if (handler != null) {
            handler.onSuccess(t);
        }
    }

    /**
     * 取消订阅，同时取消http请求
     */
    private void cancelRequest() {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public Disposable getDisposable() {
        return disposable;
    }
}