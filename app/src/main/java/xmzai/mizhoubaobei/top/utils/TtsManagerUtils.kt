/**
 * @fileoverview TtsManagerUtils 工具类
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 工具方法集合，提供通用功能支持
 */

package xmzai.mizhoubaobei.top.utils

import android.content.Context
import android.util.Log
//import com.air4.chinesetts.dispatcher.OnTtsStateListener
//import com.air4.chinesetts.dispatcher.TtsStateDispatcher
//import com.air4.chinesetts.tts.TtsManager


object TtsManagerUtils {

    fun initTts(context:Context){
//        TtsManager.getInstance().init(context)
//
//        TtsStateDispatcher.getInstance().addListener(object : OnTtsStateListener {
//            override fun onTtsReady() {
//                Log.e("ceshi","tts贮备好了")
//            }
//
//            override fun onTtsStart(text: String) {
//                // 空实现（根据需要添加逻辑）
//            }
//
//            override fun onTtsStop() {
//                // 空实现（根据需要添加逻辑）
//            }
//        })

    }

    fun TtsSpeak(inputText:String){
        //TtsManager.getInstance().speak(inputText, 1f, true)
    }

    fun TtsStop(){
        //TtsManager.getInstance().stopTts()
    }





}