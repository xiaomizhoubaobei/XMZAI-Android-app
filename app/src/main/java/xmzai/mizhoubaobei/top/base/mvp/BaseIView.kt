/**
 * @fileoverview BaseIView 基础组件
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 基础框架组件，提供公共功能
 */

package xmzai.mizhoubaobei.top.base.mvp

import android.os.Bundle

/**
 * author : lzh
 * e-mail : luozhanhang@adswave.com
 * time   : 2024/7/1
 * desc   :
 * version: 1.0
 */
interface BaseIView {

    fun initData(savedInstanceState: Bundle?) //暴露给presenter，可以刷新
    fun showLoading()

    fun hideLoading()

    fun showError(errorMsg: String?) //显示错误信息，弹toast


//    fun getMyActivity(): BaseActivity?

    fun updateViews(type: Int, vararg data: Any?) //可用于刷新界面

    fun onPhoneCodeSuccess()  //发送手机验证码成功

    fun onFail()  //失败处理


}