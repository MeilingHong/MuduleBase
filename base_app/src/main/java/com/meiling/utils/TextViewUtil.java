package com.meiling.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

/**
 * 2022-01-07 15:04
 *
 * @author marisareimu
 */
public class TextViewUtil {

    public enum TextSizeUnit {
        PX, DIP, SP;
    }

    /**
     * 代码设置TextView的加粗
     * 显示效果会比xml中android:textStyle="bold"要细，
     * 但比android:textStyle="normal"要粗
     *
     * @param textView 需要设置加粗效果的TextView
     */
    public static void setFakeBoldText(@Nullable TextView textView) {
        if (textView == null) {
            return;
        }
        if (null == textView.getPaint()) {
            return;
        }
        textView.getPaint().setFakeBoldText(true);
    }

    /**
     * 设置TextView的文字大小
     *
     * @param textView     待设置的TextView
     * @param textSizeUnit 指定大小使用的单位
     * @param textSize     指定大小使用的大小
     */
    public static void setTextSize(@Nullable TextView textView, @NonNull TextSizeUnit textSizeUnit, @FloatRange(from = 0) float textSize) {
        if (textView == null) {
            return;
        }
        textView.setTextSize(textSizeUnit == TextSizeUnit.PX ? TypedValue.COMPLEX_UNIT_PX :
                        textSizeUnit == TextSizeUnit.DIP ? TypedValue.COMPLEX_UNIT_DIP :
                                textSizeUnit == TextSizeUnit.SP ? TypedValue.COMPLEX_UNIT_SP :
                                        TypedValue.COMPLEX_UNIT_SP,
                textSize);
    }

    /**
     * 指定TextView显示的颜色
     *
     * @param textView 待设置的TextView
     * @param color    需要设置的颜色
     */
    public static void setTextColor(@Nullable TextView textView, @ColorInt int color) {
        if (textView == null) {
            return;
        }
        textView.setTextColor(color);
    }

    /**
     * 指定TextView显示的颜色
     *
     * @param textView 待设置的TextView
     * @param mContext 上下文
     * @param color    需要设置的颜色【在资源中的位置】
     */
    public static void setTextColor(@Nullable TextView textView, @NonNull Context mContext, @ColorRes int color) {
        if (textView == null) {
            return;
        }
        textView.setTextColor(ContextCompat.getColor(mContext, color));
    }

    /**
     * 指定TextView提示文字的颜色【主要为EditText使用】
     *
     * @param textView 待设置的TextView
     * @param color    需要设置的颜色
     */
    public static void setHintColor(@Nullable TextView textView, @ColorInt int color) {
        if (textView == null) {
            return;
        }
        textView.setHintTextColor(color);
    }

    /**
     * 指定TextView提示文字的颜色【主要为EditText使用】
     *
     * @param textView 待设置的TextView
     * @param mContext 上下文
     * @param color    需要设置的颜色【在资源中的位置】
     */
    public static void setHintColor(@Nullable TextView textView, @NonNull Context mContext, @ColorRes int color) {
        if (textView == null) {
            return;
        }
        textView.setTextColor(ContextCompat.getColor(mContext, color));
    }


    /**
     * 使用资源引用的方式设置TextView的文字
     *
     * @param textView 待设置的TextView
     * @param content  需要显示内容
     */
    public static void setTextViewContent(@Nullable TextView textView, @StringRes int content) {
        if (textView == null) {
            return;
        }
        textView.setText(content);
    }

    /**
     * 设置TextView内显示的文字
     *
     * @param textView 待设置的TextView
     * @param content  需要显示内容
     */
    public static void setTextViewContent(@Nullable TextView textView, CharSequence content) {
        if (textView == null) {
            return;
        }
        textView.setText(TextUtils.isEmpty(content) ? "" : content);
    }
}

