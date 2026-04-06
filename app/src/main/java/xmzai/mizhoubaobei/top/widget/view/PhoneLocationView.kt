package xmzai.mizhoubaobei.top.widget.view

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout

/**
 * 手机区号选择视图
 */
class PhoneLocationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var countryCodeValue: String = "+86"

    fun getCountryCode(): String = countryCodeValue

    fun setCountryCode(code: String) {
        countryCodeValue = code
    }
}
