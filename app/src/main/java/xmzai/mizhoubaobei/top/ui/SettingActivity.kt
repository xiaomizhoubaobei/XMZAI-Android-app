/**
 * @fileoverview SettingActivity 界面
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark Activity 或界面页面，处理用户交互和界面逻辑
 */

package xmzai.mizhoubaobei.top.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.webkit.SslErrorHandler
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresExtension
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.lzyzsd.jsbridge.BridgeHandler
import com.github.lzyzsd.jsbridge.BridgeWebView
import com.github.lzyzsd.jsbridge.CallBackFunction
import com.github.lzyzsd.jsbridge.DefaultHandler
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import xmzai.mizhoubaobei.top.MainActivity
import xmzai.mizhoubaobei.top.MyApplication
import xmzai.mizhoubaobei.top.R
import xmzai.mizhoubaobei.top.adapter.EmojiAdapter
import xmzai.mizhoubaobei.top.base.BaseActivity
import xmzai.mizhoubaobei.top.bean.HtmlProxyJsonBean
import xmzai.mizhoubaobei.top.bean.HtmlQuickAccessBean
import xmzai.mizhoubaobei.top.constant.AppConstant
import xmzai.mizhoubaobei.top.data.MainMessage
import xmzai.mizhoubaobei.top.databinding.ActivityMainBinding
import xmzai.mizhoubaobei.top.databinding.ActivitySettingBinding
import xmzai.mizhoubaobei.top.datastore.DataStoreManager
import xmzai.mizhoubaobei.top.http.ApiService
import xmzai.mizhoubaobei.top.http.NetworkFactory
import xmzai.mizhoubaobei.top.room.ChatDatabase
import xmzai.mizhoubaobei.top.room.UserConfigurationRoom
import xmzai.mizhoubaobei.top.ui.model.MainModel
import xmzai.mizhoubaobei.top.utils.ActivityUtils
import xmzai.mizhoubaobei.top.utils.DialogUtils
import xmzai.mizhoubaobei.top.utils.LanguageUtil
import xmzai.mizhoubaobei.top.utils.LanguageUtil.saveLanguageSetting
import xmzai.mizhoubaobei.top.utils.LogUtils
import xmzai.mizhoubaobei.top.utils.SystemUtils
import xmzai.mizhoubaobei.top.utils.ThemeUtil
import xmzai.mizhoubaobei.top.utils.ToastUtils
import xmzai.mizhoubaobei.top.utils.ViewAnimationUtils
import xmzai.mizhoubaobei.top.utils.base.WearData
import xmzai.mizhoubaobei.top.utils.base.WearUtil
import xmzai.mizhoubaobei.top.viewModel.ChatViewModel
import xmzai.mizhoubaobei.top.widget.utils.CommonEnum
import xmzai.mizhoubaobei.top.widget.utils.CommonHtmlUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.Locale

class SettingActivity : BaseActivity() {
    private lateinit var binding: ActivitySettingBinding
    private lateinit var dataStoreManager: DataStoreManager
    private var mQuickAccessBean: HtmlQuickAccessBean? = null
    private var mWebView: BridgeWebView? = null
    @Volatile
    private var personalCenter = false
    private lateinit var chatDatabase: ChatDatabase
    private lateinit var modelListNull:MutableList<String>

    private lateinit var dialogUtils: DialogUtils

    private var defaultSystemLanguage = "zh"
    private var defaultSystemTheme = "light"
    private var defaultEmoji = "\uD83D\uDE00"
    private var mUserId = ""
    private var isNewChat = false

    private val chatViewModel: ChatViewModel by viewModels()
    private val BASE_URL = "https://api.302.ai/"
    private var CUSTOMIZE_URL_TWO = "https://api.siliconflow.cn/"
    private var apiService = NetworkFactory.createApiService(ApiService::class.java,BASE_URL)
    var mLanguage = ""
    private var readImageUrl = ""

    @Suppress("DEPRECATION")

