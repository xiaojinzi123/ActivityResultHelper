package com.xiaojinzi.activityresult;


import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.AnyThread;
import androidx.annotation.NonNull;

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

}
