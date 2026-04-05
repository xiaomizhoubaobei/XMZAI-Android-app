/**
 * @fileoverview OnItemSelectModelClickListener 接口定义
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 回调接口或监听器定义
 */

package com.newAi302.app.infa

import com.newAi302.app.data.ChatBackMessage
import com.newAi302.app.data.ChatMessage
import com.newAi302.app.room.ChatItemChat
import com.newAi302.app.room.ChatItemRoom
import com.newAi302.app.room.SelectModelData

fun interface OnItemSelectModelClickListener { //注意这里用了fun，不然会报错，activity中不能使用这个接口
    fun onItemClick(selectModelData: SelectModelData)
}