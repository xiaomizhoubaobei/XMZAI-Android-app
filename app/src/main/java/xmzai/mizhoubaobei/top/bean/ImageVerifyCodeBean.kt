package xmzai.mizhoubaobei.top.bean

/**
 * 图片验证码数据类
 */
data class ImageVerifyCodeBean(
    val image: String? = null,  // base64 编码的图片或图片 URL
    val key: String? = null      // 验证码标识key
)
