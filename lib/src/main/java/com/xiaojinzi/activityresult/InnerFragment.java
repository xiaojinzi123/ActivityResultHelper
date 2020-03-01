package com.xiaojinzi.activityresult;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.xiaojinzi.activityresult.bean.ActivityResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InnerFragment extends Fragment {

    public static final String TAG = "ActivityResultInnerFragment";

    @NonNull
    private Map<Integer, Callback<ActivityResult>> map = new HashMap<>();


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 根据 requestCode 获取发射器
        Callback<ActivityResult> findConsumer = null;
        Integer findRequestCode = null;
        // 找出 requestCode 一样的那个
        Set<Integer> keySet = map.keySet();
        for (Integer requestCodeItem : keySet) {
            if (requestCodeItem.equals(requestCode)) {
                findRequestCode = requestCodeItem;
                break;
            }
        }
        if (findRequestCode != null) {
            findConsumer = map.get(findRequestCode);
        }
        if (findConsumer != null) {
            findConsumer.accept(new ActivityResult(requestCode, resultCode, data));
        }
        if (findRequestCode != null) {
            map.remove(findRequestCode);
        }

    }

    public void setCallback(@NonNull Integer requestCode, Callback<ActivityResult> callback) {
        Utils.nonNull(requestCode);
        Utils.nonNull(callback);
        if (map.containsKey(requestCode)) {
            throw new IllegalArgumentException("requestCode is exist!");
        }
        map.put(requestCode, callback);
    }

    /**
     * 获取一个可用的 requestCode
     *
     * @return 返回一个可用的 requestCode
     */
    @NonNull
    public Integer getRandomRequestCode() {
        Integer result = null;
        for (int i = 1; i < 256; i++) {
            if (!map.containsKey(i)) {
                result = i;
                break;
            }
        }
        return result;
    }

}
