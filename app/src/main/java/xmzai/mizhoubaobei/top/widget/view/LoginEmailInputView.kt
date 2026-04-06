package xmzai.mizhoubaobei.top.widget.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.view.isVisible

/**
 * 登录邮箱输入视图
 */
class LoginEmailInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var email: String = ""
        get() = field
        set(value) { field = value }

    fun setHint(hint: String) {}

    fun getText(): String = email

    fun setError(error: String) {}

    fun clearError() {}
}
