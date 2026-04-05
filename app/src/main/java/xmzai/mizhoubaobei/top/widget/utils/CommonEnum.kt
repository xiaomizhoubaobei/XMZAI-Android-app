/**
 * @fileoverview CommonEnum 工具类
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 工具方法集合，提供通用功能支持
 */

package xmzai.mizhoubaobei.top.widget.utils

/**
 * author : lzh
 * e-mail : luozhanhang@adswave.com
 * time   : 2024/6/3
 * desc   : 全局枚举
 * version: 1.0
 */
object CommonEnum {


    interface PassWordType {
        companion object {
            const val EMAIL: Int = 1  //邮箱
            const val PHONE: Int = 2  //手机号码
        }
    }

    //加载html路径
    interface LoadHtmlType {
        companion object {
            const val NEED_SPLICED_PARA: Int = 1      //需拼接参数  如：汇总
            const val NOT_NEED_SPLICED_PARA: Int = 2  //不需要拼接参数
            const val DIRECT_LINK: Int = 3            //直接链接
        }
    }

    //proxyType
    interface ProxyType {
        companion object {
            const val PROXY_TYPE_SOCKS: String = "socks5"
            const val PROXY_TYPE_HTTP: String = "http"
        }
    }
}