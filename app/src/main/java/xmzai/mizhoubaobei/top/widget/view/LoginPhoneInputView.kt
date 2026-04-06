package xmzai.mizhoubaobei.top.widget.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

/**
 * 登录手机号输入视图
 */
class LoginPhoneInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var phoneValue: String = ""

    fun setPhoneLocation(code: String) {}

    fun getPhone(): String = phoneValue

    fun setPhone(phone: String) {
        phoneValue = phone
    }

    fun setHint(hint: String) {}

    fun setError(error: String) {}

    fun clearError() {}
}
