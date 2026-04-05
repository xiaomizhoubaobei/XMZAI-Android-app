/**
 * @fileoverview DialogFactory 自定义视图
 * @author 祁筱欣
 * @date 2026-04-05
 * @since 2026-04-05
 * @LICENSE AGPL-3.0 license
 * @remark 自定义 UI 组件
 */

package com.newAi302.app.widget.dialog.base;


import static com.newAi302.app.utils.StringUtils.getString;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.WindowManager;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.newAi302.app.R;

import java.util.Objects;

/**
 * author : lzh
 * e-mail : luozhanhang@adswave.com
 * time   : 2024/7/1
 * desc   : 加载动画弹窗 loading
 * version: 1.0
 */
public class DialogFactory {

    /**
     * 全局loading
     */
    @SuppressLint("SetTextI18n")
    public static Dialog createLoadingDialog(Context context, LoadDissListener loadDissListener) {
        Dialog progressDialog = new Dialog(context, R.style.LoadingDialog);
        progressDialog.setContentView(R.layout.dialog_loading);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.getWindow().setDimAmount(0f);
        progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        LottieAnimationView loading = progressDialog.findViewById(R.id.loading);
        loading.setImageAssetsFolder("images/");
        loading.loop(true);
        loading.setAnimation("loading_of_love.json");
        ((TextView) progressDialog.findViewById(R.id.tv_loading)).setText(getString(R.string.toast_loading));

        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setOnCancelListener(dialog -> {
            if (loadDissListener != null) {
                loadDissListener.onLoadCancel();
            }
            loading.cancelAnimation();
        });
        progressDialog.setOnShowListener(dialog ->
                loading.playAnimation());
        return progressDialog;
    }


    public interface LoadDissListener {
        void onLoadCancel();
    }
}
