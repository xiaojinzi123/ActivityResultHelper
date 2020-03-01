package com.xiaojinzi.activityresult.ex;

/**
 * 表示 Activity result 异常
 *
 * time   : 2018/11/03
 *
 * @author : xiaojinzi
 */
public class ActivityResultException extends RuntimeException {

    public ActivityResultException() {
    }

    public ActivityResultException(String message) {
        super(message);
    }

    public ActivityResultException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActivityResultException(Throwable cause) {
        super(cause);
    }

}
