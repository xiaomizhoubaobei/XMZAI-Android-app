/**
 * @fileoverview MainMessage 数据模型
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 数据实体类，定义数据结构
 */

package xmzai.mizhoubaobei.top.data

import java.io.Serializable

/**
 * author : lzh
 * e-mail : luozhanhang@adswave.com
 * time   : 2025/9/26
 * desc   :
 * version: 1.0
 */
data class MainMessage(
    val welcomeMsg: String,
    val senMsg: String,
    val bottomMsg:String,
    val imageUrl:String
): Serializable