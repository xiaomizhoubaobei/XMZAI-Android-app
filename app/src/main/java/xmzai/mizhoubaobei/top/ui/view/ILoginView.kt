/**
 * @fileoverview ILoginView 界面
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark Activity 或界面页面，处理用户交互和界面逻辑
 */

package xmzai.mizhoubaobei.top.ui.view

import xmzai.mizhoubaobei.top.base.mvp.BaseIView
import xmzai.mizhoubaobei.top.bean.LoginBean
import xmzai.mizhoubaobei.top.network.common_bean.exception.NetException

/**
 * author : lzh
 * e-mail : luozhanhang@adswave.com
 * time   : 2024/5/7
 * desc   : 登录 view
 * version: 1.0
 */
interface ILoginView : BaseIView {

    fun onLoginSuccess(bean: LoginBean?)  //登录成功

    fun onLoginFail(msg: NetException?)  //登录失败
}