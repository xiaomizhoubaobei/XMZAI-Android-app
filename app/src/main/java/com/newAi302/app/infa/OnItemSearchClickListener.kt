/**
 * @fileoverview OnItemSearchClickListener 接口定义
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 回调接口或监听器定义
 */

package com.newAi302.app.infa

import com.newAi302.app.room.ChatItemRoom

interface OnItemSearchClickListener {
    fun onItemClick(chatItem: ChatItemRoom)
}