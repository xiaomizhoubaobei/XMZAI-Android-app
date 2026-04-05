/**
 * @fileoverview TLoginBean 界面
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark Activity 或界面页面，处理用户交互和界面逻辑
 */

package com.newAi302.app.ui.thirdloginsdk

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * author : lzh
 * e-mail : luozhanhang@adswave.com
 * time   : 2024/7/16
 * desc   :
 * version: 1.0
 */
@Parcelize
data class TLoginBean(
    val token: String?,
    val id: String?,
    val typeValue: String?,
    val loginType: TLoginMgr.LoginType,
    val email: String?
) : Parcelable