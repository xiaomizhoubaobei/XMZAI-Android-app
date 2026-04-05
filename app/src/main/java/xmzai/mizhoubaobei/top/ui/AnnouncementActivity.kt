/**
 * @fileoverview AnnouncementActivity 界面
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark Activity 或界面页面，处理用户交互和界面逻辑
 */

package xmzai.mizhoubaobei.top.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import xmzai.mizhoubaobei.top.R
import xmzai.mizhoubaobei.top.base.BaseActivity
import xmzai.mizhoubaobei.top.databinding.ActivityAnnouncementBinding
import xmzai.mizhoubaobei.top.databinding.ActivitySettingBinding

class AnnouncementActivity : BaseActivity() {
    private lateinit var binding: ActivityAnnouncementBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_announcement)
        binding = ActivityAnnouncementBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backImage.setOnClickListener {
            finish()
        }

    }
}