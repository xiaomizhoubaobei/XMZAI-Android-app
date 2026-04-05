/**
 * @fileoverview RegisterActivity 界面
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark Activity 或界面页面，处理用户交互和界面逻辑
 */

package xmzai.mizhoubaobei.top.ui.login

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import xmzai.mizhoubaobei.top.R
import xmzai.mizhoubaobei.top.base.mvp.MVPBaseActivity
import xmzai.mizhoubaobei.top.databinding.ActivityRegisterBinding
import xmzai.mizhoubaobei.top.databinding.ActivityRegisterNewBinding
import xmzai.mizhoubaobei.top.ui.presenter.RegisterPresenter
import xmzai.mizhoubaobei.top.ui.thirdloginsdk.ITLoginListener
import xmzai.mizhoubaobei.top.ui.thirdloginsdk.TLoginBean
import xmzai.mizhoubaobei.top.ui.thirdloginsdk.TLoginMgr
import xmzai.mizhoubaobei.top.ui.view.IRegisterView
import xmzai.mizhoubaobei.top.utils.LogUtils
import xmzai.mizhoubaobei.top.utils.ToastUtils
import xmzai.mizhoubaobei.top.widget.ProxyActivityNavUtil
import xmzai.mizhoubaobei.top.widget.view.RegisterEmailInputView
import xmzai.mizhoubaobei.top.widget.view.RegisterPhoneInputView

/**
 * author : lzh
 * e-mail : luozhanhang@adswave.com
 * time   : 2024/5/13
 * desc   : 注册页面
 * version: 1.0
 */
class RegisterActivity :
    MVPBaseActivity<IRegisterView, ActivityRegisterNewBinding, RegisterPresenter>(), IRegisterView {//ActivityRegisterBinding
    var isLoginGoogle = false//ActivityRegisterNewBinding

    override fun createDataBinding(): ActivityRegisterNewBinding {
        return ActivityRegisterNewBinding.inflate(layoutInflater)
    }

    override fun createPresenter(): RegisterPresenter {
        return RegisterPresenter()
    }

    override fun initView() {
        setEmailSwitch(true)
    }

    override fun initListener() {
        mBinding?.llEmailRegister?.setOnClickListener {
            setEmailSwitch(true)
        }

        mBinding?.llPhoneRegister?.setOnClickListener {
            setEmailSwitch(false)
        }

        mBinding?.backImage?.setOnClickListener {
            finish()
        }

        //邮箱注册用户操作
        mBinding?.emailRegister?.setRegisterEmailInputListener(object :
            RegisterEmailInputView.RegisterEmailInputListener {
            override fun goRegister(
                userName: String,
                inviteCode: String,
                email: String,
                passWord: String,
                verifyCode: String,
                randomNum: String,
                emailCode: String,
            ) {
                /*mPresenter.getEmailCode(
                    email,
                    userName,
                    inviteCode,
                    passWord,
                    verifyCode,
                    randomNum
                )*/

                mPresenter.goRegisterEmail(
                    userName,
                    inviteCode,
                    email,
                    passWord,
                    emailCode,
                    verifyCode,
                    randomNum
                )
            }

            override fun googleRegister() {
//                TODO("Not yet implemented")
                //google
                isLoginGoogle = true
                loginGoogle()
            }
        })

        //手机号码注册
        mBinding?.phoneRegister?.setRegisterPhoneInputListener(object :
            RegisterPhoneInputView.RegisterPhoneInputListener {
            override fun goRegister(
                userName: String,
                inviteCode: String,
                phoneNumber: String,
                verifyCode: String,
                passWord: String,
                passWordAgain: String
            ) {
                mPresenter.goRegisterPhone(
                    userName,
                    phoneNumber,
                    passWord,
                    passWordAgain,
                    verifyCode,
                    inviteCode
                )
            }

            //获取手机验证码
            override fun getPhoneSmsRnyCode(
                phoneNumber: String,
                imgVerifyCode: String,
                randomNum: String
            ) {
                mPresenter.getPhoneSmsRnyCode(phoneNumber, imgVerifyCode, randomNum)
            }
        })

        mBinding?.btnBackLogin?.setOnClickListener {
            finish()
        }
    }

    /**
     * @param isEmail 是否邮箱注册 true为邮箱， false为手机  默认为邮箱登录
     */
    private fun setEmailSwitch(isEmail: Boolean) {

        mBinding?.tvEmailRegisterName?.setTextColor(
            if (isEmail) resources.getColor(R.color.color302AI) else resources.getColor(
                R.color.un_selected
            )
        )
        mBinding?.viewEmailLine?.visibility = if (isEmail) View.VISIBLE else View.GONE
        mBinding?.emailRegister?.visibility =
            if (isEmail) View.VISIBLE else View.GONE

        mBinding?.tvEmailPhoneName?.setTextColor(
            if (isEmail) resources.getColor(R.color.un_selected) else resources.getColor(
                R.color.color302AI
            )
        )
        mBinding?.viewPhoneLine?.visibility = if (isEmail) View.GONE else View.VISIBLE
        mBinding?.phoneRegister?.visibility =
            if (isEmail) View.GONE else View.VISIBLE
    }


    /**
     * 获取Email_code 成功
     */
    override fun onEmailCodeSuccess() {

    }

    /**
     * 发送手机验证码成
     */
    override fun onPhoneCodeSuccess() {
        mBinding?.phoneRegister?.setPhoneVerifyCodeState()
    }

    override fun onRegisterSuccess() {
//        TODO("Not yet implemented")
        Log.e("ceshi","onRegisterSuccess")
//        runOnUiThread {
//            ToastUtils.showShort(R.string.register_success)
//        }
        //Toast.makeText(this@RegisterActivity,R.string.register_success,Toast.LENGTH_SHORT).show()
        if(isLoginGoogle){
            ProxyActivityNavUtil.navMain(this@RegisterActivity)
        }else{
            ProxyActivityNavUtil.navLogin(this@RegisterActivity)
        }
    }

    //失败处理
    override fun onFail() {

    }

    private fun loginGoogle() {
        TLoginMgr.getInstance().login(this@RegisterActivity,
            TLoginMgr.LoginType.Google,object : ITLoginListener {
            override fun onStart(tLoginType: TLoginMgr.LoginType?) {
                LogUtils.e("ceshi  第三方登录========：onStart=====")
            }

            override fun onSuccess(result: TLoginBean?) {
                LogUtils.e("ceshi  第三方登录========：onSuccess=====:",result)
                //result?.token?.let { mPresenter.goRegisterLoginGoogle(it) }
            }

                override fun onSuccess1(token: String?) {
                    //token?.let { mPresenter.goRegisterLoginGoogle(it) }
                }

                override fun onSuccess2(user_id: String?, email: String?) {
                    user_id?.let { email?.let { it1 -> mPresenter.goRegisterLoginGoogle2(it, it1) } }
                }

            override fun onCancel() {
                LogUtils.e("ceshi  第三方登录========：onCancel=====")
            }

            override fun onFailed(errMsg: String?) {
                LogUtils.e("ceshi  第三方登录========：onFailed=====:",errMsg)
            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        TLoginMgr.getInstance().onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        isLoginGoogle = false
    }
}