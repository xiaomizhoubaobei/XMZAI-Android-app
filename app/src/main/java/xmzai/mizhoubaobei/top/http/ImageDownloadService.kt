/**
 * @fileoverview ImageDownloadService 网络模块
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 网络请求相关组件
 */

package xmzai.mizhoubaobei.top.http

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

// 图片下载接口
interface ImageDownloadService {
    // @Url：动态传入完整图片URL
    @GET
    fun downloadImage(@Url url: String): Call<ResponseBody> // 返回字节流
}