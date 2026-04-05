/**
 * @fileoverview IConfigProvide 模块
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 功能模块实现
 */

package com.newAi302.app.module

/**
 * author : lzh
 * e-mail : luozhanhang@adswave.com
 * time   : 2024/7/1
 * desc   : 环境配置接口
 * version: 1.0
 */
interface IConfigProvide {

    fun initTest()

    fun initTestHtml()

    fun initPreProduct()

    fun initProduct()
    fun initProductHtml()
}