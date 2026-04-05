/**
 * @fileoverview PermissionUtils 工具类
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 工具方法集合，提供通用功能支持
 */

package xmzai.mizhoubaobei.top.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat

/**
 * author : lzh
 * e-mail : luozhanhang@adswave.com
 * time   : 2025/5/15
 * desc   :
 * version: 1.0
 */
object PermissionUtils {

    val TAG = "PermissionUtils"
    fun checkRecordPermission(activity: Activity) {
        val permission = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO)
        if (permission == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Record permission is granted")
        } else {
            Log.d(TAG, "Requesting record permission")
            activity.requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), 0)
        }
    }
}