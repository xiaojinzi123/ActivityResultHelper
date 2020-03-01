package com.xiaojinzi.activityresult;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.AnyThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xiaojinzi.activityresult.support.Action;

import java.util.concurrent.atomic.AtomicReference;

public class Utils {

    /**
     * 主线程的Handler
     */
    private static Handler h = new Handler(Looper.getMainLooper());

    private Utils() {
        throw new UnsupportedOperationException();
    }

    public static void nonNull(Object target) {
        if (target == null) {
            throw new NullPointerException();
        }
    }

    /**
     * 在主线程执行任务,和上面的方法唯一的区别就是一定是post过去的
     */
    public static void postActionToMainThreadAnyway(@NonNull Runnable r) {
        h.post(r);
    }

    /**
     * 是否是主线程
     */
    public static boolean isMainThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }

    @AnyThread
    @SuppressLint("WrongThread")
    public static void mainThreadAction(@NonNull final Action action) {
        if (isMainThread()) {
            action.run();
        } else {
            final AtomicReference<Object> resultAtomicReference = new AtomicReference<>();
            final AtomicReference<RuntimeException> exceptionAtomicReference = new AtomicReference<>();
            Utils.postActionToMainThreadAnyway(new Runnable() {
                @Override
                public void run() {
                    try {
                        action.run();
                        resultAtomicReference.set(0);
                    } catch (RuntimeException e) {
                        exceptionAtomicReference.set(e);
                    }
                }
            });
            // 线程空转.
            while (resultAtomicReference.get() == null && exceptionAtomicReference.get() == null) {
            }
            if (exceptionAtomicReference.get() != null) {
                throw exceptionAtomicReference.get();
            }
        }
    }

    /**
     * 从一个 {@link Context} 中获取 {@link Activity}
     * 由于 {@link Context} 有可能是 {@link ContextWrapper} 包装的,所以一直要从 {@link ContextWrapper#getBaseContext()}
     * 方法中获取 {@link Context} 并判断是否是 {@link Activity}
     *
     * @param context 上下文的参数
     * @return 如果 {@link Context} 最开始是 {@link Activity} 的话会返回一个 {@link Activity},否则返回 null
     */
    @Nullable
    public static Activity getActivityFromContext(@Nullable Context context) {
        if (context == null) {
            return null;
        }
        Activity realActivity = null;
        if (context instanceof Activity) {
            realActivity = (Activity) context;
        } else {
            // 最终结束的条件是 realContext = null 或者 realContext 不是一个 ContextWrapper
            Context realContext = context;
            while (realContext instanceof ContextWrapper) {
                realContext = ((ContextWrapper) realContext).getBaseContext();
                if (realContext instanceof Activity) {
                    realActivity = (Activity) realContext;
                    break;
                }
            }
        }
        return realActivity;
    }

}
