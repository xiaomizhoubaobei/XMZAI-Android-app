/**
 * @fileoverview KeyboardUtils 工具类
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 工具方法集合，提供通用功能支持
 */

package xmzai.mizhoubaobei.top.utils

import android.app.Activity
import android.graphics.Rect
import android.view.ViewTreeObserver

/**
 * author :
 * e-mail :
 * time   : 2025/5/12
 * desc   :
 * version: 1.0
 */
class KeyboardUtils(private val activity: Activity) {
    private var isKeyboardVisible = false
    private var globalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener? = null

    // 状态监听回调（可选）
    var onKeyboardStatusChanged: ((Boolean) -> Unit)? = null

    /**
     * 即时查询软键盘是否显示
     */
    fun isKeyboardShowing(): Boolean {
        val rootView = activity.window.decorView.rootView
        val rect = Rect()
        // 获取窗口可见区域（不包含软键盘）
        activity.window.decorView.getWindowVisibleDisplayFrame(rect)

        // 屏幕总高度（减去状态栏高度，可选）
        val screenHeight = rootView.height
        // 可见区域底部坐标（软键盘顶部坐标）
        val visibleBottom = rect.bottom

        // 软键盘高度 = 屏幕总高度 - 可见区域底部坐标
        val keyboardHeight = screenHeight - visibleBottom
        return keyboardHeight > 200  // 阈值：超过 200px 视为软键盘显示
    }

    /**
     * 注册状态监听（可选，需要实时回调时使用）
     */
    fun registerKeyboardListener() {
        val rootView = activity.window.decorView.rootView
        globalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
            val newStatus = isKeyboardShowing()
            if (newStatus != isKeyboardVisible) {
                isKeyboardVisible = newStatus
                onKeyboardStatusChanged?.invoke(isKeyboardVisible)
            }
        }
        rootView.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
    }

    /**
     * 注销监听（避免内存泄漏）
     */
    fun unregisterKeyboardListener() {
        val rootView = activity.window.decorView.rootView
        globalLayoutListener?.let {
            rootView.viewTreeObserver.removeOnGlobalLayoutListener(it)
            globalLayoutListener = null
        }
    }
}