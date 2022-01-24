package com.meiling.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.meiling.utils.TextViewUtil;
import com.meiling.utils.activity.ActivityUtil;
import com.meiling.utils.log.LogCatUtil;
import com.meiling.utils.sys_camera.SystemCameraCall;

import java.io.File;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {

    private SystemCameraCall.Callback mCallback = new SystemCameraCall.Callback() {
        private File mTempFile;

        @Override
        public File getCurrentFile() {
            return mTempFile;
        }

        @Override
        public void setCurrentFile(File tempFile) {
            mTempFile = tempFile;
        }

        @Override
        public void noCameraHandler() {
            LogCatUtil.e("noCameraHandler");
        }

        @Override
        public void noCameraPermission() {
            LogCatUtil.e("noCameraPermission");
        }

        @Override
        public void noStoragePermission() {
            LogCatUtil.e("noStoragePermission");
        }

        @Override
        public void fileDirectoryEmptyOrNotExist() {
            LogCatUtil.e("fileDirectoryEmptyOrNotExist");
        }
    };

    private SystemCameraCall.CallbackVideo mVideoCallback = new SystemCameraCall.CallbackVideo() {
        private File mTempFile;

        @Override
        public File getCurrentFile() {
            return mTempFile;
        }

        @Override
        public void setCurrentFile(File tempFile) {
            mTempFile = tempFile;
        }

        @Override
        public void noCameraHandler() {
            LogCatUtil.e("noCameraHandler");
        }

        @Override
        public void noCameraPermission() {
            LogCatUtil.e("noCameraPermission");
        }

        @Override
        public void noStoragePermission() {
            LogCatUtil.e("noStoragePermission");
        }

        @Override
        public void noAudioRecordPermission() {
            LogCatUtil.e("noAudioRecordPermission");
        }

        @Override
        public void fileDirectoryEmptyOrNotExist() {
            LogCatUtil.e("fileDirectoryEmptyOrNotExist");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogCatUtil.setTagAndDebug("AndroidRuntime", true);
        final ActivityResultLauncher<Intent> activityResultLauncher = ActivityUtil.getActivityLauncher(this, new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                /*
                 * 测试，该方式在API 22 【华为畅享5s上执行成功】
                 * 并且连续执行时，返回的文件会改变
                 *
                 * todo 更高版本系统有待验证
                 */
                // todo 拍照权限检查
//                if (result != null && result.getResultCode() == RESULT_OK) {
//                    // 拍照执行成功
//                    if (mCallback != null &&
//                            // 检查文件存在与否
//                            mCallback.getCurrentFile() != null &&
//                            mCallback.getCurrentFile().exists() &&
//                            mCallback.getCurrentFile().length() > 0) {
//                        LogCatUtil.e("拍照执行成功：" + mCallback.getCurrentFile().getAbsolutePath());
//                    } else {
//                        LogCatUtil.e("拍照执行失败---文件不存在");
//                    }
//                } else {
//                    LogCatUtil.e("拍照执行失败");
//                }
                // todo 摄像权限检查
                if (result != null && result.getResultCode() == RESULT_OK) {
                    // 拍照执行成功
                    if (mVideoCallback != null &&
                            // 检查文件存在与否
                            mVideoCallback.getCurrentFile() != null &&
                            mVideoCallback.getCurrentFile().exists() &&
                            mVideoCallback.getCurrentFile().length() > 0) {
                        LogCatUtil.e("摄像执行成功：" + mVideoCallback.getCurrentFile().getAbsolutePath());
                    } else {
                        LogCatUtil.e("摄像执行失败---文件不存在");
                    }
                } else {
                    LogCatUtil.e("摄像执行失败");
                }
            }
        });
        TextView textView = findViewById(R.id.textView);
        TextViewUtil.setTextSize(textView, TextViewUtil.TextSizeUnit.DIP, 16);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo 拍照权限检查
//                if (!checkPermission() &&
//                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                            Manifest.permission.CAMERA}, 1000);
//                    return;
//                }
//                SystemCameraCall.callSystemCamera_Picture(TestActivity.this, getDirectory(),
//                        System.currentTimeMillis() + ".png", BaseConstant.authority, mCallback, activityResultLauncher);
                // todo 摄像权限检查
                if (!checkPermission() &&
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA,
                            Manifest.permission.RECORD_AUDIO}, 1000);
                    return;
                }
                SystemCameraCall.callSystemCamera_Video(TestActivity.this, getDirectory(),
                        System.currentTimeMillis() + ".png", BaseConstant.authority, 60, 1024 * 1024 * 10L, mVideoCallback, activityResultLauncher);
            }
        });
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // todo 拍照权限检查
//            return checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
//                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
//                    checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
            // todo 摄像权限检查
            return checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    private File getDirectory() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {//Android 10 以后不再可用 Environment.getExternalStorageDirectory
            return getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        } else {
            return Environment.getExternalStorageDirectory();
        }
    }
}