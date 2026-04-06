/**
 * @fileoverview NetworkCreator 网络模块
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 网络请求相关组件
 */

package xmzai.mizhoubaobei.top.network;

import xmzai.mizhoubaobei.top.network.common_bean.callback.ResponseCallback;
import xmzai.mizhoubaobei.top.network.converter.JsonConverterFactory;
import xmzai.mizhoubaobei.top.utils.base.WearUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * author : lzh
 * e-mail : luozhanhang@adswave.com
 * time   : 2024/5/7
 * desc   :
 * version: 1.0
 */
public class NetworkCreator {

    public static NetService getNetService(ResponseCallback callback) {
        if (callback == null) {
            return NetworkServiceHolder.NET_SERVICE;
        } else {
            return NetworkHolder.getRetrofit(callback).create(NetService.class);
        }
//        return NetworkHolder.getRetrofit(callback).create(NetService.class);
    }

    /**
     * 产生一个全局的retrofit客户端
     */
    private static final class NetworkHolder {
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(WearUtil.APP_SERVER_HTTPS)
//                .baseUrl("https://test-api2.proxy302.com")
                .addConverterFactory(JsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttpHolder.OK_HTTP_CLIENT)
                .build();

        private static Retrofit getRetrofit(ResponseCallback callback) {
            return new Retrofit.Builder()
                    .baseUrl(WearUtil.APP_SERVER_HTTPS)
//                    .baseUrl("https://test-api2.proxy302.com")
                    .addConverterFactory(JsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(OkHttpHolder.getOkHttpClient(callback))
                    .build();
        }
    }

    /**
     * 可以单独设置的okhttp
     */
    private static final class OkHttpHolder {
        private static final int TIME_OUT = 30;
        private static final RequestInterceptor interceptor = RequestInterceptor.getInstance();

        private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)   // 添加拦截器，实现缓存以及一些请求头
                .build();

        private static OkHttpClient getOkHttpClient(ResponseCallback callback) {
            return new OkHttpClient.Builder()
                    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .addInterceptor(new RequestInterceptor(callback))   // 添加拦截器，实现缓存以及一些请求头
                    .build();
        }
    }

    private static final class NetworkServiceHolder {
        private static final NetService NET_SERVICE = NetworkHolder.RETROFIT_CLIENT.create(NetService.class);
    }
}
