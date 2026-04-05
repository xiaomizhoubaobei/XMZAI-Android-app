/**
 * @fileoverview ForgetPassWordEmailActivity 界面
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark Activity 或界面页面，处理用户交互和界面逻辑
 */

package xmzai.mizhoubaobei.top.ui.login

import android.text.TextUtils
import androidx.core.widget.addTextChangedListener
import xmzai.mizhoubaobei.top.base.mvp.MVPBaseActivity
import xmzai.mizhoubaobei.top.R
import xmzai.mizhoubaobei.top.databinding.ActivityForgetPasswordBinding
import xmzai.mizhoubaobei.top.ui.presenter.ForgetPassWordPresenter
import xmzai.mizhoubaobei.top.ui.view.IForgetPassWordView
import xmzai.mizhoubaobei.top.widget.ProxyActivityNavUtil
import xmzai.mizhoubaobei.top.widget.utils.CommonUtils
import xmzai.mizhoubaobei.top.widget.view.VerificationEditTextView
import xmzai.mizhoubaobei.top.widget.view.VerifyCodeView

/**
 * author : lzh
 * e-mail : luozhanhang@adswave.com
 * time   : 2024/5/14
 * desc   : 忘记密码 activity
 * version: 1.0
 */
class ForgetPassWordEmailActivity :
     MVPBaseActivity<IForgetPassWordView, ActivityForgetPasswordBinding, ForgetPassWordPresenter>(),IForgetPassWordView {

     private var mEmail = ""       //输入的邮箱
     private var mVerifyCode = ""  //图形验证码
     private var mRandomCode = ""  //图形验证码生成的字符串

     override fun createDataBinding(): ActivityForgetPasswordBinding {
         return ActivityForgetPasswordBinding.inflate(layoutInflater)
     }

     override fun initListener() {
         mBinding?.editEmail?.addTextChangedListener {
             mEmail = it.toString()
             mBinding?.tvEmptyTipEmail?.text =
                 if (TextUtils.isEmpty(it.toString())) resources.getString(R.string.email_empty) else ""
         }

         //输入的验证码
         mBinding?.verificationInput?.setVerifyCodeListener(object :
             VerificationEditTextView.VerifyCodeListener {
             override fun verifyCode(code: String) {
                 mVerifyCode = code
             }
         })

         //图形验证码
         mBinding?.verifyCodeView?.setVerifyCodeViewListener(object :
             VerifyCodeView.VerifyCodeViewListener {
             override fun randomStr(randomNum: String) {
                 mRandomCode = randomNum
             }
         })

         //修改密码
         mBinding?.btnResetPass?.setOnClickListener {
             //判断邮箱不为空
             var mIsEmailEmpty = false
             if (!TextUtils.isEmpty(mEmail.trim())) {
                 //判断邮箱是否合法
                 val isValidEmail = CommonUtils.isValidEmail(mEmail)
                 if (isValidEmail) {
                     mIsEmailEmpty = true
                     mBinding?.tvEmptyTipEmail?.text = ""
                 } else {
                     mBinding?.tvEmptyTipEmail?.text = resources.getString(R.string.email_illegal)
                 }
             } else {
                 mBinding?.tvEmptyTipEmail?.text = resources.getString(R.string.email_empty)
             }

             //验证码判断
             val isCode = mBinding?.verificationInput?.setVerifyCodeEmpty()

             if (mIsEmailEmpty && isCode == true) {
                 mPresenter.resetLoginPassWordEmail(mEmail, mVerifyCode, mRandomCode)
             }
         }

         mBinding?.btnBackLogin?.setOnClickListener {
             finish()
         }

         mBinding?.tvNoAccount?.setOnClickListener {
             ProxyActivityNavUtil.navToRegister(this@ForgetPassWordEmailActivity)
             finish()
         }
     }

     override fun initView() {

     }

     override fun createPresenter(): ForgetPassWordPresenter {
         return ForgetPassWordPresenter()
     }

    override fun onPhoneCodeSuccess() {
        finish()
    }

    override fun onFail() {

    }

    override fun onChangePhonePassWordSuccess() {

    }

}