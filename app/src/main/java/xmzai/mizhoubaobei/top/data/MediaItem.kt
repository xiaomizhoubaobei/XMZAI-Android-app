/**
 * @fileoverview MediaItem 数据模型
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 数据实体类，定义数据结构
 */

package xmzai.mizhoubaobei.top.data

import android.net.Uri


data class MediaItem(
    val id: Long,
    val uri: Uri,
    val isVideo: Boolean,
    val duration: Int = 0, // 视频时长(毫秒)，图片为0
    var type: String = "复制"
)
