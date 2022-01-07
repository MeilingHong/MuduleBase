package com.meiling.utils;

import android.view.View;

import androidx.annotation.Nullable;

/**
 * 2022-01-07 17:12
 *
 * @author marisareimu
 */
public class ViewUtil {
    /**
     * 设置View的可见性
     *
     * @param view      指定的View，可空
     * @param isVisible View是否可见
     */
    public static void setViewVisibility(@Nullable View view, boolean isVisible) {
        if (view == null) {
            return;
        }
        view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }
}
