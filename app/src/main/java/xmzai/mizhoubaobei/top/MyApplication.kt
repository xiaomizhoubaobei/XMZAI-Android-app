/**
 * @fileoverview MyApplication 模块
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 项目核心功能模块
 */

package xmzai.mizhoubaobei.top

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.databinding.library.BuildConfig
import xmzai.mizhoubaobei.top.network.httpconfig.HostConfigProvide
import xmzai.mizhoubaobei.top.utils.LanguageUtil
import xmzai.mizhoubaobei.top.utils.Utils
import xmzai.mizhoubaobei.top.utils.base.WearData
import xmzai.mizhoubaobei.top.utils.base.WearUtil
import com.tencent.mmkv.MMKV

//import leakcanary.LeakCanary

/**
 * author :
 * e-mail :
 * time   : 2025/4/16
 * desc   :
 * version: 1.0
 */
class MyApplication:Application() {
    // 活跃 Activity 的数量
    private var activeActivityCount = 0

    override fun attachBaseContext(base: Context) {
        // 初始化时应用保存的语言设置
        val language = LanguageUtil.getSavedLanguage(base)
        super.attachBaseContext(LanguageUtil.applyLanguage(base, language))
    }

    override fun onTerminate() {
        super.onTerminate()
    }

    companion object {
        lateinit var myApplicationContext: Context
        lateinit var sharedPreferences: SharedPreferences
        var isFirstLaunch: Boolean = true
    }


    override fun onCreate() {
        super.onCreate()
        myApplicationContext = this
        // LeakCanary 会自动初始化并开始监控
        // 初始化 LeakCanary（无需检查分析进程）
        MMKV.initialize(this)
        Utils.init(this)
        Log.e("ceshi","活跃的Activity$isFirstLaunch")
        WearUtil.init(this)
        WearUtil.initEvn(HostConfigProvide())
        WearData.getInstance()
        if (BuildConfig.DEBUG) {
//            LeakCanary.config = LeakCanary.config.copy(
//                dumpHeap = true // 确保在调试版本中启用堆转储
//            )
        }
        // 初始化SharedPreferences
        sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)

        // 读取首次启动标记
        /*isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true)

        // 如果是首次启动，在读取后就更新标记（防止多次判断）
        if (isFirstLaunch) {
            sharedPreferences.edit().putBoolean("isFirstLaunch", false).apply()
        }*/
        isFirstLaunch = true
        // 注册 Activity 生命周期回调
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                activeActivityCount++
            }

            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

            override fun onActivityDestroyed(activity: Activity) {
                activeActivityCount--
                // 当所有 Activity 都被销毁时，执行“应用即将被杀死”的逻辑
                if (activeActivityCount == 0) {
                    //handleAppKilled()
                    Log.e("ceshi","没有活跃的Activity$isFirstLaunch")
                    isFirstLaunch = true
                }
            }
        })


    }



}