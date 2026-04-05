/**
 * @fileoverview ShowScreenImageActivity 界面
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark Activity 或界面页面，处理用户交互和界面逻辑
 */

package xmzai.mizhoubaobei.top.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import xmzai.mizhoubaobei.top.R
import xmzai.mizhoubaobei.top.base.BaseActivity
import xmzai.mizhoubaobei.top.databinding.ActivityMainBinding
import xmzai.mizhoubaobei.top.databinding.ActivityShowScreenImageBinding
import xmzai.mizhoubaobei.top.utils.BitmapUtils

class ShowScreenImageActivity : BaseActivity() {
    private lateinit var binding:ActivityShowScreenImageBinding
    companion object {
        private const val EXTRA_KEY_PATH = "path"
        @JvmStatic
        fun action(context: Context, path: String) {
            val intent = Intent(context, ShowScreenImageActivity::class.java)
            intent.putExtra(EXTRA_KEY_PATH, path)
            context.startActivity(intent)
        }
    }
    private var path = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityShowScreenImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (intent.getStringExtra(EXTRA_KEY_PATH) != ""){
            path = intent.getStringExtra(EXTRA_KEY_PATH).toString()
        }
        binding.iv.setImageBitmap(BitmapUtils.decodeBitmap(path, this))
    }


}