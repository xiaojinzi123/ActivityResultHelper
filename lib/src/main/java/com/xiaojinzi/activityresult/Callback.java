package com.xiaojinzi.activityresult;

import androidx.annotation.NonNull;

public interface Callback<T> {

        /**
         * 接受一个数据
         *
         * @param t 目标数据
         */
        void accept(@NonNull T t);

    }