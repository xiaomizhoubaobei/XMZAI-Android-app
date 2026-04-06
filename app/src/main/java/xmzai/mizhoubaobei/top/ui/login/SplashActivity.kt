/**
 * @fileoverview SplashActivity 界面
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark Activity 或界面页面，处理用户交互和界面逻辑
 */

package xmzai.mizhoubaobei.top.ui.login

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import xmzai.mizhoubaobei.top.R
import xmzai.mizhoubaobei.top.base.BaseActivity
//import xmzai.mizhoubaobei.top.util.LogUtils
import xmzai.mizhoubaobei.top.utils.SP
import xmzai.mizhoubaobei.top.utils.ThreadUtils
import xmzai.mizhoubaobei.top.utils.base.WearData
import xmzai.mizhoubaobei.top.widget.ProxyActivityNavUtil

/**
 * author : lzh
 * e-mail : luozhanhang@adswave.com
 * time   : 2024/7/1
 * desc   : 欢迎页
 * version: 1.0
 */
class SplashActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        SP.getInstance()

        initView()
    }


    private fun initView() {
        // 直接跳转到主页
        navMain()
    }

    //去主页
    private fun navMain() {
        ProxyActivityNavUtil.navMain(this@SplashActivity)
        ThreadUtils.runOnUiThreadDelayed({ finish() }, 1000)
    }
}