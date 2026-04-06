/**
 * @fileoverview NetworkUtil 网络模块
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 网络请求相关组件
 */

package xmzai.mizhoubaobei.top.network;

import android.util.Log;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSONObject;
import xmzai.mizhoubaobei.top.network.common_bean.callback.ResponseCallback;
import xmzai.mizhoubaobei.top.network.common_bean.exception.NetException;
import xmzai.mizhoubaobei.top.network.disposable.NetAllSubscriber;
import xmzai.mizhoubaobei.top.network.disposable.NetNoRuleSubscriber;
import xmzai.mizhoubaobei.top.network.utils.GsonUtils;
import xmzai.mizhoubaobei.top.utils.EncodeUtils;
import xmzai.mizhoubaobei.top.utils.GZIPUtils;
import xmzai.mizhoubaobei.top.utils.LogUtils;

import org.reactivestreams.Publisher;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * author : lzh
 * e-mail : luozhanhang@adswave.com
 * time   : 2024/5/7
 * desc   :
 * version: 1.0
 */
public class NetworkUtil {

    public static String TAG = "NetworkNewUtils";

    private static volatile NetworkUtil mInstance;

    public static NetworkUtil getInstance() {
        if (mInstance == null) {
            synchronized (NetworkUtil.class) {
                if (mInstance == null) {
                    mInstance = new NetworkUtil();
                }
            }
        }
        return mInstance;
    }

    /**
     * post - Map请求
     */
    public <T> Disposable executePostAllRaw(final String url, Map<String, Object> parame, final ResponseCallback<T> callback) {
        Type finalNeedType = GenericsUtils.getSuperClassGenricType(callback.getClass());
        if (finalNeedType == null) {
            return null;
        }

        NetworkClient build = NetworkClient.create()
                .raw(GsonUtils.toJson(parame))
                .url(url)
                .build();
        return build.postraw().compose(getSchedulersTransformerMain()).subscribeWith(new NetAllSubscriber<>(url, finalNeedType, callback));
    }

    /**
     * 表单方式上传参数的 POST 请求（区别于 JSON 原始请求）
     * @param url 请求地址
     * @param parame 表单参数（键值对）
     * @param callback 请求结果回调
     * @param <T> 回调数据的泛型类型
     * @return Disposable 用于取消请求
     */
    public <T> Disposable executePostAllRaw1(final String url, Map<String, Object> parame, final ResponseCallback<T> callback) {
        // 1. 解析回调的泛型类型（与原方法逻辑一致）
        Type finalNeedType = GenericsUtils.getSuperClassGenricType(callback.getClass());
        if (finalNeedType == null) {
            return null;
        }

        // 2. 处理参数空指针：避免传入 null 导致后续表单参数异常
        if (parame == null) {
            parame = new HashMap<>();
        }

        // 3. 构建 NetworkClient：使用表单参数（params）替代 JSON 原始请求（raw）
        NetworkClient build = NetworkClient.create()
                .url(url)                  // 设置请求地址
                .params(parame)            // 传入表单参数（键值对），对应 NetworkClient 的 PARAMS 字段
                .build();                  // 构建客户端实例

        // 4. 发起表单 POST 请求：调用 post()（对应 POST 类型），而非原方法的 postraw()（POST_RAW 类型）
        return build.post()
                .compose(getSchedulersTransformerMain())  // 线程切换（与原方法一致）
                .subscribeWith(new NetAllSubscriber<>(url, finalNeedType, callback));  // 订阅回调（与原方法一致）
    }

    /**
     * post - bean请求（自动过滤null字段）
     */
    public <T> Disposable executePostAllRaw(final String url, Object bean, final ResponseCallback<T> callback) {
        Type finalNeedType = GenericsUtils.getSuperClassGenricType(callback.getClass());
        if (finalNeedType == null || bean == null) {
            return null;
        }
        NetworkClient build = NetworkClient.create()
                .raw(GsonUtils.toJson(bean))
                .url(url)
                .build();
        return build.postraw().compose(getSchedulersTransformerMain()).subscribeWith(new NetAllSubscriber<>(url, finalNeedType, callback));
    }

