/**
 * @fileoverview LoginPresenter 界面
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark Activity 或界面页面，处理用户交互和界面逻辑
 */

package xmzai.mizhoubaobei.top.ui.presenter

import xmzai.mizhoubaobei.top.R
import xmzai.mizhoubaobei.top.base.mvp.BasePresenter
import xmzai.mizhoubaobei.top.bean.LoginBean
import xmzai.mizhoubaobei.top.constant.AppConstant
import xmzai.mizhoubaobei.top.network.common_bean.callback.LoginCallback
import xmzai.mizhoubaobei.top.network.common_bean.callback.ResponseData
import xmzai.mizhoubaobei.top.network.common_bean.bean.BaseResponse
import xmzai.mizhoubaobei.top.network.common_bean.callback.RequestCallback
import xmzai.mizhoubaobei.top.network.common_bean.exception.NetException
import xmzai.mizhoubaobei.top.ui.model.LoginModel
import xmzai.mizhoubaobei.top.ui.view.ILoginView
import xmzai.mizhoubaobei.top.utils.LogUtils
import xmzai.mizhoubaobei.top.utils.ToastUtils
import xmzai.mizhoubaobei.top.utils.base.WearData
import com.google.gson.Gson
import xmzai.mizhoubaobei.top.bean.PhoneSmsRnyCodeBean

/**
 * author : lzh
 * e-mail : luozhanhang@adswave.com
 * time   : 2024/5/7
 * desc   :
 * version: 1.0
 */
class LoginPresenter : BasePresenter<ILoginView>() {

    /**
     * 邮箱登录
     * @param email 邮箱
     * @param passWord 密码
     * @param verifyCode 验证码
     * @param code 获取验证码随机生产的随机码
     */
    fun goLogin(email: String, passWord: String, verifyCode: String, code: String) {
        if (getView() != null) {
            getView()?.showLoading()
        }
        val hashMap: MutableMap<String, Any> = HashMap()
        hashMap["email"] = email
        hashMap["phone"] = ""
        hashMap["password"] = passWord
        hashMap["ref"] = ""
        hashMap["event"] = ""
        hashMap["captcha"] = verifyCode //验证码
        hashMap["code"] = code
        hashMap["login_from"] = AppConstant.loginForm  //登录来源
        LoginModel.goLogin(hashMap, object : RequestCallback<BaseResponse<LoginBean>>() {
            override fun onSuccess(data: BaseResponse<LoginBean>?) {
                if (getView() != null) {
                    getView()?.hideLoading()
                    getView()?.onLoginSuccess(data?.data)
                    WearData.getInstance().saveLoginEmailCode(email)
                    WearData.getInstance().saveLoginEmailPassWord(passWord)
                }

//                LogUtils.e("ceshi 接口返回的内容是什么==========：", data?.data?.token)
            }

            override fun onError(e: NetException?) {
                if (getView() != null) {
                    getView()?.hideLoading()
                    getView()?.onLoginFail(e)
                }
                when (e?.code) {
                    NetException.VERIFICATION_CODE_ERROR -> {
                        ToastUtils.showShort(R.string.verify_code_error)
                    }

                    NetException.LOGIN_WRONG_PASSWORD -> {
                        ToastUtils.showShort(R.string.login_password_error)
                    }
                }
            }
        })
    }

    fun goLoginAll(email: String, passWord: String, verifyCode: String, code: String) {
        if (getView() != null) {
            getView()?.showLoading()
        }
        val hashMap: MutableMap<String, Any> = HashMap()
        hashMap["account"] = email
        hashMap["phone"] = ""
        hashMap["password"] = passWord
        hashMap["ref"] = ""
        hashMap["event"] = ""
        hashMap["captcha"] = verifyCode //验证码
        hashMap["code"] = code
        hashMap["login_from"] = AppConstant.loginForm  //登录来源
        LoginModel.goLoginAll(hashMap, object : RequestCallback<BaseResponse<LoginBean>>() {
            override fun onSuccess(data: BaseResponse<LoginBean>?) {
                if (getView() != null) {
                    getView()?.hideLoading()
                    getView()?.onLoginSuccess(data?.data)
                    WearData.getInstance().saveLoginEmailCode(email)
                    WearData.getInstance().saveLoginEmailPassWord(passWord)
                }

//                LogUtils.e("ceshi 接口返回的内容是什么==========：", data?.data?.token)
            }

            override fun onError(e: NetException?) {
                if (getView() != null) {
                    getView()?.hideLoading()
                    getView()?.onLoginFail(e)
                }
                when (e?.code) {
                    NetException.VERIFICATION_CODE_ERROR -> {
                        ToastUtils.showShort(R.string.verify_code_error)
                    }

                    NetException.LOGIN_WRONG_PASSWORD -> {
                        ToastUtils.showShort(R.string.login_password_error)
                    }
                }
            }
        })
    }

    /**
     * 获取容联云手机验证码
     * @param mobile
     * @param verifyCode 图形验证码
     * @param code 生成图形验证码的字符串
     */
    fun getPhoneSmsRnyCode(mobile: String, captcha: String, code: String) {
        if (getView() != null) {
            getView()?.showLoading()
        }
        val hashMap: MutableMap<String, Any> = HashMap()
        hashMap["mobile"] = mobile
        hashMap["captcha"] = captcha
        hashMap["code"] = code
        LoginModel.getUserSmsRnyCode(hashMap,
            object : RequestCallback<BaseResponse<PhoneSmsRnyCodeBean>>() {
                override fun onSuccess(data: BaseResponse<PhoneSmsRnyCodeBean>?) {
                    if (getView() != null) {
                        getView()?.onPhoneCodeSuccess()
                        getView()?.hideLoading()
                    }
                }

                override fun onError(e: NetException?) {
                    if (getView() != null) {
                        getView()?.onFail()
                        getView()?.hideLoading()
                    }
                    when (e?.code) {
                        NetException.VERIFICATION_CODE_ERROR -> {
                            ToastUtils.showShort(R.string.verify_code_error)
                        }

                        NetException.LOGIN_WRONG_PASSWORD -> {
                            ToastUtils.showShort(R.string.login_password_error)
                        }

                        NetException.ALREADY_REGISTERED -> {
                            ToastUtils.showShort(R.string.register_already_email_error)
                        }
                    }
                }
            })
    }

