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
                 * ?????????????????????API 22 ???????????????5s??????????????????
                 * ????????????????????????????????????????????????
                 *
                 * todo ??????????????????????????????
                 */
                // todo ??????????????????
//                if (result != null && result.getResultCode() == RESULT_OK) {
//                    // ??????????????????
//                    if (mCallback != null &&
//                            // ????????????????????????
//                            mCallback.getCurrentFile() != null &&
//                            mCallback.getCurrentFile().exists() &&
//                            mCallback.getCurrentFile().length() > 0) {
//                        LogCatUtil.e("?????????????????????" + mCallback.getCurrentFile().getAbsolutePath());
//                    } else {
//                        LogCatUtil.e("??????????????????---???????????????");
//                    }
//                } else {
//                    LogCatUtil.e("??????????????????");
//                }
                // todo ??????????????????
                if (result != null && result.getResultCode() == RESULT_OK) {
                    // ??????????????????
                    if (mVideoCallback != null &&
                            // ????????????????????????
                            mVideoCallback.getCurrentFile() != null &&
                            mVideoCallback.getCurrentFile().exists() &&
                            mVideoCallback.getCurrentFile().length() > 0) {
                        LogCatUtil.e("?????????????????????" + mVideoCallback.getCurrentFile().getAbsolutePath());
                    } else {
                        LogCatUtil.e("??????????????????---???????????????");
                    }
                } else {
                    LogCatUtil.e("??????????????????");
                }
            }
        });
        TextView textView = findViewById(R.id.textView);
        TextViewUtil.setTextSize(textView, TextViewUtil.TextSizeUnit.DIP, 16);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo ??????????????????
//                if (!checkPermission() &&
//                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                            Manifest.permission.CAMERA}, 1000);
//                    return;
//                }
//                SystemCameraCall.callSystemCamera_Picture(TestActivity.this, getDirectory(),
//                        System.currentTimeMillis() + ".png", BaseConstant.authority, mCallback, activityResultLauncher);
                // todo ??????????????????
                if (!checkPermission() &&
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA,
                            Manifest.permission.RECORD_AUDIO}, 1000);
                    return;
                }
                SystemCameraCall.callSystemCamera_Video(TestActivity.this, getDirectory(),
                        System.currentTimeMillis() + ".mp4", BaseConstant.authority, 60, 1024 * 1024 * 10L, mVideoCallback, activityResultLauncher);
            }
        });
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // todo ??????????????????
//            return checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
//                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
//                    checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
            // todo ??????????????????
            return checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    private File getDirectory() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {//Android 10 ?????????????????? Environment.getExternalStorageDirectory
            return getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        } else {
            return Environment.getExternalStorageDirectory();
        }
    }
}