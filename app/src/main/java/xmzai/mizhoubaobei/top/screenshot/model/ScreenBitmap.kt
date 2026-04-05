/**
 * @fileoverview ScreenBitmap 截图功能
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 截图相关功能实现
 */

package xmzai.mizhoubaobei.top.screenshot.model

import android.graphics.Bitmap

/**
 * description: 抽象截屏对象
 * author: bear .
 * Created date:  2019-06-24.
 * mail:2280885690@qq.com
 */
class ScreenBitmap {
    var name: String = ""
    var bitmap: Bitmap? = null
    var filePath: String = ""
}