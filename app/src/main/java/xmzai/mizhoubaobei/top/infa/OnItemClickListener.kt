/**
 * @fileoverview OnItemClickListener 接口定义
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

interface OnItemClickListener {
    fun onItemClick(chatItem: ChatItemRoom)

    fun onDeleteClick(selectList: MutableList<Int>)

    fun onBackFunctionClick(chatFunction: ChatBackMessage)
}