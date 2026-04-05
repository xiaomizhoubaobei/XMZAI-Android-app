/**
 * @fileoverview RegisterBeanRes 数据模型
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 数据实体类，定义数据结构
 */

package com.newAi302.app.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * author : lzh
 * e-mail : luozhanhang@adswave.com
 * time   : 2024/5/14
 * desc   : 注册实体
 * version: 1.0
 */
@Parcelize
data class RegisterBeanRes(
    val token: String = ""
) : Parcelable

