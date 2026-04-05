/**
 * @fileoverview LoginCallback 网络模块
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 网络请求相关组件
 */

package xmzai.mizhoubaobei.top.network.common_bean.callback;

public interface LoginCallback {
    // 请求成功时的回调方法，参数为响应体内容（这里假设为字符串形式，可根据实际情况调整）
    void onSuccess(String responseBody);
    // 请求失败时的回调方法，参数为错误码和对应的错误信息
    void onFailure(int errorCode, String errorMessage);
}
