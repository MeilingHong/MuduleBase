package com.meiling.utils.activity;

import android.content.Intent;

import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

/**
 * 2022-01-11 11:48
 *
 * @author marisareimu
 */
public class ActivityUtil {
    /**
     * 针对API29以上版本，startActivityForResult方法被废弃，构建的启动类（能够获取到返回值），需要将
     * androidx支持包升级到1.3.1以上【implementation 'androidx.appcompat:appcompat:1.3.1'】
     *
     * PS:需要保证该方法的调用在Activity onStart 方法前进行调用，否则将会抛出异常
     *
     * @param mContext 启动源【发起者】
     * @param activityResultCallback 获取
     * @return 启动对象，用于启动页面跳转
     */
    public static ActivityResultLauncher<Intent> getActivityLauncher(@NonNull ComponentActivity mContext, @NonNull ActivityResultCallback<ActivityResult> activityResultCallback) {
        /*
         * 异常：java.lang.IllegalStateException: LifecycleOwner com.meiling.app.TestActivity@38320d3f is attempting to register while current state is RESUMED. LifecycleOwners must call register before they are STARTED.
         * registerForActivityResult 调用需要在 onStart之前，否则将会报错，不能够在
         */
        return mContext.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), activityResultCallback);
    }
}