    /**
     * 手机号码登录
     */
    fun goLoginPhone(
        phoneNumber: String,
        password: String,
        verifyCode: String,
        code: String
    ) {
        if (getView() != null) {
            getView()?.showLoading()
        }
        val hashMap: MutableMap<String, Any> = HashMap()
        hashMap["email"] = ""
        hashMap["phone"] = phoneNumber
        hashMap["password"] = password
        hashMap["ref"] = ""
        hashMap["event"] = ""
        hashMap["captcha"] = verifyCode //验证码
        hashMap["code"] = code
        hashMap["login_from"] = AppConstant.loginForm  //登录来源
        LoginModel.goLoginPhone(hashMap, object : RequestCallback<BaseResponse<LoginBean>>() {
            override fun onSuccess(data: BaseResponse<LoginBean>?) {
                if (getView() != null) {
                    getView()?.hideLoading()
                    getView()?.onLoginSuccess(data?.data)
                    WearData.getInstance().saveLoginPhoneCode(phoneNumber)
                    WearData.getInstance().saveLoginPhonePassWord(password)
                    LogUtils.e("ceshi ======手机号码登录成功啦=========：", data?.data?.token)
                }
            }

            override fun onError(e: NetException?) {
//                LogUtils.e("ceshi  phone接口返回的错误===========：", e?.code, e?.message)
                if (getView() != null) {
                    getView()?.hideLoading()
                    getView()?.onLoginFail(e)
                }

                when (e?.code) {
                    NetException.VERIFICATION_CODE_ERROR -> {
                        ToastUtils.showShort(R.string.verify_code_error)
                    }

                    NetException.LOGIN_WRONG_PASSWORD -> {
                        ToastUtils.showShort(R.string.login_password_error)
                    }
                }
            }
        })
    }

    fun goLoginPhoneSms(
        phoneNumber: String,
        password: String,
        verifyCode: String,
        code: String
    ) {
        if (getView() != null) {
            getView()?.showLoading()
        }
        val hashMap: MutableMap<String, Any> = HashMap()
        hashMap["email"] = ""
        hashMap["id_token"] = ""
        hashMap["phone_number"] = phoneNumber
        hashMap["sms_code"] = password
        hashMap["ref"] = ""
        hashMap["event"] = ""
        hashMap["captcha"] = verifyCode //验证码
        hashMap["code"] = code
        hashMap["login_from"] = AppConstant.loginForm  //登录来源
        LoginModel.goLoginSmsPhone(hashMap, object : RequestCallback<BaseResponse<LoginBean>>() {
            override fun onSuccess(data: BaseResponse<LoginBean>?) {
                if (getView() != null) {
                    getView()?.hideLoading()
                    getView()?.onLoginSuccess(data?.data)
                    WearData.getInstance().saveLoginPhoneCode(phoneNumber)
                    WearData.getInstance().saveLoginPhonePassWord(password)
                    LogUtils.e("ceshi ======手机号码登录成功啦=========：", data?.data?.token)
                }
            }

            override fun onError(e: NetException?) {
//                LogUtils.e("ceshi  phone接口返回的错误===========：", e?.code, e?.message)
                if (getView() != null) {
                    getView()?.hideLoading()
                    getView()?.onLoginFail(e)
                }

                when (e?.code) {
                    NetException.VERIFICATION_CODE_ERROR -> {
                        ToastUtils.showShort(R.string.verify_code_error)
                    }

                    NetException.LOGIN_WRONG_PASSWORD -> {
                        ToastUtils.showShort(R.string.login_password_error)
                    }
                }
            }
        })
    }


    //google register and login 2
    fun goRegisterLoginGoogle2(user_id:String,email: String){
        val hashMap: MutableMap<String, String> = HashMap()
        hashMap["user_id"] = user_id
        hashMap["email"] = email
        LoginModel.goLoginGoogle2(hashMap,  object: LoginCallback {
            override fun onSuccess(responseBody: String?) {
                val gson = Gson()
                android.util.Log.e("ceshi","0success=${responseBody}")
                val response = gson.fromJson(responseBody, ResponseData::class.java)
//                val token = response.data.token.replace("Basic|\\s".toRegex(), "")
//                android.util.Log.e("ceshi","success=${token}")
//                WearData.getInstance().saveToken(token)
                if (getView() != null) {
                    getView()?.hideLoading()
                    getView()?.onLoginSuccess(response.data)
                }
            }

            override fun onFailure(errorCode: Int, errorMessage: String?) {
                if (getView() != null) {
                    getView()?.hideLoading()
                }

                if (getView() != null) {
                    //getView()?.onLoginFail()
                    getView()?.hideLoading()
                }
                when (errorCode.toString()) {
                    NetException.VERIFICATION_CODE_ERROR -> {
                        ToastUtils.showShort(R.string.verify_code_error)
                    }

                    NetException.LOGIN_WRONG_PASSWORD -> {
                        ToastUtils.showShort(R.string.login_password_error)
                    }

                    NetException.ALREADY_REGISTERED -> {
                        ToastUtils.showShort(R.string.register_already_email_error)
                    }
                }
            }

        })
    }

}