    // 初始化表情列表
    val emojis = listOf(
        "😀", "😃", "😄", "😁", "😆", "😅", "😂", "🤣", "😊", "😇",
        "🙂", "🙃", "😉", "😌", "😍", "🥰", "😘", "😗", "😙", "😚",
        "😋", "😛", "😝", "😜", "🤪", "🤨", "🧐", "🤓", "😎", "🤩",
        "🥳", "😏", "😒", "😞", "😔", "😟", "😕", "🙁", "☹️", "😣",
        "😖", "😫", "😩", "🥺", "😢", "😭", "😤", "😠", "😡", "🤯",
        "😳", "🥵", "🥶", "😱", "😨", "😰", "😥", "😓", "🤗", "🤔",
        "🤭", "🤫", "🤥", "😶", "😐", "😑", "😒", "🙄", "😳", "🤤",
        "😪", "😴", "🤢", "🤮", "🤧", "😷", "🤒", "🤕", "🤑", "🤠",
        "😈", "👿", "👹", "👺", "💀", "☠️", "👻", "👽", "👾", "🤖",
        "💩", "👻", "🎃", "😺", "😸", "😹", "😻", "😼", "😽", "🙀",
        "😿", "😾", "👐", "👏", "🤝", "👍", "👎", "✊", "👊", "🤛", "🤜",
        "🤞", "✌️", "🤟", "👌", "👈", "👉", "👆", "👇", "☝️", "✋",
        "🤚", "🖐️", "🖖", "👋", "🤙", "💪", "🦾", "👂", "🦻", "👃",
        "👁️", "👀", "👅", "👄", "👶", "🧒", "👦", "👧", "👨", "👩",
        "🧑", "👴", "👵", "👱", "👮", "🕵️", "👩‍⚕️", "👨‍⚕️", "👩‍🌾", "👨‍🌾",
        "👩‍🍳", "👨‍🍳", "👩‍🎓", "👨‍🎓", "👩‍🎤", "👨‍🎤", "👩‍💻", "👨‍💻", "👩‍🏫", "👨‍🏫",
        "👩‍⚖️", "👨‍⚖️", "👩‍🚒", "👨‍🚒", "👩‍✈️", "👨‍✈️", "👩‍🚀", "👨‍🚀", "👩‍⚙️", "👨‍⚙️",
        "👩‍🔧", "👨‍🔧", "👩‍🎨", "👨‍🎨", "👩‍🏭", "👨‍🏭", "👩‍💼", "👨‍💼", "👩‍🔬", "👨‍🔬",
        "👩‍💻", "👨‍💻", "👩‍🎓", "👨‍🎓", "👩‍🏫", "👨‍🏫", "👩‍⚕️", "👨‍⚕️", "👩‍🔧", "👨‍🔧",
        "👩‍🎤", "👨‍🎤", "👩‍🚒", "👨‍🚒", "👩‍✈️", "👨‍✈️", "👩‍🚀", "👨‍🚀", "👩‍⚖️", "👨‍⚖️"
    )
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Ai302)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_setting)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        chatDatabase = ChatDatabase.getInstance(this)
        dataStoreManager = DataStoreManager(MyApplication.myApplicationContext)
        val newChat = intent.getSerializableExtra("chat_new", Boolean::class.java)
        if (newChat != null){
           isNewChat = newChat
        }
        chatViewModel.userInfoResult.observe(this){
            it.let {
                binding.userBalanceTv.text = it.balance.toString()
                lifecycleScope.launch(Dispatchers.Main) {
                    // 方法1：使用内置的CircleCrop变换
                    Glide.with(this@SettingActivity)
                        .load(it.avatar)
                        .apply(RequestOptions.circleCropTransform())
                        .placeholder(android.R.drawable.ic_menu_gallery)
                        .error(android.R.drawable.stat_notify_error)
                        .into(binding.imageProfile)
                }
                lifecycleScope.launch(Dispatchers.IO) {
                    dataStoreManager.saveUserBalance(it.balance)
                    dataStoreManager.saveImageUrl(it.avatar)
                }
            }
        }
        initView()
    }

    override fun onStop() {
        super.onStop()
        lifecycleScope.launch(Dispatchers.IO) {
            val userId = dataStoreManager.readUserEmailData.first()?:""
            var userConfigurationRoom = chatDatabase.chatDao().getUserConfigByUserId(userId)
            userConfigurationRoom?.appEmojisData = defaultEmoji
            userConfigurationRoom?.systemLanguage = defaultSystemLanguage
            userConfigurationRoom?.systemTheme = defaultSystemTheme

//            userConfigurationRoom?.let {
//                chatDatabase.chatDao().insertUserConfig(
//                    UserConfigurationRoom(0,userConfigurationRoom.userId,userConfigurationRoom.systemLanguage,"light",readUseTracelessSwitch,
//                        readSlideBottomSwitch,readAppEmojisData,readSearchServiceType,readModelType,readBuildTitleModelType,modelList,readBuildTitleTime)
//                )
//            }




        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    private fun initView(){
        dialogUtils = DialogUtils {
            Log.e("ceshi","弹窗返回$it")
            when(it){
                /*ContextCompat.getString(this@SettingActivity, R.string.language_ch_message) -> {
                    binding.systemLanguageTV.text = ContextCompat.getString(this@SettingActivity, R.string.language_ch_message)
//                    lifecycleScope.launch(Dispatchers.IO) {
//                        dataStoreManager.saveBuildTitleTimeData("第一次对话")
//                    }
                    switchLanguage(LanguageUtil.LANGUAGE_ZH)
                    applyLanguage()
                    //onLanguageChange()
                    defaultSystemLanguage = "zh"
                }
                ContextCompat.getString(this@SettingActivity, R.string.language_en_message) -> {
                    binding.systemLanguageTV.text = ContextCompat.getString(this@SettingActivity, R.string.language_en_message)
                    switchLanguage(LanguageUtil.LANGUAGE_EN)
                    applyLanguage()
                    //onLanguageChange()
                    defaultSystemLanguage = "en"
                }

                ContextCompat.getString(this@SettingActivity, R.string.language_ja_message) -> {
                    binding.systemLanguageTV.text = ContextCompat.getString(this@SettingActivity, R.string.language_ja_message)
                    switchLanguage(LanguageUtil.LANGUAGE_JA)
                    applyLanguage()
                    //onLanguageChange()
                    defaultSystemLanguage = "ja"
                }*/
                "中文" -> {
                    binding.systemLanguageTV.text = "中文"
//                    lifecycleScope.launch(Dispatchers.IO) {
//                        dataStoreManager.saveBuildTitleTimeData("第一次对话")
//                    }
                    switchLanguage(LanguageUtil.LANGUAGE_ZH)
                    applyLanguage()
                    //onLanguageChange()
                    defaultSystemLanguage = "zh"
                }
                "English" -> {
                    binding.systemLanguageTV.text = "English"
                    switchLanguage(LanguageUtil.LANGUAGE_EN)
                    applyLanguage()
                    //onLanguageChange()
                    defaultSystemLanguage = "en"
                }

                "日本語" -> {
                    binding.systemLanguageTV.text = "日本語"
                    switchLanguage(LanguageUtil.LANGUAGE_JA)
                    applyLanguage()
                    //onLanguageChange()
                    defaultSystemLanguage = "ja"
                }

                "light" -> {
                    binding.systemThemeTv.text = getString(R.string.setting_light_message)
                    ThemeUtil.saveThemeSetting(this,ThemeUtil.THEME_LIGHT)
                    ThemeUtil.changeTheme(this,ThemeUtil.THEME_LIGHT)
                    // 2. 重启当前Activity，使新上下文生效
                    val intent = intent // 获取当前Activity的启动意图
                    finish() // 销毁当前Activity
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)) // 重建Activity
                    @Suppress("DEPRECATION")
                    overridePendingTransition(0, 0) // 可选：去除切换动画
                }

                "night" -> {
                    binding.systemThemeTv.text = getString(R.string.setting_night_message)
                    ThemeUtil.saveThemeSetting(this,ThemeUtil.THEME_NIGHT)
                    ThemeUtil.changeTheme(this,ThemeUtil.THEME_NIGHT)
                    // 2. 重启当前Activity，使新上下文生效
                    val intent = intent // 获取当前Activity的启动意图
                    finish() // 销毁当前Activity
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)) // 重建Activity
                    @Suppress("DEPRECATION")
                    overridePendingTransition(0, 0) // 可选：去除切换动画
                }

                "follow_system" -> {
                    binding.systemThemeTv.text = getString(R.string.setting_system_message)
                    ThemeUtil.saveThemeSetting(this,ThemeUtil.THEME_FOLLOW_SYSTEM)
                    ThemeUtil.changeTheme(this,ThemeUtil.THEME_FOLLOW_SYSTEM)
                    // 2. 重启当前Activity，使新上下文生效
                    val intent = intent // 获取当前Activity的启动意图
                    finish() // 销毁当前Activity
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)) // 重建Activity
                    @Suppress("DEPRECATION")
                    overridePendingTransition(0, 0) // 可选：去除切换动画
                }


            }
        }

        binding.historyImage.setOnClickListener {
            ViewAnimationUtils.performClickEffect(it)
            lifecycleScope.launch(Dispatchers.IO) {
                //val chatItemHistory = chatDatabase.chatDao().getLastChatItem()
                //val chatItemHistory = chatDatabase.chatDao().getChatsByUserId(mUserId).last()
                // 1. 先获取列表（不要直接链式调用 last()）
                val chatList = chatDatabase.chatDao().getChatsByUserId(mUserId)

                // 2. 检查列表是否为空
                val chatItemHistory = if (chatList.isNotEmpty()) {
                    // 列表非空，安全获取最后一个元素
                    chatList.last()
                } else {
                    // 列表为空，根据业务需求处理（如返回 null 或默认值）
                    null
                }
                val intent = Intent(this@SettingActivity, MainActivity::class.java)
                if (chatItemHistory != null){
                    if (!isNewChat){
                        intent.putExtra("setting_chat_item", chatItemHistory)
                    }
                }
                val botMsg = getString(R.string.front_page_bottom_message)
                val welMsg = getString(R.string.front_page_message)
                val sendMsg = getString(R.string.chat_edit_message)

                intent.putExtra("msg_setting",MainMessage(welMsg,sendMsg,botMsg,readImageUrl))
                Log.e("ceshi","发送前：${welMsg}")
                startActivity(intent)
                finish()
            }

        }
        binding.toAnnouncementConst.setOnClickListener {
            val intent = Intent(this, AnnouncementActivity::class.java)
            startActivity(intent)
        }
        binding.toServiceConst.setOnClickListener {
            // 处理点击事件
            //android.widget.Toast.makeText(context, "你点击了官网地址", android.widget.Toast.LENGTH_SHORT).show()
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://302.ai/") // 设置要跳转的网址
            this.startActivity(intent) // 启动活动
        }
        binding.toModelManagerConst.setOnClickListener {
            val intent = Intent(this, ModelManagerActivity::class.java)
            startActivity(intent)
        }
        val systemLanguage = SystemUtils.getSystemLanguage(this)
        val systemThem = SystemUtils.getSystemTheme(this)
        Log.e("ceshi","系统$systemLanguage,$systemThem")
