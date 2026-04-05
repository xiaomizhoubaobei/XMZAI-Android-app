/**
 * @fileoverview IClickListener 自定义视图
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 自定义 UI 组件
 */

package xmzai.mizhoubaobei.top.widget.listener;

import android.view.View;

/**
 * author : lzh
 * e-mail : luozhanhang@adswave.com
 * time   : 2025/05/17
 * desc   : 防止重复点击ClickListener
 * version: 1.0
 */
public abstract class IClickListener implements View.OnClickListener {
    private long mLastClickTime = 0;
    private static final int TIME_INTERVAL = 500;

    @Override
    public final void onClick(View v) {
        if (System.currentTimeMillis() - mLastClickTime >= getTimeInterval()) {
            onIClick(v);
            mLastClickTime = System.currentTimeMillis();
        } else {
            onAgain(v);
        }
    }

    //回调
    protected abstract void onIClick(View v);

    //可重复时处理
    protected void onAgain(View v) {

    }

    public int getTimeInterval() {
        return TIME_INTERVAL;
    }
}
