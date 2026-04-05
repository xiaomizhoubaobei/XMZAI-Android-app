/**
 * @fileoverview IMenuBarViewListener 自定义视图
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 自定义 UI 组件
 */

package xmzai.mizhoubaobei.top.widget.listener

/**
 * author : lzh
 * e-mail : luozhanhang@adswave.com
 * time   : 2024/6/4
 * desc   : 菜单栏事件监听接口
 * version: 1.0
 */
interface IMenuBarViewListener {
    fun onTotal() //汇总
    fun onDynamicTraffic() //动态IP 按流量计费
    fun onDynamicIp()  //动态IP 按IP计费
    fun onStaticTraffic() //静态IP 按流量计费
    fun onStaticIp()  //静态IP 按IP计费
    fun onQuickAccess() //快速访问
    fun onPersonalCenter() //个人中心
    fun onHelpCenter() //帮助中心
    fun onLogout() //登出
}