/**
 * @fileoverview ScreenShotImpl 截图功能
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 截图相关功能实现
 */

package xmzai.mizhoubaobei.top.screenshot.model.engine

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import xmzai.mizhoubaobei.top.screenshot.model.config.ScreenShotConfig
import xmzai.mizhoubaobei.top.screenshot.model.i.IBitmapConvert
import xmzai.mizhoubaobei.top.screenshot.model.i.IBitmapConvertCallBack
import xmzai.mizhoubaobei.top.screenshot.model.i.IScreenShot
import xmzai.mizhoubaobei.top.screenshot.model.i.IScreenShotCallBack
import xmzai.mizhoubaobei.top.screenshot.utils.combineBitmapsIntoOnlyOne
import xmzai.mizhoubaobei.top.screenshot.utils.savingBitmapIntoFile

/**
 * description:
 * author: bear .
 * Created date:  2019-06-25.
 * mail:2280885690@qq.com
 */
class ScreenShotImpl(bitmapConvert: IBitmapConvert) : IScreenShot {

    private var mBitmapConvert = bitmapConvert

    override fun takeCapture(context: Context, view: View, callBack: IScreenShotCallBack?) {
        if (context is Activity && context.isFinishing) return
        mBitmapConvert.convert(view, object : IBitmapConvertCallBack {
            override fun onResult(bitmap: Bitmap?) {
                bitmap?.let {
                    savingBitmapIntoFile(context, it, callBack)
                }
            }
        })
        Log.e("ceshi","截图测试0")

    }

    override fun takeCapture(context: Context, view: View, topBitmap: Bitmap?, callBack: IScreenShotCallBack?) {
        if (topBitmap == null) {
            takeCapture(context, view, callBack)
            return
        }
        mBitmapConvert.convert(view, object : IBitmapConvertCallBack {
            override fun onResult(bitmap: Bitmap?) {
                bitmap?.let {
                    val bitmapList = ArrayList<Bitmap>()
                    bitmapList.add(topBitmap)
                    bitmapList.add(it)
                    val combineBitmap = combineBitmapsIntoOnlyOne(
                        context,
                        bitmapList,
                        context.resources.displayMetrics.widthPixels,
                        ScreenShotConfig.MAX_SCREEN_SHOT_HEIGHT
                    )
                    savingBitmapIntoFile(context, combineBitmap, callBack)
                }
            }
        })
    }

    override fun takeCapture(
        context: Context,
        view: View,
        topBitmap: Bitmap?,
        bottomBitmap: Bitmap?,
        callBack: IScreenShotCallBack?
    ) {
        if (topBitmap == null && bottomBitmap == null) {
            takeCapture(context, view, callBack)
            return
        }
        mBitmapConvert.convert(view, object : IBitmapConvertCallBack {
            override fun onResult(bitmap: Bitmap?) {
                bitmap?.let {
                    val bitmapList = ArrayList<Bitmap>()
                    if (topBitmap != null) {
                        bitmapList.add(topBitmap)
                    }
                    bitmapList.add(it)
                    if (bottomBitmap != null) {
                        bitmapList.add(bottomBitmap)
                    }
                    val combineBitmap = combineBitmapsIntoOnlyOne(
                        context,
                        bitmapList,
                        context.resources.displayMetrics.widthPixels,
                        ScreenShotConfig.MAX_SCREEN_SHOT_HEIGHT
                    )
                    savingBitmapIntoFile(context, combineBitmap, callBack)
                }
            }
        })
    }

    override fun takeCapture(
        context: Context,
        view: View,
        topBitmap: Bitmap?,
        bottomBitmap: Bitmap?,
        width: Int,
        callBack: IScreenShotCallBack?
    ) {
        if (topBitmap == null && bottomBitmap == null) {
            takeCapture(context, view, callBack)
            return
        }
        mBitmapConvert.convert(view, object : IBitmapConvertCallBack {
            override fun onResult(bitmap: Bitmap?) {
                bitmap?.let {
                    val bitmapList = ArrayList<Bitmap>()
                    if (topBitmap != null) {
                        bitmapList.add(topBitmap)
                    }
                    bitmapList.add(it)
                    if (bottomBitmap != null) {
                        bitmapList.add(bottomBitmap)
                    }
                    val combineBitmap = combineBitmapsIntoOnlyOne(
                        context,
                        bitmapList,
                        width,
                        ScreenShotConfig.MAX_SCREEN_SHOT_HEIGHT
                    )
                    savingBitmapIntoFile(context, combineBitmap, callBack)
                }
            }
        })
    }
}