/**
 * @fileoverview ProxyActivityNavUtil 界面
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark Activity 或界面页面，处理用户交互和界面逻辑
 */

package xmzai.mizhoubaobei.top.widget

import android.app.Activity
import android.content.Intent
import xmzai.mizhoubaobei.top.MainActivity


/**
 * author : lzh
 * e-mail : luozhanhang@adswave.com
 * time   : 2024/5/17
 * desc   : 公共页跳转管理类
 * version: 1.0
 */
class ProxyActivityNavUtil {

    companion object {

        /**
         * 跳转到主页
         */
        fun navMain(activity: Activity) {
            val intent = Intent(activity, MainActivity::class.java)//MainActivity
            activity.startActivity(intent)
        }
    }
}