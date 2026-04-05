/**
 * @fileoverview ConsumptionDetailsActivity 界面
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
import xmzai.mizhoubaobei.top.databinding.ActivityConsumptionDetailsBinding
import xmzai.mizhoubaobei.top.databinding.ActivityVersionInformationBinding

class ConsumptionDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConsumptionDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityConsumptionDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backImage.setOnClickListener {
            finish()
        }
    }

}