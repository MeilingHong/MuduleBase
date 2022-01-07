package com.meiling.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

/**
 * 2022-01-07 16:57
 * @author marisareimu
 */
public class DrawableUtil {
    /**
     * 获取系统的Drawable对象
     *
     * @param context 上下文
     * @param drawableRes 资源位置
     * @return 资源对应的Drawable对象
     */
    public static Drawable getResDrawable(@NonNull Context context, @DrawableRes int drawableRes){
        return ContextCompat.getDrawable(context, drawableRes);
    }
}
