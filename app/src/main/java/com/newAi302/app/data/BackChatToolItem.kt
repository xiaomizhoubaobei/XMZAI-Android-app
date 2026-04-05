/**
 * @fileoverview BackChatToolItem 数据模型
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 数据实体类，定义数据结构
 */

package com.newAi302.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.io.Serializable

/**
 * author :
 * e-mail :
 * time   : 2025/4/15
 * desc   :
 * version: 1.0
 */

data class BackChatToolItem(
    val type: String,
    val message: String,
    val position:Int
): Serializable