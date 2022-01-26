package com.meiling.utils.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * 2022-01-26 14:20
 */
public class FragmentLifecycleLauncher implements LifecycleObserver {

    private Fragment mComponentActivity;
    private ActivityResultLauncher<Intent> mLauncher;

    public FragmentLifecycleLauncher(@NonNull Fragment componentActivity, @NonNull ActivityResultCallback<ActivityResult> activityResultCallback) {
        mComponentActivity = componentActivity;
        mComponentActivity.getLifecycle().addObserver(this);
        mLauncher = mComponentActivity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), activityResultCallback);
    }

    /**
     * 由于启动器，每一次注册，都是创建一个新的，而页面销毁时最好又手动注销下，且一个启动器与一个页面返回相对应，所以分装起来，
     * 调用者仅需要关注需要使用的部分，减少对创建与资源释放的担忧
     *
     * @param cls    需要跳转的页面类
     * @param bundle 需要传递给跳转页面的参数
     */
    public void launchActivity(Class<?> cls, @Nullable Bundle bundle) {
        Intent intent = new Intent(mComponentActivity.getContext(), cls);
        if (bundle != null) intent.putExtras(bundle);
        mLauncher.launch(intent);
    }

    /**
     * 为了兼容某些情况下需要直接使用对应Launcher的场景，提供返回对应Launcher对象的方法
     *
     * @return 返回持有的Launcher对象
     */
    public ActivityResultLauncher<Intent> getLauncher(){
        return mLauncher;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        mLauncher.unregister();
        mLauncher = null;
        // 释放对应引用
        mComponentActivity.getLifecycle().removeObserver(this);// 删除对应回调
        mComponentActivity = null;
    }
}
