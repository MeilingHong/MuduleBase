package com.meiling.utils.sys_camera;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.NonNull;

/**
 * 2022-01-10 22:12
 *
 * @author marisareimu
 */
public class SystemCameraCall {
    public static final int SYS_CAMERA_REQUEST_CODE = 10089;

    public interface Callback {
        void noCameraHandler();// 未找到可以处理动作的程序

        void noCameraPermission();

        void noStoragePermission();
    }

    public static void callSystemCamera_Picture(@NonNull Context mContext, Callback callback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {// 动态权限检查，保证拥有必要的权限
            if (mContext.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (callback != null) callback.noCameraPermission();
                return;
            }
            if (mContext.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    mContext.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (callback != null) callback.noStoragePermission();
                return;
            }
        }
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(mContext.getPackageManager()) != null) {

        } else {
            if (callback != null) callback.noCameraHandler();// 未找到系统中可以处理该Intent的程序
        }
    }
}
