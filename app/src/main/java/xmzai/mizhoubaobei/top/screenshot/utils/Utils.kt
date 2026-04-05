/**
 * @fileoverview Utils 工具类
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 工具方法集合，提供通用功能支持
 */

package xmzai.mizhoubaobei.top.screenshot.utils

import android.content.Context

/**
 * description:
 * author: bear .
 * Created date:  2019-06-25.
 * mail:2280885690@qq.com
 */
fun dip2px(context: Context,dpValue: Float): Int {
    return (dpValue * context.resources.displayMetrics.density + 0.5f).toInt()
}

fun px2dip(context: Context,pxValue: Float): Int {
    return (pxValue / context.resources.displayMetrics.density + 0.5f).toInt()
}