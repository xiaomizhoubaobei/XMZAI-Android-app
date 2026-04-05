/**
 * @fileoverview ScreenPosition 数据模型
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 数据实体类，定义数据结构
 */

package xmzai.mizhoubaobei.top.data

/**
 * 封装 View 在屏幕上的绝对位置（X、Y 坐标）
 * @param x 屏幕水平坐标（从屏幕左侧边缘开始计算）
 * @param y 屏幕垂直坐标（从屏幕顶部边缘开始计算，含状态栏）
 */
data class ScreenPosition(val x: Int, val y: Int)