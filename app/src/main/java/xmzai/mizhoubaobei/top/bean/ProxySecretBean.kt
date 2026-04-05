/**
 * @fileoverview ProxySecretBean 数据模型
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 数据实体类，定义数据结构
 */

package xmzai.mizhoubaobei.top.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * author : lzh
 * e-mail : luozhanhang@adswave.com
 * time   : 2024/7/22
 * desc   : 代理加密密钥
 * version: 1.0
 */

/*{
    "code": 0,
    "msg": "success",
    "data": {
    "value": "g1wqp68zfkohjpb8q990aezmjp5kq4ka",
    "domain": "proxy.runfordec.autos",
    "port": 4100
 }
}*/

@Parcelize
data class ProxySecretBean(
    val value: String = "",
    val domain: String = "",
    val port: Long = 0
) : Parcelable