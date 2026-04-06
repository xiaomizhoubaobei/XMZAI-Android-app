package xmzai.mizhoubaobei.top.widget.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView

/**
 * 验证码视图（获取验证码按钮）
 */
class VerifyCodeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var countdownSeconds: Int = 60
    private var isCountingDown: Boolean = false

    var onVerifyCodeClickListener: (() -> Unit)? = null

    fun startCountdown(seconds: Int = 60) {
        countdownSeconds = seconds
        isCountingDown = true
    }

    fun stopCountdown() {
        isCountingDown = false
        countdownSeconds = 60
    }

    fun reset() {
        isCountingDown = false
        countdownSeconds = 60
    }

    fun isCountingDown(): Boolean = isCountingDown

    override fun setEnabled(enabled: Boolean) {
        isEnabled = enabled
    }
}
