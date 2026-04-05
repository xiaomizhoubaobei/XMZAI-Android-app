/**
 * @fileoverview IXLogin 界面
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark Activity 或界面页面，处理用户交互和界面逻辑
 */

package xmzai.mizhoubaobei.top.ui.thirdloginsdk

import android.app.Activity
import android.content.Intent

/**
 * author : lzh
 * e-mail : luozhanhang@adswave.com
 * time   : 2024/7/16
 * desc   :
 * version: 1.0
 */
interface IXLogin {

    fun login(context: Activity, loginListener: ITLoginListener)

    fun onActivityResult(reqCode: Int, resCode: Int, data: Intent)

    fun logout(context: Activity, logoutListener: ITLogoutListener)

    fun release()
}