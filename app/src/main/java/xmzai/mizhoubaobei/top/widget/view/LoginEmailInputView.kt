/**
 * @fileoverview LoginEmailInputView 自定义视图
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 自定义 UI 组件
 */

package xmzai.mizhoubaobei.top.widget.view

import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.core.widget.addTextChangedListener
import xmzai.mizhoubaobei.top.R
import xmzai.mizhoubaobei.top.databinding.LayoutPasswordLoginBinding
import xmzai.mizhoubaobei.top.utils.StringUtils
import xmzai.mizhoubaobei.top.utils.ToastUtils
import xmzai.mizhoubaobei.top.utils.base.WearData
import xmzai.mizhoubaobei.top.widget.listener.IClickListener
import xmzai.mizhoubaobei.top.widget.utils.CommonEnum
import xmzai.mizhoubaobei.top.widget.utils.CommonUtils

/**
 * author : lzh
 * e-mail : luozhanhang@adswave.com
 * time   : 2024/5/13
 * desc   : 邮箱登录 用户操作 view
 * version: 1.0
 */
class LoginEmailInputView(context: Context, attrs: AttributeSet? = null) :
    RelativeLayout(context, attrs) {

    private var mEmail = ""        //邮箱
    private var mPassWord = ""     //密码
    private var mVerifyCode = ""   //输入的验证码
    private var mRandomNum = ""    //图片验证码生产的随机数
    private var mListener: LoginEmailListener? = null
    //private lateinit var mBinding: LayoutPasswordLoginBinding

    val mBinding: LayoutPasswordLoginBinding by lazy {
        //Log.e("ceshi","0来来来来来")
        LayoutPasswordLoginBinding.bind(View.inflate(context, R.layout.layout_password_login_new, this))
    }

//    val mBinding: LayoutPasswordLoginBinding by lazy {
//        Log.e("ceshi", "开始初始化 mBinding：加载 layout_password_login")
//        val rootView = try {
//            View.inflate(context, R.layout.layout_password_login, null)
//        } catch (e: Exception) {
//            Log.e("ceshi", "加载布局失败：${e.message}", e)
//            throw e // 抛出异常，让日志显示具体错误
//        }
//        Log.e("ceshi", "布局加载成功，根 View 类型：${rootView.javaClass.simpleName}")
//
//        this.addView(rootView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
//
//        val binding = try {
//            LayoutPasswordLoginBinding.bind(rootView)
//        } catch (e: Exception) {
//            Log.e("ceshi", "Binding 绑定失败：${e.message}", e)
//            throw e
//        }
//        Log.e("ceshi", "mBinding 初始化成功")
//        binding
//    }

    init {
        //Log.e("ceshi","来来来来来")
        initView()
        initListener()
    }

    private fun initView() {
        val deviceModel = Build.MODEL
        if (deviceModel == "2411DRN47C"){
            mBinding.rlLoginGoogle.visibility = View.GONE
        }else{
            //mBinding.rlLoginGoogle.visibility = View.VISIBLE
        }
        mBinding.checkbox.isChecked = WearData.getInstance().isRememberPassWord
        mBinding.checkbox1.isChecked = WearData.getInstance().isReadAndAgreeUTsAndPAs
        mEmail =
            if (!TextUtils.isEmpty(WearData.getInstance().emailCode)) WearData.getInstance().emailCode else ""
        mBinding.editEmail.setText(mEmail)
        mBinding.rlPassword.setCurrentLoginType(CommonEnum.PassWordType.EMAIL)
    }


    private fun initListener() {
        //邮箱
        mBinding.editEmail.addTextChangedListener {
            mEmail = it.toString()
            mBinding.tvEmptyTipEmail.text =
                if (StringUtils.isEmpty(it.toString())) resources.getString(R.string.email_empty) else ""
        }

        //密码
        mBinding.rlPassword.setLoginPassWordListener(object :
            PassWorkEditView.LoginPasswordListener {
            override fun inputContent(password: String) {
                mPassWord = password
            }

            override fun eyeSwitch(isSwitch: Boolean) {

            }

            override fun cleanInput() {

            }
        })

        //验证码
        mBinding.verificationInput.setVerifyCodeListener(object :
            VerificationEditTextView.VerifyCodeListener {
            override fun verifyCode(code: String) {
                mVerifyCode = code
            }
        })

        //刷新验证码
        mBinding.verifyCodeView.setVerifyCodeViewListener(object :
            VerifyCodeView.VerifyCodeViewListener {
            override fun randomStr(randomNum: String) {
                mRandomNum = randomNum
            }
        })

        //记住密码
        mBinding.checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            WearData.getInstance().saveRememberPassword(isChecked)
        }

        //忘记密码
        mBinding.tvForgetPassword.setOnClickListener(object : IClickListener() {
            override fun onIClick(v: View?) {
                if (mListener != null) {
                    mListener?.onForgetPassWord()
                }
            }
        })

        //登录
        mBinding.btnLogin.setOnClickListener(object : IClickListener() {
            override fun onIClick(v: View?) {
                mBinding.rlPassword.setType(false)
                //判断邮箱不为空
                var mIsEmailEmpty = false
                if (!TextUtils.isEmpty(mEmail.trim())) {
                    //判断邮箱是否合法
                    var isValidEmail = CommonUtils.isValidEmail(mEmail)
                    isValidEmail = true
                    if (isValidEmail) {
                        mIsEmailEmpty = true
                        mBinding.tvEmptyTipEmail.text = ""
                    } else {
                        mBinding.tvEmptyTipEmail.text = resources.getString(R.string.email_illegal)
                    }
                } else {
                    mBinding.tvEmptyTipEmail.text = resources.getString(R.string.email_empty)
                }

                //输入的密码是否满足条件
                val mIsFillPassWork: Boolean = if (!TextUtils.isEmpty(mPassWord.trim())) {
                    mBinding.rlPassword.getIsPassWorkTerm()
                } else {
                    if (WearData.getInstance().isRememberPassWord) {
                        mPassWord = WearData.getInstance().emailPassWord
                    }else{
                        mPassWord =
                            if (!TextUtils.isEmpty(WearData.getInstance().emailPassWord)) WearData.getInstance().emailPassWord else ""
                    }
                    mBinding.rlPassword.setPassWordIsEmpty()
                    //false
                }

                //验证码判断
                val isCode = mBinding.verificationInput.setVerifyCodeEmpty()

                if (mIsEmailEmpty && mIsFillPassWork && isCode) {
                    if (mListener != null) {
                        val isAgree = WearData.getInstance().isReadAndAgreeUTsAndPAs
                        if (isAgree) {
                            mListener?.onLogin(
                                mEmail.trim(),
                                mPassWord.trim(),
                                mVerifyCode.trim(),
                                mRandomNum
                            )
                        }else {
                            ToastUtils.showShort(R.string.no_agree_error)
                        }


                    }
                }
            }
        })

        mBinding.checkbox1.setOnCheckedChangeListener { buttonView, isChecked ->
            WearData.getInstance().saveReadAndAgreeUTsAndPAs(isChecked)
        }

        mBinding.tvUserTerms.setOnClickListener(object : IClickListener() {
            override fun onIClick(v: View?) {
                if (mListener != null) {
                    mListener?.readMessage("UTs")
                }
            }
        })

        mBinding.tvPrivacyAgreement.setOnClickListener(object : IClickListener() {
            override fun onIClick(v: View?) {
                if (mListener != null) {
                    mListener?.readMessage("PAs")
                }
            }
        })

        //google登录
        mBinding.rlLoginGoogle.setOnClickListener(object : IClickListener() {
            override fun onIClick(v: View?) {
                if (mListener != null) {
                    val isAgree = WearData.getInstance().isReadAndAgreeUTsAndPAs
                    if (isAgree) {
                        mListener?.onGoogleLogin()
                    }else {
                        ToastUtils.showShort(R.string.no_agree_error)
                    }
                }
            }
        })
    }

    interface LoginEmailListener {
        fun onForgetPassWord()

        fun onLogin(email: String, passWord: String, verifyCode: String, randomNum: String)

        fun onGoogleLogin()

        fun readMessage(type:String)

    }

    fun setLoginEmailListener(listener: LoginEmailListener) {
        this.mListener = listener
    }

}