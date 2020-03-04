package com.xiaojinzi.activityresult;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.xiaojinzi.activityresult.bean.ActivityResult;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

/**
 * 拓展对 RxJava 的支持
 */
public class RxActivityResultHelper extends ActivityResultHelper {

    protected RxActivityResultHelper(FragmentActivity mActivity) {
        super(mActivity);
    }

    protected RxActivityResultHelper(Fragment mFragment) {
        super(mFragment);
    }

    @NonNull
    public static RxActivityResultHelper with(@NonNull Context context) {
        Utils.nonNull(context);
        Activity targetAct = Utils.getActivityFromContext(context);
        if (targetAct instanceof FragmentActivity) {
            return new RxActivityResultHelper((FragmentActivity) targetAct);
        } else {
            throw new IllegalArgumentException("this context is nothing to do with FragmentActivity!");
        }
    }

    @NonNull
    public static RxActivityResultHelper with(@NonNull FragmentActivity fragmentActivity) {
        Utils.nonNull(fragmentActivity);
        return new RxActivityResultHelper(fragmentActivity);
    }

    @NonNull
    public static RxActivityResultHelper with(@NonNull Fragment frag) {
        Utils.nonNull(frag);
        return new RxActivityResultHelper(frag);
    }

    @NonNull
    @Override
    public RxActivityResultHelper target(@NonNull Intent intent) {
        super.target(intent);
        return this;
    }

    @NonNull
    @Override
    public RxActivityResultHelper target(@NonNull Class<? extends Activity> activityClass) {
        super.target(activityClass);
        return this;
    }

    @NonNull
    @Override
    public RxActivityResultHelper requestCodeRandom() {
        super.requestCodeRandom();
        return this;
    }

    @NonNull
    @Override
    public RxActivityResultHelper requestCode(@NonNull Integer requestCode) {
        super.requestCode(requestCode);
        return this;
    }

    @NonNull
    @Override
    public RxActivityResultHelper options(@NonNull Bundle options) {
        super.options(options);
        return this;
    }

    public Single<Intent> intentCall() {
        return Single.create(new SingleOnSubscribe<Intent>() {
            @Override
            public void subscribe(final SingleEmitter<Intent> emitter) throws Exception {
                if (!emitter.isDisposed()) {
                    startForIntent(new Callback<Intent>() {
                        @Override
                        public void accept(@NonNull Intent intent) {
                            if (!emitter.isDisposed()) {
                                emitter.onSuccess(intent);
                            }
                        }
                    });
                }
            }
        });
    }

    public Single<Intent> intentCall(final int expectResultCode) {
        return Single.create(new SingleOnSubscribe<Intent>() {
            @Override
            public void subscribe(final SingleEmitter<Intent> emitter) throws Exception {
                if (!emitter.isDisposed()) {
                    startForIntent(expectResultCode, new Callback<Intent>() {
                        @Override
                        public void accept(@NonNull Intent intent) {
                            if (!emitter.isDisposed()) {
                                emitter.onSuccess(intent);
                            }
                        }
                    });
                }
            }
        });
    }

    public Single<ActivityResult> call() {
        return Single.create(new SingleOnSubscribe<ActivityResult>() {
            @Override
            public void subscribe(final SingleEmitter<ActivityResult> emitter) throws Exception {
                if (!emitter.isDisposed()) {
                    startForResult(new Callback<ActivityResult>() {
                        @Override
                        public void accept(@NonNull ActivityResult activityResult) {
                            if (!emitter.isDisposed()) {
                                emitter.onSuccess(activityResult);
                            }
                        }
                    });
                }
            }
        });
    }

}
