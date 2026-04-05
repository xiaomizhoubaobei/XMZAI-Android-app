/**
 * @fileoverview ModelDataRoom 数据存储
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 本地数据库或数据持久化相关组件
 */

package xmzai.mizhoubaobei.top.room

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import xmzai.mizhoubaobei.top.data.ChatMessage
import java.io.Serializable

/**
 * author :
 * e-mail :
 * time   : 2025/4/15
 * desc   :
 * version: 1.0
 */
@Entity(tableName = "model_items",
    // 为 title 字段添加唯一索引（全局唯一）
    indices = [Index(value = ["modelId"], unique = true)])
@TypeConverters(MessagesConverter::class)
data class ModelDataRoom(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val modelId: String,
    val remark: String,
    val reasoning: Boolean,
    val imageUnderstanding: Boolean,
    val baseUrl: String,
    val apiKey: String,
    val isCustomize: Boolean = false
): Serializable