    /**
     * post - 同步处理
     */
    public <T> Disposable executePostAllRawSync(final String url, Map<String, Object> parames, final ResponseCallback<T> callback) {
        Type finalNeedType = GenericsUtils.getSuperClassGenricType(callback.getClass());
        if (finalNeedType == null) {
            return null;
        }
        NetworkClient build = NetworkClient.create()
                .params(parames)
                .raw(GsonUtils.toJson(parames))
                .url(url)
                .build();
        return build.postraw().compose(getSchedulersTransformerSync()).subscribeWith(new NetAllSubscriber<>(url, finalNeedType, callback));
    }

    public <T> Disposable executePostAllRawSync(final String url, Object bean, final ResponseCallback<T> callback) {
        Type finalNeedType = GenericsUtils.getSuperClassGenricType(callback.getClass());
        if (finalNeedType == null || bean == null) {
            return null;
        }
        NetworkClient build = NetworkClient.create()
                .raw(JSONObject.toJSONString(bean))
                .url(url)
                .build();
        return build.postraw().compose(getSchedulersTransformerSync()).subscribeWith(new NetAllSubscriber<>(url, finalNeedType, callback));
    }

    public <T> Disposable executeGetAllRaw(final String url, Map<String, Object> parame, final ResponseCallback<T> callback) {
        Type finalNeedType = GenericsUtils.getSuperClassGenricType(callback.getClass());
        if (finalNeedType == null) {
            return null;
        }
        NetworkClient build = NetworkClient.create()
                .raw(GsonUtils.toJson(parame))
                .url(url)
                .build();

        return build.getRaw().compose(getSchedulersTransformerMain()).subscribeWith(new NetAllSubscriber<>(url, finalNeedType, callback));
    }


    /**
     * 用于没用 result：true ；code = 0；data：{} 的数据返回解析
     */
    public <T> Disposable executePost(final String url, Map<String, Object> parames, final ResponseCallback<T> callback) {
        Type finalNeedType = GenericsUtils.getSuperClassGenricType(callback.getClass());
        if (finalNeedType == null) {
            return null;
        }
        NetworkClient build = NetworkClient.create()
                .params(parames)
//                .raw(GsonUtils.toJson(parames))
                .url(url)
                .build();

        return build.post().compose(getSchedulersTransformerMain()).subscribeWith(new NetNoRuleSubscriber<>(url, finalNeedType, callback));
    }

    public <T> Disposable executePostAll(final String url, Map<String, Object> parames, final ResponseCallback<T> callback) {
        Type finalNeedType = GenericsUtils.getSuperClassGenricType(callback.getClass());
        if (finalNeedType == null) {
            return null;
        }
        NetworkClient build = NetworkClient.create()
                .params(parames)
//                .raw(GsonUtils.toJson(parames))
                .url(url)
                .build();

        return build.post().compose(getSchedulersTransformerMain()).subscribeWith(new NetAllSubscriber<>(url, finalNeedType, callback));
    }

    public <T> Disposable executePostAsync(final String url, Map<String, Object> parames, final ResponseCallback<T> callback) {
        Type finalNeedType = GenericsUtils.getSuperClassGenricType(callback.getClass());
        if (finalNeedType == null) {
            return null;
        }
        NetworkClient build = NetworkClient.create()
                .params(parames)
                .url(url)
                .build();

        return build.post().compose(getSchedulersTransformerSync()).subscribeWith(new NetAllSubscriber<>(url, finalNeedType, callback));
    }

    public <T> Disposable executePutAll(final String url, Map<String, Object> parames, final ResponseCallback<T> callback) {
        Type finalNeedType = GenericsUtils.getSuperClassGenricType(callback.getClass());
        if (finalNeedType == null) {
            return null;
        }
        NetworkClient build = NetworkClient.create()
                .params(parames)
//                .raw(GsonUtils.toJson(parames))
                .url(url)
                .build();
        return build.put().compose(getSchedulersTransformerMain()).subscribeWith(new NetAllSubscriber<>(url, finalNeedType, callback));
    }

