package com.meiling.utils.apk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import java.io.File;

import androidx.core.content.FileProvider;

/**
 * 2022-01-10 11:24
 *
 * @author marisareimu
 */
public class InstallApkUtil {
    public interface Callback {
        void invalidContext();// 上下文为空

        void emptyAuthority();// 授权字符串为空

        void invalidFile();// 文件对象为空、文件不存在、文件大小为0
    }

    /**
     * 执行文件安装的工具类
     *
     * @param mContext  上下文
     * @param authority 授权
     * @param file      指定的文件
     * @param callback  回调（返回相关的异常）
     */
    public static void installApk(Context mContext, String authority, File file, Callback callback) {
        if (mContext == null) {
            if (callback != null) callback.invalidContext();
            return;
        }
        if (TextUtils.isEmpty(authority)) {
            if (callback != null) callback.emptyAuthority();
            return;
        }
        if (file == null || !file.exists() || file.length() <= 0) {
            if (callback != null) callback.invalidFile();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        Uri uriData;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {// 获取文件的访问授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uriData = FileProvider.getUriForFile(mContext, authority, file);
        } else {
            uriData = Uri.fromFile(file);
        }
        String type = "application/vnd.android.package-archive";
        intent.setDataAndType(uriData, type);
        mContext.startActivity(intent);
    }
}
