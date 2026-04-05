/**
 * @fileoverview IBitmapConvert 截图功能
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 截图相关功能实现
 */

package xmzai.mizhoubaobei.top.screenshot.model.i

import android.view.View

/**
 * description:
 * author: bear .
 * Created date:  2019-06-25.
 * mail:2280885690@qq.com
 */
interface IBitmapConvert {
    fun convert(view: View, callBack: IBitmapConvertCallBack?)
}