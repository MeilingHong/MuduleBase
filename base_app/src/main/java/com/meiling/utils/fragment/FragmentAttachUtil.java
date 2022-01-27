package com.meiling.utils.fragment;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * 2022-01-26 17:29
 */
public class FragmentAttachUtil {

    /**
     * 创建事务
     *
     * @param fragmentActivity 宿主
     * @return 事务
     */
    public static FragmentTransaction getFragmentTransaction(@NonNull FragmentActivity fragmentActivity) {
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        return fragmentManager.beginTransaction();
    }

    /**
     * 创建事务
     *
     * @param fragmentActivity 宿主
     * @return 事务
     */
    public static FragmentTransaction getFragmentTransaction(@NonNull Fragment fragmentActivity) {
        FragmentManager fragmentManager = fragmentActivity.getChildFragmentManager();
        return fragmentManager.beginTransaction();
    }

    /**
     * 添加Fragment
     *
     * @param fragmentActivity 宿主
     * @param fragment         需要添加的Fragment
     * @param containerId      容器
     * @param fragmentTag      标记
     * @param isAllowStateLoss 事务类型
     * @param ignoreWhenAdded  是否在已经添加过时，忽略动作
     */
    public static void attachFragmentToContainer_Add(@NonNull FragmentActivity fragmentActivity, @NonNull Fragment fragment, @IdRes int containerId, @Nullable String fragmentTag, boolean isAllowStateLoss, boolean ignoreWhenAdded) {
        FragmentTransaction fragmentTransaction = getFragmentTransaction(fragmentActivity);
        Fragment fragmentTemp = getFragmentByTag(fragmentActivity, fragmentTag);
        if (fragmentTemp != null) {
            if (ignoreWhenAdded) return;
            throw new RuntimeException("fragment tag name " + fragmentTag + " has bean added!");
        }
        fragmentTransaction.add(containerId, fragment, fragmentTag);
        if (isAllowStateLoss) {
            fragmentTransaction.commitAllowingStateLoss();
        } else {
            fragmentTransaction.commit();
        }
    }

    /**
     * 添加Fragment
     *
     * @param fragmentActivity 宿主
     * @param fragment         需要添加的Fragment
     * @param containerId      容器
     * @param fragmentTag      标记
     * @param isAllowStateLoss 事务类型
     * @param ignoreWhenAdded  是否在已经添加过时，忽略动作
     */
    public static void attachFragmentToContainer_Add(@NonNull Fragment fragmentActivity, @NonNull Fragment fragment, @IdRes int containerId, @Nullable String fragmentTag, boolean isAllowStateLoss, boolean ignoreWhenAdded) {
        FragmentTransaction fragmentTransaction = getFragmentTransaction(fragmentActivity);
        Fragment fragmentTemp = getFragmentByTag(fragmentActivity, fragmentTag);
        if (fragmentTemp != null) {
            if (ignoreWhenAdded) return;
            throw new RuntimeException("fragment tag name " + fragmentTag + " has bean added!");
        }
        fragmentTransaction.add(containerId, fragment, fragmentTag);
        if (isAllowStateLoss) {
            fragmentTransaction.commitAllowingStateLoss();
        } else {
            fragmentTransaction.commit();
        }
    }

    /**
     * 替换Fragment
     *
     * @param fragmentActivity 宿主
     * @param fragment         需要替换的Fragment
     * @param containerId      容器
     * @param fragmentTag      标记
     * @param isAllowStateLoss 事务类型
     */
    public static void attachFragmentToContainer_Replace(@NonNull FragmentActivity fragmentActivity, @NonNull Fragment fragment, @IdRes int containerId, @Nullable String fragmentTag, boolean isAllowStateLoss) {
        FragmentTransaction fragmentTransaction = getFragmentTransaction(fragmentActivity);
        fragmentTransaction.replace(containerId, fragment, fragmentTag);
        if (isAllowStateLoss) {
            fragmentTransaction.commitAllowingStateLoss();
        } else {
            fragmentTransaction.commit();
        }
    }

    /**
     * 替换Fragment
     *
     * @param fragmentActivity 宿主
     * @param fragment         需要替换的Fragment
     * @param containerId      容器
     * @param fragmentTag      标记
     * @param isAllowStateLoss 事务类型
     */
    public static void attachFragmentToContainer_Replace(@NonNull Fragment fragmentActivity, @NonNull Fragment fragment, @IdRes int containerId, @Nullable String fragmentTag, boolean isAllowStateLoss) {
        FragmentTransaction fragmentTransaction = getFragmentTransaction(fragmentActivity);
        fragmentTransaction.replace(containerId, fragment, fragmentTag);
        if (isAllowStateLoss) {
            fragmentTransaction.commitAllowingStateLoss();
        } else {
            fragmentTransaction.commit();
        }
    }

    /**
     * 获取对应的Fragment
     *
     * @param fragmentActivity 宿主
     * @param fragmentTag      标记
     * @return 该标记的Fragment；若没有添加，则为null
     */
    public static Fragment getFragmentByTag(@NonNull FragmentActivity fragmentActivity, @Nullable String fragmentTag) {
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        return fragmentManager.findFragmentByTag(fragmentTag);
    }

    /**
     * 获取对应的Fragment
     *
     * @param fragmentActivity 宿主
     * @param fragmentTag      标记
     * @return 该标记的Fragment；若没有添加，则为null
     */
    public static Fragment getFragmentByTag(@NonNull Fragment fragmentActivity, @Nullable String fragmentTag) {
        FragmentManager fragmentManager = fragmentActivity.getChildFragmentManager();
        return fragmentManager.findFragmentByTag(fragmentTag);
    }
}
