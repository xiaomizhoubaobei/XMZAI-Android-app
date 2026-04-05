/**
 * @fileoverview OnItemSelectModelClickListener 接口定义
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 回调接口或监听器定义
 */

package xmzai.mizhoubaobei.top.infa

import xmzai.mizhoubaobei.top.data.ChatBackMessage
import xmzai.mizhoubaobei.top.data.ChatMessage
import xmzai.mizhoubaobei.top.room.ChatItemChat
import xmzai.mizhoubaobei.top.room.ChatItemRoom
import xmzai.mizhoubaobei.top.room.SelectModelData

fun interface OnItemSelectModelClickListener { //注意这里用了fun，不然会报错，activity中不能使用这个接口
    fun onItemClick(selectModelData: SelectModelData)
}