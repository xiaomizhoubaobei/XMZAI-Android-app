/**
 * @fileoverview Sha1Util 界面
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark Activity 或界面页面，处理用户交互和界面逻辑
 */

package xmzai.mizhoubaobei.top.ui.thirdloginsdk

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.os.Build
import android.util.Base64
import xmzai.mizhoubaobei.top.utils.LogUtils
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.Locale

/**
 * author : lzh
 * e-mail : luozhanhang@adswave.com
 * time   : 2024/7/16
 * desc   :
 * version: 1.0
 */

/**
 * 用来获取 facebook 的 keyHash 和 google 的 Sha1 key。
 */
object Sha1Util {

    fun getKeyHash(context: Context): String {
        LogUtils.i("Sha1Util", "sha1 = $sha1")
        var keyHash = ""
        try {
            val info = context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.GET_SIGNING_CERTIFICATES
            )
            val signatures: Array<Signature>? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                info.signingInfo?.apkContentsSigners
            } else {
                @Suppress("DEPRECATION")
                info.signatures
            }
            signatures?.forEach { signature ->
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                keyHash = Base64.encodeToString(md.digest(), Base64.DEFAULT)
                LogUtils.d("Sha1Util", "KeyHash: $keyHash")
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return keyHash
    }

    @Throws(
        PackageManager.NameNotFoundException::class,
        CertificateException::class,
        NoSuchAlgorithmException::class
    )
    fun getCertificateSHA1Fingerprint(context: Context): String {
        val pm = context.packageManager
        val pName = context.packageName
        val flags = PackageManager.GET_SIGNING_CERTIFICATES
        var pInfo: PackageInfo? = null

        pInfo = pm.getPackageInfo(pName, flags)
        val cert: ByteArray? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            pInfo.signingInfo?.apkContentsSigners?.get(0)?.toByteArray()
        } else {
            @Suppress("DEPRECATION")
            pInfo.signatures!![0].toByteArray()
        }
            ?: return ""
        val input: InputStream = ByteArrayInputStream(cert)
        val cf = CertificateFactory.getInstance("X509")
        val c = cf.generateCertificate(input) as X509Certificate
        val md = MessageDigest.getInstance("SHA1")
        val publicKey = md.digest(c.encoded)
        return byte2HexFormatted(publicKey)
    }

    private fun byte2HexFormatted(arr: ByteArray): String {
        val str = StringBuilder(arr.size * 2)
        for (i in arr.indices) {
            var h = Integer.toHexString(arr[i].toInt())
            val l = h.length
            if (l == 1) {
                h = "0$h"
            }
            if (l > 2) {
                h = h.substring(l - 2, l)
            }
            str.append(h.uppercase(Locale.getDefault()))
            if (i < (arr.size - 1)) {
                str.append(':')
            }
        }
        return str.toString()
    }
}