/**
 * @fileoverview DataStoreUtils 工具类
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 工具方法集合，提供通用功能支持
 */

package xmzai.mizhoubaobei.top.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore


val Context.dataStore by preferencesDataStore(name = "proxy_info")

//key
val keyName = stringPreferencesKey("username")
val keyPassWord = stringPreferencesKey("password")
val keyPort = intPreferencesKey("port")
val keyType = stringPreferencesKey("type")
val keyServer = stringPreferencesKey("server")

val keyGoogleLogin = booleanPreferencesKey("isGoogleLogin")