    /**
     * 单文件上传，可设置加密上传头
     */
    public <T> Disposable executeUpload(final String url, File file, HashMap<String, String> addHeadersMap, final ResponseCallback<T> callback) {
        Type finalNeedType = GenericsUtils.getSuperClassGenricType(callback.getClass());
        if (finalNeedType == null) {
            return null;
        }

//        Log.e("executeUpload: " + FileUtils.getSize(file));
        NetworkClient build = NetworkClient.create()
                .file(file)
                .callbback(callback)
                .url(url)
                .addHeaders(addHeadersMap)
                .build();

        return build.upload().compose(getSchedulersTransformerMain()).subscribeWith(new NetAllSubscriber<>(url, finalNeedType, callback));
    }

    /**
     * 上传gzip
     */
    public <T> Disposable executePostZip(final String url, String params, final ResponseCallback<T> callback) {

        Type finalNeedType = GenericsUtils.getSuperClassGenricType(callback.getClass());
        if (finalNeedType == null) {
            return null;
        }
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("text/plain; charset=UTF-8"), GZIPUtils.compress(EncodeUtils.urlEncode(params)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (requestBody == null) {
            callback.onError(new NetException("", "gzip post error"));
            return null;
        }
        NetworkClient build = NetworkClient.create()
                .postGzip(requestBody)
                .url(url)
                .build();

        return build.postGzip().compose(getSchedulersTransformerMain()).subscribeWith(new NetNoRuleSubscriber<>(url, finalNeedType, callback));

    }

    public static <T> FlowableTransformer<T, T> getSchedulersTransformerMain() {
        return getSchedulersTransformer(AndroidSchedulers.mainThread());
    }

    public static <T> FlowableTransformer<T, T> getSchedulersTransformerSync() {
        return getSchedulersTransformer(Schedulers.io());
    }

    public static <T> FlowableTransformer<T, T> getSchedulersTransformer(Scheduler observeScheduler) {
        FlowableTransformer<T, T> flowableTransformer = new FlowableTransformer<T, T>() {
            @NonNull
            @Override
            public Publisher<T> apply(@NonNull Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(observeScheduler);
            }
        };
        return flowableTransformer;
    }

    /**
     * 执行get请求 无参
     * 不执行过滤
     * 已用gson解析
     * 返回的是 Subscription
     */
    public <T> Disposable executeGet(final String url, ResponseCallback<T> callback) {
        Type finalNeedType = GenericsUtils.getSuperClassGenricType(callback.getClass());
        if (finalNeedType == null) {
            return null;
        }
        NetworkClient build = NetworkClient.create()
                .url(url)
                .build();

        return build.get().compose(getSchedulersTransformerMain()).subscribeWith(new NetAllSubscriber<>(url, finalNeedType, callback));
    }

    /**
     * 返回json 中， 没外层包装
     */
    public <T> Disposable executeGetNoRule(final String url, ResponseCallback<T> callback) {
        Type finalNeedType = GenericsUtils.getSuperClassGenricType(callback.getClass());
        if (finalNeedType == null) {
            return null;
        }
        NetworkClient build = NetworkClient.create()
                .url(url)
                .build();

        return build.get().compose(getSchedulersTransformerMain()).subscribeWith(new NetNoRuleSubscriber<>(url, finalNeedType, callback));
    }

    /**
     * MethodHandler
     */
    private List<Type> MethodHandler(Type mType) {
        List<Type> needtypes = new ArrayList<>();
        if (mType instanceof ParameterizedType) {
            Type[] parentypes = ((ParameterizedType) mType).getActualTypeArguments();
            for (Type childtype : parentypes) {
                Log.d(TAG, "===========childtype:=======" + childtype);
                needtypes.add(childtype);
                if (childtype instanceof ParameterizedType) {
                    Type[] childtypes = ((ParameterizedType) childtype).getActualTypeArguments();
                    for (Type type : childtypes) {
                        needtypes.add(type);
                        Log.d(TAG, "=========type:=======" + childtype);
                    }
                }
            }
        }
        return needtypes;
    }

}
