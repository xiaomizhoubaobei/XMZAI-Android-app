/**
 * @fileoverview ProxyApkBean 数据模型
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 数据实体类，定义数据结构
 */

package com.newAi302.app.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


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
data class ProxyApkBean(
    val min_version: String = "",
    val recommend_version: String = "",
    val latest_download_url: String = ""
) : Parcelable