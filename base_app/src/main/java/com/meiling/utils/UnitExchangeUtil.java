package com.meiling.utils;

import android.content.Context;

/**
 * 2022-01-07 17:24
 * @author marisareimu
 */
public class UnitExchangeUtil {
    /**
     * dip转化成px
     *
     * @param context 上下文
     * @param dpValue 多少DP
     * @return 转换后的PX值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转化成dip
     *
     * @param context 上下文
     * @param pxValue 多少Px值
     * @return 转换后的DP值
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * px转化成sp
     *
     * @param context 上下文
     * @param pxValue 多少Px值
     * @return 转换后的SP值
     */
    public static int px2sp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转化成px
     *
     * @param context 上下文
     * @param spValue 多少SP值
     * @return 转换后的PX值
     */
    public static int sp2px(Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }
}
