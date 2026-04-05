/**
 * @fileoverview BaseIPresenter 基础组件
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
interface BaseIPresenter {

    fun initData(savedInstanceState: Bundle?) // 初始化数据: Presenter的initData后，会调用Activity的initData方法

    fun onCreate()

    fun onCreateView()

    fun onResume()

    fun onPause()

    fun onDestroyView()

    fun onDestroy()

    fun clearResources() //清除释放资源
}