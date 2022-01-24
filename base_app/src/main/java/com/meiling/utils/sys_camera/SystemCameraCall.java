package com.meiling.utils.sys_camera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import java.io.File;
import java.util.List;

import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

/**
 * 2022-01-10 22:12
 *
 * @author marisareimu
 */
public class SystemCameraCall {

    public interface Callback {
        File getCurrentFile();// 用于返回

        void setCurrentFile(File tempFile);

        void noCameraHandler();// 未找到可以处理动作的程序

        void noCameraPermission();

        void noStoragePermission();

        void noAudioRecordPermission();

        void fileDirectoryEmptyOrNotExist();
    }

    public static void callSystemCamera_Picture(@NonNull ComponentActivity mContext, File directory, String fileName, String authorityString, Callback callback, ActivityResultLauncher<Intent> activityResultLauncher) {
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
            if (directory == null || !directory.exists() || !directory.isDirectory()) {// 保证目录存在，且不为空
                if (callback != null) callback.fileDirectoryEmptyOrNotExist();
                return;
            }
            File tempCameraFile = new File(directory, fileName);// 指定的存储文件
            if (callback != null) callback.setCurrentFile(tempCameraFile);
            takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);// 授予访问权限
            Uri imageUri = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                imageUri = FileProvider.getUriForFile(mContext, authorityString, tempCameraFile);// 在低版本系统上使用该方式，发现会存在文件无法访问的情况，导致使用系统相机拍摄后，无法进行预览【预览会抛出安全异常】
            } else {
                imageUri = Uri.fromFile(tempCameraFile);
            }
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);// 设置输出文件

            List<ResolveInfo> resInfoList = mContext.getPackageManager().queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                mContext.grantUriPermission(packageName, imageUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            activityResultLauncher.launch(takePictureIntent);
        } else {
            if (callback != null) callback.noCameraHandler();// 未找到系统中可以处理该Intent的程序
        }
    }

    public static void callSystemCamera_Video(@NonNull ComponentActivity mContext, File directory, String fileName, String authorityString, int videoTimeLimitSec, Callback callback, @NonNull ActivityResultLauncher<Intent> activityResultLauncher) {
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
            if (mContext.checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                if (callback != null) callback.noAudioRecordPermission();
                return;
            }
        }
        Intent takePictureIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takePictureIntent.resolveActivity(mContext.getPackageManager()) != null) {
            if (directory == null || !directory.exists() || !directory.isDirectory()) {// 保证目录存在，且不为空
                if (callback != null) callback.fileDirectoryEmptyOrNotExist();
                return;
            }
            File tempCameraFile = new File(directory, fileName);// 指定的存储文件
            if (callback != null) callback.setCurrentFile(tempCameraFile);

            takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);// 授予访问权限
            Uri imageUri = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                imageUri = FileProvider.getUriForFile(mContext, authorityString, tempCameraFile);// 在低版本系统上使用该方式，发现会存在文件无法访问的情况，导致使用系统相机拍摄后，无法进行预览【预览会抛出安全异常】
            } else {
                imageUri = Uri.fromFile(tempCameraFile);
            }
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);// 设置输出文件
            takePictureIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, videoTimeLimitSec);// 设置文件限制---时长：单位为秒
            takePictureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);// 设置文件限制---文件质量：此值在最低质量最小文件尺寸时是0，在最高质量最大文件尺寸时是１.

            List<ResolveInfo> resInfoList = mContext.getPackageManager().queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                mContext.grantUriPermission(packageName, imageUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            activityResultLauncher.launch(takePictureIntent);// 使用启动对象启动跳转
        } else {
            if (callback != null) callback.noCameraHandler();// 未找到系统中可以处理该Intent的程序
        }
    }
}
