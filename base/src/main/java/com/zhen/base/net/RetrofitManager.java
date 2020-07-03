package com.zhen.base.net;

import com.zhen.base.http.interceptor.logging.LoggingInterceptor;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import static com.zhen.base.utils.Constant.BASE_URL;

/**
 * @author lep
 */
public class RetrofitManager {

    private Retrofit retrofit;

    public void initManager() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(new LoggingInterceptor.Builder().build())
                .addInterceptor(new HeaderInter())
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit.create(Api.class);

    }

    private <T> Disposable getDisposable(Observable<ApiRequestBean<T>> observable, IRequestListener handler) {
        ZpNetSubscriber zpNetSubscriber = new ZpNetSubscriber(handler);
        observable
                .map((Function<ApiRequestBean<T>, Object>) resultBean -> {
                    int resultCode = resultBean.getCode();
                    // 统一处理结果
                    if (resultCode == 200 && resultBean.getResult() != null) {
                        return resultBean.getResult();
                    } else {
                        throw new ApiException(resultCode, resultBean.getCodeMsg());
                    }
                })
                .compose(observableToMain())
                .subscribe(zpNetSubscriber);
        return zpNetSubscriber.getDisposable();
    }

    /**
     * 切换主线程
     */
    public  <T> ObservableTransformer<T, T> observableToMain() {
        return observable -> {
            return observable.subscribeOn(Schedulers.io())//在IO线程进行网络请求
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());//回到主线程去处理请求结果
        };
    }


}
