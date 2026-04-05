/**
 * @fileoverview NetworkProgressListener 网络模块
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 网络请求相关组件
 */

package xmzai.mizhoubaobei.top.network.callback;

/**
 * author : lzh
 * e-mail : luozhanhang@adswave.com
 * time   : 2024/7/1
 * desc   : 网络请求 - 进度监听
 * version: 1.0
 */
public interface NetworkProgressListener {
    void onProgress(long uploadSize, long fileSize);
}
