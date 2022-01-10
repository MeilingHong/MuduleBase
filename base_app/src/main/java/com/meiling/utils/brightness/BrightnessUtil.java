package com.meiling.utils.brightness;

import android.app.Activity;
import android.view.WindowManager;

/**
 * 2022-01-10 11:42
 *
 * @author marisareimu
 */
public class BrightnessUtil {
    public interface Callback {
        void invalidActivity();//告诉调用者activity参数为空

        void emptyWindow();// 获取的window对象为空

        void emptyWindowAttribute();// window对象的属性对象为空

        void invalidBrightnessValid(int brightness);// 无效的亮度值
    }

    /**
     * 获取当前页面的（Window）的亮度【不需要权限，但也仅限于操作的页面】
     *
     * @param activity 指定的活动对象
     * @param callback 回调
     * @return 当前页面的亮度值【-1为无效的亮度值，表示发生了异常，此值不能够被使用】
     */
    public static int getWindowBrightness(Activity activity, Callback callback) {
        if (activity == null) {
            if (callback != null) callback.invalidActivity();
            return -1;
        }
        if (activity.getWindow() == null) {
            if (callback != null) callback.emptyWindow();
            return -1;
        }
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        if (lp == null) {
            if (callback != null) callback.emptyWindowAttribute();
            return -1;
        }
        return (int) (lp.screenBrightness * 255);
    }

    /**
     * 设置当前页面的（Window）的亮度【不需要权限，但也仅限于操作的页面】
     *
     * @param activity   指定的活动对象
     * @param brightness 亮度值【限制亮度值的范围】
     * @param callback   回调
     */
    public static void setWindowBrightness(Activity activity, int brightness, Callback callback) {
        if (activity == null) {
            if (callback != null) callback.invalidActivity();
            return;
        }
        if (activity.getWindow() == null) {
            if (callback != null) callback.emptyWindow();
            return;
        }
        if (brightness < 0 || brightness > 255) {
            if (callback != null) callback.invalidBrightnessValid(brightness);// 提示设置的亮度值无效
            return;
        }
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        if (lp == null) {
            if (callback != null) callback.emptyWindowAttribute();
            return;
        }
        lp.screenBrightness = brightness * (1F / 255F);//通过限制，取值范围，保证，亮度值在0~1之间，而不会超出这个范围
        activity.getWindow().setAttributes(lp);
    }
}
