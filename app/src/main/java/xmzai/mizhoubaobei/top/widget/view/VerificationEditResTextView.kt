package xmzai.mizhoubaobei.top.widget.view

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout

/**
 * 验证码编辑文本视图（注册用）
 */
class VerificationEditResTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    var verifyCode: String = ""
        get() = field
        set(value) { field = value }

    fun getCode(): String = verifyCode

    fun setHint(hint: String) {}

    fun setError(error: String) {}

    fun clearError() {}
}
