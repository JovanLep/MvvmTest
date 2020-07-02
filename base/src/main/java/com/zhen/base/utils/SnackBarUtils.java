package com.zhen.base.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.zhen.base.R;
import com.zhen.mvvm.utils.Utils;

import static android.view.Gravity.BOTTOM;
import static android.view.Gravity.CENTER;
import static android.view.Gravity.CENTER_HORIZONTAL;


/**
 * @author Administrator
 */
public class SnackBarUtils {

    /**
     * 显示消息提示
     *
     * @param activity 页面环境
     * @param resId    需要显示的消息资源ID
     *                 显示时间  Snackbar.LENGTH_INDEFINITE 不消失显示，除非手动取消
     *                 Snackbar.LENGTH_SHORT 短时间显示
     *                 Snackbar.LENGTH_LONG 长时间显示
     *                 自定义一个毫秒值
     */
    public static void showSnackBar(@NonNull Activity activity, @StringRes int resId) {
        Snackbar snackbar = Snackbar.make(activity.getWindow().getDecorView(), resId, Snackbar.LENGTH_LONG);
        snackBarStyle1(snackbar);
        snackbar.show();
    }

    /**
     * 显示消息提示
     *
     * @param activity 页面环境
     * @param text     需要显示的消息
     *                 显示时间  Snackbar.LENGTH_INDEFINITE 不消失显示，除非手动取消
     *                 Snackbar.LENGTH_SHORT 短时间显示
     *                 Snackbar.LENGTH_LONG 长时间显示
     *                 自定义一个毫秒值
     */
    public static void showSnackBar(@NonNull Activity activity, @NonNull CharSequence text) {
        Snackbar snackbar = Snackbar.make(activity.getWindow().getDecorView(), text, Snackbar.LENGTH_LONG);
        snackBarStyle1(snackbar);
        snackbar.show();
    }

    /**
     * 显示消息提示
     *
     * @param fragment 页面环境
     * @param resId    需要显示的消息资源ID
     */
    public static void showSnackBar(@NonNull Fragment fragment, @StringRes int resId) {
        if (fragment.getView() == null) {
            return;
        }
        Snackbar snackbar = Snackbar.make(fragment.getView(), resId, Snackbar.LENGTH_LONG);
        snackBarStyle1(snackbar);
        snackbar.show();
    }

    /**
     * 显示消息提示
     *
     * @param fragment 页面环境
     * @param text     需要显示的消息
     */
    public static void showSnackBar(@NonNull Fragment fragment, @NonNull CharSequence text) {
        if (fragment.getView() == null) {
            return;
        }
        Snackbar snackbar = Snackbar.make(fragment.getView(), text, Snackbar.LENGTH_LONG);
        snackBarStyle1(snackbar);
        snackbar.show();
    }

    /**
     * 显示消息提示
     *
     * @param dialog 页面环境
     * @param resId  需要显示的消息资源ID
     */
    public static void showSnackBar(@NonNull Dialog dialog, @StringRes int resId) {
        if (dialog.getWindow() == null) {
            return;
        }
        Snackbar snackbar = Snackbar.make(dialog.getWindow().getDecorView(), resId, Snackbar.LENGTH_LONG);
        snackBarStyle2(snackbar);
        snackbar.show();
    }

    /**
     * 显示消息提示
     *
     * @param dialog 页面环境
     * @param text   需要显示的消息
     */
    public static void showSnackBar(@NonNull Dialog dialog, @NonNull CharSequence text) {
        if (dialog.getWindow() == null) {
            return;
        }
        Snackbar snackbar = Snackbar.make(dialog.getWindow().getDecorView(), text, Snackbar.LENGTH_LONG);
        snackBarStyle2(snackbar);
        snackbar.show();
    }

