/**
 * @fileoverview IForgetPassWordView 界面
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark Activity 或界面页面，处理用户交互和界面逻辑
 */

package com.newAi302.app.ui.view

import com.newAi302.app.base.mvp.BaseIView

/**
 * author : lzh
 * e-mail : luozhanhang@adswave.com
 * time   : 2024/5/14
 * desc   : 忘记密码 view
 * version: 1.0
 */
interface IForgetPassWordView : BaseIView {

    override fun onPhoneCodeSuccess()  //发送手机验证码成功

    override fun onFail()  //失败处理

    fun onChangePhonePassWordSuccess()
}