/**
 * @fileoverview UserConfigurationRoom 数据存储
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
import java.io.Serializable

@Entity(tableName = "user_items",
    // 为 title 字段添加唯一索引（全局唯一）
    indices = [Index(value = ["userId"], unique = true)])
@TypeConverters(MessagesConverter::class)
data class UserConfigurationRoom (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    var systemLanguage: String,
    var systemTheme: String,
    var useTracelessSwitch: Boolean,
    var slideBottomSwitch: Boolean,
    var appEmojisData: String,
    var searchServiceType: String,
    var defaultChatModelType: String,
    var defaultBuildTitleModelType: String,
    var modelList: MutableList<String>,
    var buildTitleTime:String
): Serializable