//        if (systemLanguage == "中文 (中国)"){
//            binding.systemLanguageTV.text = "简体中文"
//        }else if (systemLanguage == "English (United States)"){
//            binding.systemLanguageTV.text = "English"
//        }
        val language = LanguageUtil.getSavedLanguage(this)
        Log.e("ceshi","获取的语言是:$language")
        mLanguage = language
        defaultSystemLanguage = language
        when (language) {
            LanguageUtil.LANGUAGE_ZH -> binding.systemLanguageTV.text = "简体中文"
            LanguageUtil.LANGUAGE_JA -> binding.systemLanguageTV.text = "日本語"
            else -> binding.systemLanguageTV.text = "English"
        }
        /*if (systemThem == "浅色模式"){
            binding.systemThemeTv.text = "light"
        }else if (systemThem == "深色模式"){
            binding.systemThemeTv.text = "night"
        }*/
        val customizeTheme = ThemeUtil.getSavedTheme(this)
        Log.e("ceshi","获取的主题是:$customizeTheme")
        if (customizeTheme == ThemeUtil.THEME_LIGHT){
            binding.systemThemeTv.text = getString(R.string.setting_light_message)
        }else if (customizeTheme == ThemeUtil.THEME_NIGHT){
            binding.systemThemeTv.text = getString(R.string.setting_night_message)
        }else{
            binding.systemThemeTv.text = getString(R.string.setting_system_message)
        }


        binding.systemLanguageCons.setOnClickListener {
            //SystemUtils.openLanguageSettings(this)
            /*val options = mutableListOf(ContextCompat.getString(this@SettingActivity, R.string.language_ch_message),
                ContextCompat.getString(this@SettingActivity, R.string.language_en_message),
                ContextCompat.getString(this@SettingActivity, R.string.language_ja_message))*/
            val options = mutableListOf("中文","English","日本語")
            dialogUtils.setupPopupWindow(options,"languageTypeList",this)
            dialogUtils.showPopup(binding.systemLanguageLine)
        }
        binding.systemThemeCons.setOnClickListener {
            //SystemUtils.openThemeSettings(this)
            val options = mutableListOf(getString(R.string.setting_light_message),
                getString(R.string.setting_night_message),
                getString(R.string.setting_system_message))
            dialogUtils.setupPopupWindow(options,"systemThemeList",this)
            dialogUtils.showPopup(binding.systemThemeLine)
        }

        binding.toPreferencesCons.setOnClickListener {
            val intent = Intent(this, PreferencesActivity::class.java)
            startActivity(intent)
        }

        /*binding.imageProfile.setOnClickListener {
            showEmojiPickerDialog()
        }*/

        binding.logoutBtn.setOnClickListener {
            insertUserConfiguration()
            lifecycleScope.launch(Dispatchers.IO) {
                dataStoreManager.saveData("")
                WearData.getInstance().saveToken("")
                WearData.getInstance().saveGetModelList(false)
            }
            val botMsg = getString(R.string.front_page_bottom_message)
            val welMsg = getString(R.string.front_page_message)
            val sendMsg = getString(R.string.chat_edit_message)
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("come_from","setting")
            intent.putExtra("msg_setting",MainMessage(welMsg,sendMsg,botMsg,readImageUrl))
            startActivity(intent)
            finish()
        }

        binding.versionConst.setOnClickListener {
            val intent = Intent(this, VersionUpdateActivity::class.java)
            startActivity(intent)
        }
        binding.protocolConst.setOnClickListener {
            val intent = Intent(this, ProtocolActivity::class.java)
            startActivity(intent)
        }

        binding.cons0.setOnClickListener {
            val intent = Intent(this, PersonalCenterActivity::class.java)
            startActivity(intent)
        }

        binding.clearChatConst.setOnClickListener {
            ViewAnimationUtils.performClickEffect(it)
            lifecycleScope.launch (Dispatchers.IO){
                chatDatabase.chatDao().deleteAllChatsByUserId(mUserId)
            }
        }



    }

    override fun onResume() {
        super.onResume()
        /*val systemThem = SystemUtils.getSystemTheme(this)
        Log.e("ceshi","系统主题,$systemThem")
        if (systemThem == "浅色模式"){
            binding.systemThemeTv.text = "light"
        }else if (systemThem == "深色模式"){
            binding.systemThemeTv.text = "night"
        }*/

        lifecycleScope.launch((Dispatchers.IO)) {

            /*val readAppEmojisData = dataStoreManager.readAppEmojisData.first()
            readAppEmojisData?.let {
                lifecycleScope.launch(Dispatchers.Main) {
                    Log.e("setting","readAppEmojisData是多少：$it")
                    binding.imageProfile.text = it
                }
            }*/
            val data = dataStoreManager.readImageUrl.first()
            data?.let {
                Log.e("ceshi", "imageurl是个多少：$it")
                readImageUrl = it
                lifecycleScope.launch(Dispatchers.Main) {
                    // 方法1：使用内置的CircleCrop变换
                    Glide.with(this@SettingActivity)
                        .load(it)
                        .apply(RequestOptions.circleCropTransform())
                        .placeholder(android.R.drawable.ic_menu_gallery)
                        .error(android.R.drawable.stat_notify_error)
                        .into(binding.imageProfile)
                }

            }

            val readUserNameData = dataStoreManager.readUserNameData.first()
            readUserNameData?.let {
                lifecycleScope.launch(Dispatchers.Main) {
                    Log.e("setting","readUserNameData：$it")
                    binding.userSettingTv.text = it
                }
            }

            val readUserBalanceData = dataStoreManager.readUserBalanceData.first()
            readUserBalanceData?.let {
                lifecycleScope.launch(Dispatchers.Main) {
                    Log.e("setting","readUserNameData：$it")
                    binding.userBalanceTv.text = it.toString()
                }
            }

            val readUserEmailData = dataStoreManager.readUserEmailData.first()
            readUserEmailData?.let {
                lifecycleScope.launch(Dispatchers.Main) {
                    Log.e("setting","readUserEmailData：$it")
                    binding.userIdSettingTv.text = it
                    mUserId = readUserEmailData
                }
            }



        }
    }


    private fun showEmojiPickerDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_emoji_picker)
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setGravity(Gravity.BOTTOM)
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation

        var recyclerView = dialog.findViewById<RecyclerView>(R.id.emojiRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 4)
        recyclerView.adapter = EmojiAdapter(emojis) { selectedEmoji ->
            // 更新TextView显示选中的表情
            //binding.imageProfile.text = selectedEmoji
            defaultEmoji = selectedEmoji
            lifecycleScope.launch(Dispatchers.IO) {
                dataStoreManager.saveAppEmojisData(selectedEmoji)
            }

            // 添加选中动画效果
            binding.imageProfile.scaleX = 0.8f
            binding.imageProfile.scaleY = 0.8f
            binding.imageProfile.animate()
                .scaleX(1.2f)
                .scaleY(1.2f)
                .setDuration(150)
                .withEndAction {
                    binding.imageProfile.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(150)
                        .start()
                }
                .start()

            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.cancelButton).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
    }



    /**
     * 加载webView链接
     * @param type       加载类型
     * @param htmlUrl    加载路径
     */
    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    private fun loadWebHandler(type: Int, htmlUrl: String,mWebView: BridgeWebView) {
        mWebView?.settings?.javaScriptEnabled = true
//        mWebView?.settings?.databaseEnabled = true //数据库缓存
        mWebView?.settings?.setGeolocationEnabled(true) // 允许网页定位
        mWebView?.settings?.loadsImagesAutomatically = true // 加快网页加载完成的速度，等页面完成再加载图片
        mWebView?.settings?.domStorageEnabled = true       // 开启 localStorage
        mWebView?.setDefaultHandler(DefaultHandler())
        mWebView?.webChromeClient = WebChromeClient()

        mWebView?.webChromeClient = object : WebChromeClient() {
            // For Android >= 5.0
            override fun onShowFileChooser(
                webView: WebView,
                filePathCallback: ValueCallback<Array<Uri?>?>,
                fileChooserParams: FileChooserParams
            ): Boolean {

//                try {
//                    val intent = fileChooserParams?.createIntent()?:return super.onShowFileChooser(webView,filePathCallback,fileChooserParams)
//                    uploadMessageAboveL = filePathCallback
//                    launcher.launch(intent)
//                } catch (e: ActivityNotFoundException) {
//                    uploadMessageAboveL = null
//                    return false
//                }
                return true
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                Log.e("ceshi","onReceivedTitle$title${view?.url}")
            }

        }

        mWebView?.apply {
            settings.javaScriptEnabled = true // 确保 JS 启用（部分网站依赖 JS 加载）
            settings.domStorageEnabled = true
            settings.useWideViewPort = true // 适配屏幕，避免加载后样式问题间接导致超时
            settings.loadWithOverviewMode = true

            // 优先使用缓存，无缓存时再请求网络
            settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            // 启用 HTML5 缓存（如 localStorage、IndexedDB）
            settings.domStorageEnabled = true
            @Suppress("DEPRECATION")
            settings.databaseEnabled = true
            // 1. 支持 TLS 1.0~1.3（适配低版本 Android）
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW // 允许混合内容（HTTPS 加载 HTTP 资源）
                webViewClient = object : WebViewClient() {
                    // 解决 TLS 协议支持问题
                    override fun onReceivedSslError(
                        view: WebView?,
                        handler: SslErrorHandler?,
                        error: SslError?
                    ) {
                        // 注意：仅调试时使用！正式环境需验证证书，避免安全风险
                        handler?.proceed() // 忽略 SSL 证书错误（临时排查用）
                    }

                    // 捕获加载错误，打印日志定位问题
                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?
                    ) {
                        super.onReceivedError(view, request, error)
                        // 打印错误信息（在 Logcat 中搜索 "WebViewError"）
                        Log.e("WebViewError", "错误码：${error?.errorCode}，描述：${error?.description}")
                        when (error?.errorCode) {
                            -8 -> { // ERR_CONNECTION_TIMED_OUT
                                Toast.makeText(context, "连接超时，请检查网络或稍后重试", Toast.LENGTH_SHORT).show()
                            }
                            -2 -> { // 无网络
                                Toast.makeText(context, "网络不可用", Toast.LENGTH_SHORT).show()
                            }
                            else -> {
                                Toast.makeText(context, "加载失败：${error?.description}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }




        var url = ""
        when (type) {
            CommonEnum.LoadHtmlType.NEED_SPLICED_PARA -> {
                url =
                    WearUtil.APP_SERVER_HTTPS_HTML + htmlUrl + WearData.getInstance().token + "/?device=" + AppConstant.device
            }

            CommonEnum.LoadHtmlType.NOT_NEED_SPLICED_PARA -> {
                url = WearUtil.APP_SERVER_HTTPS_HTML + htmlUrl
            }

            CommonEnum.LoadHtmlType.DIRECT_LINK -> {
                url = htmlUrl
            }
        }

        LogUtils.e("ceshi  url是什么=================：", url)
        mWebView?.loadUrl(url)
        if(url == "https://dash.proxy302.com/webapp/user-center"){
            lifecycleScope.launch {
                delay(2000)
                personalCenter = true
            }
            lifecycleScope.launch {
                delay(2000)
                Log.e("ceshi","personalCenter>>>$personalCenter")
            }
        }
        //mWebView?.callHandler(CommonHtmlUtil.handlerName, url, null)
        //mWebView?.callHandler(CommonHtmlUtil.handlerNamePara, url, null)
        mWebView?.registerHandler(CommonHtmlUtil.handlerNamePara, object : BridgeHandler {
            override fun handler(data: String?, function: CallBackFunction?) {
                Log.e("测试","ffffffffffff")
                LogUtils.e("ceshi 是否有数据过来============:", data.toString())

                showHtmlAnalysisData(data)
            }
        })


    }

    /**
     * 数据处理
     */
    fun showHtmlAnalysisData(jsonStr: String?) {
        if (jsonStr != null) {
            val jsonObj = JSONObject(jsonStr)

            val type = jsonObj.optString(CommonHtmlUtil.htmlType)
            val data = jsonObj.optString(CommonHtmlUtil.htmlData)
            LogUtils.e("ceshi 解析的数据是什么==============：", type)

            when (type) {
                CommonHtmlUtil.payBillHtml -> {
                    //HtmlManager.jumpHtml(this@HomeActivity, data)
                    loadWebHandler(CommonEnum.LoadHtmlType.DIRECT_LINK,data,mWebView!!)
                }
            }
        }
    }


    /*private fun applyLanguage() {
        val language = LanguageUtil.getSavedLanguage(this)
        Log.e("ceshi","获取的语言是:$language")
        val context = LanguageUtil.applyLanguage(this, language)
        val resources = context.resources
        val configuration = resources.configuration
        val displayMetrics = resources.displayMetrics
        // 更新配置
        resources.updateConfiguration(configuration, displayMetrics)

    }*/
    private fun applyLanguage() {
        val language = LanguageUtil.getSavedLanguage(this)
        Log.e("ceshi","获取的语言是:$language")

        // 1. 获取新的上下文（已应用语言设置）
        val newContext = LanguageUtil.applyLanguage(this, language)

        // 2. 重启当前Activity，使新上下文生效
        val intent = intent // 获取当前Activity的启动意图
        finish() // 销毁当前Activity
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)) // 重建Activity
        @Suppress("DEPRECATION")
        overridePendingTransition(0, 0) // 可选：去除切换动画
    }

    // 语言切换按钮点击事件
    fun onLanguageChange() {
        // 例如切换到中文
        LanguageUtil.changeLanguage(this, LanguageUtil.LANGUAGE_ZH)
    }

    private fun insertUserConfiguration(){
        //MyApplication.isFirstLaunch = MyApplication.sharedPreferences.getBoolean("isFirstLaunch", true)
        lifecycleScope.launch(Dispatchers.IO) {
            //val readAppEmojisData = dataStoreManager.readAppEmojisData.first()?:"\uD83D\uDE00"
            val readImageUrl = dataStoreManager.readImageUrl.first()?:""
            val readBuildTitleModelType = dataStoreManager.readBuildTitleModelType.first()?:"gpt-4o"
            val readModelType = dataStoreManager.readModelType.first()?:"gemini-2.5-flash-nothink"
            val readUserEmailData = dataStoreManager.readUserEmailData.first()?:""
            val systemLanguage = LanguageUtil.getSavedLanguage(this@SettingActivity)
            val systemTheme = SystemUtils.getSystemTheme(this@SettingActivity)
            val readSlideBottomSwitch = dataStoreManager.readSlideBottomSwitch.first()?:false
            val readUseTracelessSwitch = dataStoreManager.readUseTracelessSwitch.first()?:false
            var modelList = dataStoreManager.modelListFlow.first()
            val readSearchServiceType = dataStoreManager.readSearchServiceType.first()?:"search1api"
            val readBuildTitleTime = dataStoreManager.readBuildTitleTime.first()?:"第一次对话"

            chatDatabase.chatDao().insertUserConfig(UserConfigurationRoom(0,readUserEmailData,systemLanguage,"light",readUseTracelessSwitch,
                readSlideBottomSwitch,readImageUrl,readSearchServiceType,readModelType,readBuildTitleModelType,modelList,readBuildTitleTime))
            //dataStoreManager.saveAppEmojisData("\uD83D\uDE00")
            dataStoreManager.saveImageUrl("")
            dataStoreManager.saveBuildTitleModeTypeData("gpt-4o")
            dataStoreManager.saveModelType("gemini-2.5-flash-nothink")
            //dataStoreManager.saveModelList(modelListNull)
            dataStoreManager.saveSearchServiceTypeData("search1api")
            dataStoreManager.saveSlideBottomSwitch(false)
            dataStoreManager.saveUseTracelessSwitch(false)
            dataStoreManager.saveBuildTitleTimeData("第一次对话")
            dataStoreManager.saveUserEmail("")
        }



    }
}
