/**
 * @fileoverview ResponseData 网络模块
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 网络请求相关组件
 */

package xmzai.mizhoubaobei.top.network.common_bean.callback

import xmzai.mizhoubaobei.top.bean.LoginBean

data class ResponseData(
    val code: Int,
    val msg: String,
    val data: LoginBean
)

data class TokenData(
    val token: String
)