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
        // 对于有连续调用需求的场景，建议对Callback实现类对象中声明一个File对象来获取
        File getCurrentFile();// 用于返回照相的文件对象【主要用于在从】

        void setCurrentFile(File tempFile);// 用了获取调用系统相机时，传入的文件对象

        void noCameraHandler();// 未找到可以处理动作的程序

        void noCameraPermission();// 无相机权限

        void noStoragePermission();// 无存储权限

        void fileDirectoryEmptyOrNotExist();// 目录对象为空或不存在【需要在调用前保证目录被创建出来】
    }

    public interface CallbackVideo {
        // 对于有连续调用需求的场景，建议对Callback实现类对象中声明一个File对象来获取
        File getCurrentFile();// 用于返回照相的文件对象【主要用于在从】

        void setCurrentFile(File tempFile);// 用了获取调用系统相机时，传入的文件对象

        void noCameraHandler();// 未找到可以处理动作的程序

        void noCameraPermission();// 无相机权限

        void noStoragePermission();// 无存储权限

        void noAudioRecordPermission();// 无录音权限

        void fileDirectoryEmptyOrNotExist();// 目录对象为空或不存在【需要在调用前保证目录被创建出来】
    }

    public static void callSystemCamera_Picture(@NonNull ComponentActivity mContext, File directory, String fileName, String authorityString, Callback callback, @NonNull ActivityResultLauncher<Intent> activityResultLauncher) {
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
            activityResultLauncher.launch(takePictureIntent);// 使用启动对象启动跳转
        } else {
            if (callback != null) callback.noCameraHandler();// 未找到系统中可以处理该Intent的程序
        }
    }


    public static void callSystemCamera_Video(@NonNull ComponentActivity mContext, File directory, String fileName, String authorityString,
                                              Integer videoTimeLimitSec,
                                              Long videoSizeBytes,
                                              CallbackVideo callback, @NonNull ActivityResultLauncher<Intent> activityResultLauncher) {
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
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(mContext.getPackageManager()) != null) {
            if (directory == null || !directory.exists() || !directory.isDirectory()) {// 保证目录存在，且不为空
                if (callback != null) callback.fileDirectoryEmptyOrNotExist();
                return;
            }
            File tempCameraFile = new File(directory, fileName);// 指定的存储文件
            if (callback != null) callback.setCurrentFile(tempCameraFile);
            takeVideoIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);// 授予访问权限
            Uri imageUri = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                imageUri = FileProvider.getUriForFile(mContext, authorityString, tempCameraFile);// 在低版本系统上使用该方式，发现会存在文件无法访问的情况，导致使用系统相机拍摄后，无法进行预览【预览会抛出安全异常】
            } else {
                imageUri = Uri.fromFile(tempCameraFile);
            }
            takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);// 设置输出文件
            if (videoTimeLimitSec != null && videoTimeLimitSec > 0) takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, videoTimeLimitSec);// 设置文件限制---时长：单位为秒
            takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);// 设置文件限制---文件质量：此值在最低质量最小文件尺寸时是0，在最高质量最大文件尺寸时是１.
            if (videoSizeBytes != null && videoSizeBytes > 0) takeVideoIntent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, videoSizeBytes);// 设置文件限制---文件大小：以字节为单位.

            List<ResolveInfo> resInfoList = mContext.getPackageManager().queryIntentActivities(takeVideoIntent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                mContext.grantUriPermission(packageName, imageUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            activityResultLauncher.launch(takeVideoIntent);// 使用启动对象启动跳转
        } else {
            if (callback != null) callback.noCameraHandler();// 未找到系统中可以处理该Intent的程序
        }
    }
}
