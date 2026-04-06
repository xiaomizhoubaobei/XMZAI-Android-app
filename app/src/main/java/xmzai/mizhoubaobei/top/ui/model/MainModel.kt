/**
 * @fileoverview MainModel 界面
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark Activity 或界面页面，处理用户交互和界面逻辑
 */

package xmzai.mizhoubaobei.top.ui.model

import android.util.Log
import xmzai.mizhoubaobei.top.bean.ForwardDomainBean
import xmzai.mizhoubaobei.top.bean.ImageVerifyCodeBean
import xmzai.mizhoubaobei.top.bean.ProxyApkBean
import xmzai.mizhoubaobei.top.bean.ProxySecretBean
import xmzai.mizhoubaobei.top.network.NetConfig
import xmzai.mizhoubaobei.top.network.NetworkUtil
import xmzai.mizhoubaobei.top.network.common_bean.bean.BaseResponse
import xmzai.mizhoubaobei.top.network.common_bean.callback.RequestCallback
import xmzai.mizhoubaobei.top.network.common_bean.callback.ResponseCallback
import xmzai.mizhoubaobei.top.network.common_bean.exception.NetException
import xmzai.mizhoubaobei.top.utils.LogUtils
import xmzai.mizhoubaobei.top.utils.ToastUtils

/**
 * author : lzh
 * e-mail : luozhanhang@adswave.com
 * time   : 2024/5/14
 * desc   :
 * version: 1.0
 */
object MainModel {

    /**
     * 获取图片验证码
     */
    fun getProxyStaticImage(
        url: String,
        callback: RequestCallback<BaseResponse<ImageVerifyCodeBean>>
    ) {
        NetworkUtil.getInstance().executeGet(
            url,
            object : ResponseCallback<BaseResponse<ImageVerifyCodeBean>>() {
                override fun onSuccess(response: BaseResponse<ImageVerifyCodeBean>?) {

                }

                override fun onError(e: NetException?) {

                }

                override fun onSuccessImg(bytes: ByteArray?) {
                    super.onSuccessImg(bytes)
                    callback.imgSuccess(bytes)
                }
            })
    }

    /**
     * 获取代理的加密密钥
     */
    fun getProxySecret(
        network: String,
        callback: RequestCallback<BaseResponse<ProxySecretBean>>
    ) {
        val hashMap: MutableMap<String, Any> = HashMap()
        hashMap["network"] = network

        NetworkUtil.getInstance()
            .executeGetAllRaw(
                NetConfig.URL_PROXY_SECRET,
                hashMap,
                object : ResponseCallback<BaseResponse<ProxySecretBean>>() {
                    override fun onSuccess(response: BaseResponse<ProxySecretBean>?) {
                        callback.onSuccess(response)
                    }

                    override fun onError(e: NetException?) {
                        callback.onError(e)
                    }
                })
    }

    fun getProxyApk(
        callback: RequestCallback<BaseResponse<ProxyApkBean>>
    ) {
        NetworkUtil.getInstance()
            .executeGet(
                NetConfig.URL_PROXY_GET_APK,
                object : ResponseCallback<BaseResponse<ProxyApkBean>>() {
                    override fun onSuccess(response: BaseResponse<ProxyApkBean>?) {
                        callback.onSuccess(response)
                    }

                    override fun onError(e: NetException?) {
                        callback.onError(e)
                    }
                })
    }

    /**
     * 获取中转域名的接口
     */
    fun getForwardDomain(callback: RequestCallback<BaseResponse<ForwardDomainBean>>) {
        NetworkUtil.getInstance().executeGet(NetConfig.URL_FORWARD_DOMAIN,
            object : ResponseCallback<BaseResponse<ForwardDomainBean>>() {
                override fun onSuccess(response: BaseResponse<ForwardDomainBean>?) {
                    callback.onSuccess(response)
                }

                override fun onError(e: NetException?) {
                    callback.onError(e)
                }
            })
    }

}