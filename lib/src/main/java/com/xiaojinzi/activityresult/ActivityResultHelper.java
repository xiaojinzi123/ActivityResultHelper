package com.xiaojinzi.activityresult;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.AnyThread;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.xiaojinzi.activityresult.bean.ActivityResult;
import com.xiaojinzi.activityresult.support.Action;

/**
 * 此类帮助使用者简化 ActivityResult 的获取
 */
public class ActivityResultHelper {

    /**
     * 表示 requestCode 是随机的
     */
    private final Integer RANDOM_REQUESTCODE = Integer.MIN_VALUE;

    @Nullable
    private FragmentActivity mActivity;

    @Nullable
    private Fragment mFragment;

    @Nullable
    private Intent mIntent;

    @Nullable
    private Class<? extends Activity> mActivityClass;

    @Nullable
    private Integer mRequestCode;

    @Nullable
    private Bundle mOptions;

    protected ActivityResultHelper(FragmentActivity mActivity) {
        this.mActivity = mActivity;
    }

    protected ActivityResultHelper(Fragment mFragment) {
        this.mFragment = mFragment;
    }

    /**
     * @param context 此 {@link Context} 必须是要和 {@link FragmentActivity} 有关系的
     *                比如一个 {@link Dialog} 中使用 {@link Dialog#getContext()}
     *                如果此 {@link Dialog} 是 {@link FragmentActivity} 弹出的,
     *                那么 此 {@link Context} 一定和 {@link FragmentActivity} 是有关系的.
     */
    @NonNull
    public static ActivityResultHelper with(@NonNull Context context) {
        Utils.nonNull(context);
        Activity targetAct = Utils.getActivityFromContext(context);
        if (targetAct instanceof FragmentActivity) {
            return new ActivityResultHelper((FragmentActivity) targetAct);
        } else {
            throw new IllegalArgumentException("this context is nothing to do with FragmentActivity!");
        }
    }

    @NonNull
    public static ActivityResultHelper with(@NonNull FragmentActivity fragmentActivity) {
        Utils.nonNull(fragmentActivity);
        return new ActivityResultHelper(fragmentActivity);
    }

    @NonNull
    public static ActivityResultHelper with(@NonNull Fragment frag) {
        Utils.nonNull(frag);
        return new ActivityResultHelper(frag);
    }

    @NonNull
    public ActivityResultHelper target(@NonNull Intent intent) {
        this.mIntent = intent;
        return this;
    }

    @NonNull
    public ActivityResultHelper target(@NonNull Class<? extends Activity> activityClass) {
        this.mActivityClass = activityClass;
        return this;
    }

    @NonNull
    public ActivityResultHelper requestCodeRandom() {
        this.mRequestCode = RANDOM_REQUESTCODE;
        return this;
    }

    @NonNull
    public ActivityResultHelper requestCode(@NonNull Integer requestCode) {
        this.mRequestCode = requestCode;
        return this;
    }

    @NonNull
    public ActivityResultHelper options(@NonNull Bundle options) {
        this.mOptions = options;
        return this;
    }

    public void startForIntent(@NonNull final Callback<Intent> callback) {
        startForResult(new Callback<ActivityResult>() {
            @Override
            public void accept(@NonNull ActivityResult activityResult) {
                if (activityResult.data != null) {
                    callback.accept(activityResult.data);
                }
            }
        });
    }

    public void startForIntent(@NonNull final int expectResultCode, @NonNull final Callback<Intent> callback) {
        startForResult(new Callback<ActivityResult>() {
            @Override
            public void accept(@NonNull ActivityResult activityResult) {
                if (activityResult.resultCode == expectResultCode) {
                    if (activityResult.data != null) {
                        callback.accept(activityResult.data);
                    }
                }
            }
        });
    }

    @AnyThread
    public void startForResult(@NonNull final Callback<ActivityResult> callback) {
        Utils.mainThreadAction(new Action() {
            @Override
            public void run() {
                doStartForResult(callback);
            }
        });
    }

    @MainThread
    private void doStartForResult(@NonNull final Callback<ActivityResult> callback) {
        // 检查参数
        Utils.nonNull(mRequestCode);
        Utils.nonNull(callback);
        if (mIntent == null && mActivityClass == null) {
            throw new IllegalArgumentException("target Intent or Activity class must be one!");
        }
        FragmentManager ft = null;
        if (mActivity != null) {
            ft = mActivity.getSupportFragmentManager();
        } else if (mFragment != null) {
            ft = mFragment.getFragmentManager();
        } else {
            throw new IllegalArgumentException("Activity or Fragment must be one!");
        }
        InnerFragment innerFragment = (InnerFragment) ft.findFragmentByTag(InnerFragment.TAG);
        if (innerFragment == null) {
            innerFragment = new InnerFragment();
            ft.beginTransaction()
                    .add(innerFragment, InnerFragment.TAG)
                    .commitNowAllowingStateLoss();
        }

        // 随机的 requestCode
        if (RANDOM_REQUESTCODE.equals(mRequestCode)) {
            mRequestCode = innerFragment.getRandomRequestCode();
        }

        innerFragment.setCallback(mRequestCode, new Callback<ActivityResult>() {
            @Override
            public void accept(@NonNull ActivityResult activityResult) {
                callback.accept(activityResult);
            }
        });
        if (mIntent == null) {
            mIntent = new Intent(mActivity, mActivityClass);
        }
        if (mOptions == null) {
            innerFragment.startActivityForResult(mIntent, mRequestCode);
        } else {
            innerFragment.startActivityForResult(mIntent, mRequestCode, mOptions);
        }
    }

}
