/**
 * @fileoverview configProxy 数据模型
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 数据实体类，定义数据结构
 */

package xmzai.mizhoubaobei.top.bean

data class configProxy(
    val username: String?,
    val password: String?,
    val port: Int?,
    val type: String?,
    val server: String?
)