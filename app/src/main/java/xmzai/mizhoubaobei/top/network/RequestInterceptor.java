/**
 * @fileoverview RequestInterceptor 网络模块
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 网络请求相关组件
 */

package xmzai.mizhoubaobei.top.network;


import xmzai.mizhoubaobei.top.network.callback.NetworkProgressListener;
import xmzai.mizhoubaobei.top.network.common_bean.callback.ResponseCallback;
import xmzai.mizhoubaobei.top.utils.LogUtils;
import xmzai.mizhoubaobei.top.utils.base.WearUtil;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * author : lzh
 * e-mail : luozhanhang@adswave.com
 * time   : 2024/5/7
 * desc   : Http请求拦截器
 * version: 1.0
 */
public class RequestInterceptor implements Interceptor {

    private static final String TAG = "RequestInterceptor";
    private static volatile RequestInterceptor mInstance;

    private static HostDispenser mHostDispenser;
    private ResponseCallback callback;

    private RequestInterceptor() {
        mHostDispenser = new HostDispenser();
    }

    public RequestInterceptor(ResponseCallback callback) {
        super();
        this.callback = callback;
    }

    public static RequestInterceptor getInstance() {
        if (mInstance == null) {
            synchronized (RequestInterceptor.class) {
                if (mInstance == null) {
                    mInstance = new RequestInterceptor();
                }
            }
        }
        return mInstance;
    }

    // 替换baseUrl
    public static String getBaseUrl(String oldHttpUrl) {
        return WearUtil.APP_SERVER_HTTPS;
    }

    @Override
    public Response intercept(Chain chain) throws IOException, RuntimeException {
        Response response = null;
        Request request = chain.request();
        Request.Builder builder = request
                .newBuilder();

        builder.addHeader("BODY-X-TYPE", "2")
                .addHeader("BODY-X-VERSION", "1.0")
                .addHeader("ver", WearUtil.appVersion)
                .build();

        RequestBody body = request.body();
        if (body != null && request.method().equals(HttpMethod.POST.toString())) {
            Buffer bufferRequest = new Buffer();
            body.writeTo(bufferRequest);
        }

        //重建新的HttpUrl，修改需要修改的url部分
        HttpUrl newFullUrl = getHttpUrl(builder, request.url());
        if (callback == null) {
            response = chain.proceed(builder.url(newFullUrl)
                    .build());
        } else {
            //进度监听
            NetworkProgressProxy progressListener = new NetworkProgressProxy(request.body(), new NetworkProgressListener() {
                @Override
                public void onProgress(long uploadSize, long fileSize) {
                    callback.onProgress(uploadSize, fileSize);
                }
            });
            response = chain.proceed(builder.url(newFullUrl)
                    .post(progressListener)
                    .build());
        }

        return response;
    }

    private HttpUrl getHttpUrl(Request.Builder builder, HttpUrl oldHttpUrl) {
        String baseUrl = getBaseUrl(oldHttpUrl.toString());

        HttpUrl newBaseUrl = HttpUrl.parse(baseUrl);
        //重建新的HttpUrl，修改需要修改的url部分
        return oldHttpUrl
                .newBuilder()
                .scheme(newBaseUrl.scheme())
                .host(newBaseUrl.host())
                .port(newBaseUrl.port())
                .build();
    }

    /**
     * 获取字符串的排序后的codePoints
     */
    private static int[] stringToSortedCodePoints(String str) {
        int[] codePoints = new int[0];

        codePoints = str.codePoints().toArray();

        Arrays.sort(codePoints);
        return codePoints;
    }
}
