/**
 * @fileoverview NetConfig 网络模块
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 网络请求相关组件
 */

package xmzai.mizhoubaobei.top.network;

/**
 * author : lzh
 * e-mail : luozhanhang@adswave.com
 * time   : 2024/5/7
 * desc   : 网络请求URL常量类
 * version: 1.0
 */
class NetConfig {

    companion object {
        @JvmField
        val URL_FORWARD_DOMAIN = "/api/forward/domain" //获取中转域名的接口

        @JvmField
        val URL_PROXY_GET_APK: String = "/api/v1/app/version"

        @JvmField
        val URL_PROXY_SECRET: String = "/api/proxy/secret"
    }
}
