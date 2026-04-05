/**
 * @fileoverview NetworkFactory 网络模块
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 网络请求相关组件
 */

package com.newAi302.app.http

/**
 * author : lzh
 * e-mail : luozhanhang@adswave.com
 * time   : 2025/4/9
 * desc   :
 * version: 1.0
 */
object NetworkFactory {
    fun <T> createApiService(serviceClass: Class<T>,baseUrl:String): T {
        //return NetworkModule.createService(serviceClass)
        return NetworkModule.createServiceWithBaseUrl(serviceClass, baseUrl)
    }
}