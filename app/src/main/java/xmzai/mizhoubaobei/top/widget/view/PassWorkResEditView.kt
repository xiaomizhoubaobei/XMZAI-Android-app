package xmzai.mizhoubaobei.top.widget.view

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout

/**
 * 密码编辑视图（注册用）
 */
class PassWorkResEditView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var passwordValue: String = ""

    fun getPassword(): String = passwordValue

    fun setPassword(password: String) {
        passwordValue = password
    }

    fun setHint(hint: String) {}

    fun setError(error: String) {}

    fun clearError() {}
}
