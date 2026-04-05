/**
 * @fileoverview BasePresenter 基础组件
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 基础框架组件，提供公共功能
 */

package xmzai.mizhoubaobei.top.base.mvp

import android.os.Bundle
import xmzai.mizhoubaobei.top.utils.base.WearUtil
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

/**
 * author : lzh
 * e-mail : luozhanhang@adswave.com
 * time   : 2024/7/1
 * desc   :
 * version: 1.0
 */
abstract class BasePresenter<IView : BaseIView?> : BaseIPresenter {

    protected var mViewRef: WeakReference<IView>? = null

    // 用来取消订阅，子类赋值
    protected var mSubscription: Disposable? = null

    fun attachView(view: IView) {
        this.mViewRef = WeakReference(view)
    }

    fun getView(): IView? {
        return if (this.mViewRef == null) null else mViewRef?.get()
    }

    fun isViewAttached(): Boolean {
        return this.mViewRef != null && mViewRef?.get() != null
    }

    override fun onCreate() {
        WearUtil.register(this)
    }

    fun onNewIntent() {
    }

    override fun onCreateView() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onDestroyView() {
        WearUtil.unregister(this)
    }

    override fun onDestroy() {
        WearUtil.unregister(this)
        clearResources()
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun clearResources() {
    }

    fun detachView() {
        if (this.mViewRef != null) {
            mViewRef!!.clear()
            this.mViewRef = null
        }
        if (mSubscription != null && !mSubscription!!.isDisposed) {
            mSubscription!!.dispose()
            mSubscription = null
        }
    }
}