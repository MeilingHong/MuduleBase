package com.meiling.utils.click_interrupt;

import android.view.View;

/**
 * 拦截指定间隔时间内的
 * <p>
 * 2022-01-10 14:15
 *
 * @author marisareimu
 */
public class ClickInterrupt implements View.OnClickListener {
    private int INTERVAL_MS = 0;
    private long clickTimestamp = 0;
    private View.OnClickListener clickListener;

    public ClickInterrupt(int INTERVAL_MS, View.OnClickListener clickListener) {
        this.INTERVAL_MS = Math.max(INTERVAL_MS, 0);// 不针对时间间隔限制那么死，
        this.clickListener = clickListener;
    }

    @Override
    public void onClick(View v) {
        if (INTERVAL_MS <= 0) {// 如果不设置间隔时间，则直接进行回调
            if (clickListener != null) clickListener.onClick(v);
            return;
        }
        // 设置了间隔时间后，需要保证两次点击必须达到设置的间隔时间
        if (clickTimestamp <= 0 ||// 第一次
                ((System.currentTimeMillis() - clickTimestamp) >= INTERVAL_MS)) {//两次间隔时间超出了指定的间隔时间
            clickTimestamp = System.currentTimeMillis();
            if (clickListener != null) clickListener.onClick(v);
        }
    }
}
