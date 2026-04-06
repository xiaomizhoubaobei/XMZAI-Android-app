/**
 * @fileoverview WearData 工具类
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 工具方法集合，提供通用功能支持
 */

package xmzai.mizhoubaobei.top.utils.base;

import xmzai.mizhoubaobei.top.constant.SPKeyConstance;
import xmzai.mizhoubaobei.top.utils.SPUtils;

/**
 * author : lzh
 * e-mail : luozhanhang@adswave.com
 * time   : 2024/7/1
 * desc   : 缓存数据管理类
 * version: 1.0
 */
public class WearData {

    private static WearData instance = new WearData();

    private WearData() {
    }

    public static WearData getInstance() {
        return instance;
    }

    public void saveGetModelList(Boolean isGet) {
        SPUtils.getInstance().put(SPKeyConstance.GET_MODEL_LIST_SUCCESS, isGet);
    }

    /**
     * 获取模型列表状态
     */
    public Boolean getGetModelList() {
        return SPUtils.getInstance().getBoolean(SPKeyConstance.GET_MODEL_LIST_SUCCESS);
    }

}
