/**
 * @fileoverview IRegisterView 界面
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
 * time   : 2024/5/13
 * desc   : 注册 view
 * version: 1.0
 */
interface IRegisterView : BaseIView {

    fun onEmailCodeSuccess()  //获取email_code成功

    override fun onPhoneCodeSuccess()  //发送手机验证码成功

    fun onRegisterSuccess()   //注册成功

    override fun onFail()  //失败处理
}