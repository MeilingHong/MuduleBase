package com.meiling.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;

/**
 * 2022-01-07 17:07
 *
 * @author marisareimu
 */
public class NonHttpUrlUtil {
    /**
     * 使用系统组件打开特定链接
     * 可以是Http和非Http链接，APP自定义的也可以
     *
     * @param mContext 上下文
     * @param url 链接
     * @param callback 回调方法
     */
    public static void openBrowser(@NonNull Context mContext, String url, @NonNull Callback callback) {
        if (TextUtils.isEmpty(url)) {
            callback.emptyUrlString();
            return;
        }
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            mContext.startActivity(Intent.createChooser(intent, "Choose appropriate App to open"));
        } else {
            callback.unhandleUrlString(url);
        }
    }

    /**
     * 对应的回调接口，用来告诉调用者传入的参数是什么情况
     */
    public interface Callback {
        void emptyUrlString();

        void unhandleUrlString(String url);
    }
}