    /**
     * Snackbar样式1-普通toast样式
     *
     * @param snackbar Snackbar对象
     */
    private static void snackBarStyle1(@NonNull Snackbar snackbar) {
        //获取SnackBar布局View实例
        View snackbarView = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarView;

        //Androidx 1.1.0s使用
//       snackbarLayout.setPadding(0, 0, 0, ScreenUtil.dip2px(BaseApp.getInstance().getApplicationContext(), 100f));
        //更改背景颜色
        snackbarLayout.setBackgroundColor(Color.TRANSPARENT);
        //获取文本View实例
        TextView textView = snackbarLayout.findViewById(R.id.snackbar_text);
        //获取按钮View实例
        Button button = snackbarLayout.findViewById(R.id.snackbar_action);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
        textView.setGravity(CENTER);
        textView.setTextSize(12);
        //更改文本颜色
        textView.setTextColor(Color.WHITE);
        textView.setBackgroundResource(R.drawable.conner_bg_snack);
        int textLength = textView.getText().length();
        if (textView.getText().toString().contains("\n")) {
            textLength = 0;
            for (String text : textView.getText().toString().split("\n")) {
                textLength = Math.max(textLength, text.length());
            }
        }
        int screenLengthPx = ScreenUtil.getScreenResolution(Utils.getContext())[0] - ScreenUtil.dip2px(Utils.getContext(), 40f);
        int width = Math.min((textLength + 4) * ScreenUtil.dip2px(Utils.getContext(), 12f), screenLengthPx);
        if (snackbarLayout.getLayoutParams() instanceof CoordinatorLayout.LayoutParams) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) snackbarLayout.getLayoutParams();
            layoutParams.width = width;
            layoutParams.gravity = CENTER_HORIZONTAL | BOTTOM;
            layoutParams.bottomMargin = ScreenUtil.dip2px(Utils.getContext(), 100f);
            snackbarLayout.setLayoutParams(layoutParams);
        } else if (snackbarLayout.getLayoutParams() instanceof FrameLayout.LayoutParams) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) snackbarLayout.getLayoutParams();
            layoutParams.width = width;
            layoutParams.gravity = CENTER_HORIZONTAL | BOTTOM;
            layoutParams.bottomMargin = ScreenUtil.dip2px(Utils.getContext(), 100f);
            snackbarLayout.setLayoutParams(layoutParams);
        }
    }

    /**
     * Snackbar样式1-普通toast样式
     *
     * @param snackbar Snackbar对象
     */
    private static void snackBarStyle2(@NonNull Snackbar snackbar) {
        //获取SnackBar布局View实例
        View snackbarView = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarView;
        //更改背景颜色
        snackbarLayout.setBackgroundColor(Color.TRANSPARENT);
        //获取文本View实例
        TextView textView = snackbarLayout.findViewById(R.id.snackbar_text);
        //获取按钮View实例
        Button button = snackbarLayout.findViewById(R.id.snackbar_action);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
        textView.setGravity(CENTER);
        textView.setTextSize(12);
        //更改文本颜色
        textView.setTextColor(Color.WHITE);
        textView.setBackgroundResource(R.drawable.conner_bg_snack);
        int textLength = textView.getText().length();
        if (textView.getText().toString().contains("\n")) {
            textLength = 0;
            for (String text : textView.getText().toString().split("\n")) {
                textLength = Math.max(textLength, text.length());
            }
        }
        int screenLengthPx = ScreenUtil.getScreenResolution(Utils.getContext())[0] - ScreenUtil.dip2px(Utils.getContext(), 40f);
        int width = Math.min((textLength + 4) * ScreenUtil.dip2px(Utils.getContext(), 12f), screenLengthPx);
        if (snackbarLayout.getLayoutParams() instanceof CoordinatorLayout.LayoutParams) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) snackbarLayout.getLayoutParams();
            layoutParams.width = width;
            layoutParams.gravity = CENTER;
            snackbarLayout.setLayoutParams(layoutParams);
        } else if (snackbarLayout.getLayoutParams() instanceof FrameLayout.LayoutParams) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) snackbarLayout.getLayoutParams();
            layoutParams.width = width;
            layoutParams.gravity = CENTER;
            snackbarLayout.setLayoutParams(layoutParams);
        }
    }
}