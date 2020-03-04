package com.xiaojinzi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.xiaojinzi.activityresult.ActivityResultHelper;
import com.xiaojinzi.activityresult.Callback;
import com.xiaojinzi.activityresult.RxActivityResultHelper;
import com.xiaojinzi.activityresult.bean.ActivityResult;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void start1(View view) {
        ActivityResultHelper.with(this)
                .target(SecondAct.class)
                // .target(new Intent(this, SecondAct.class))
                .requestCodeRandom()
                .startForIntent(RESULT_OK, new Callback<Intent>() {
                    @Override
                    public void accept(@NonNull Intent intent) {
                        Toast.makeText(MainActivity.this, "start1", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void start2(View view) {
        ActivityResultHelper.with(this)
                // .target(ThirdAct.class)
                .target(new Intent(this, ThirdAct.class))
                .requestCodeRandom()
                .startForResult(new Callback<ActivityResult>() {
                    @Override
                    public void accept(@NonNull ActivityResult activityResult) {
                        Toast.makeText(MainActivity.this, "start2", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void start3(View view) {
        RxActivityResultHelper.with(this)
                // .target(ThirdAct.class)
                .target(new Intent(this, ThirdAct.class))
                .requestCodeRandom()
                .call()
                .subscribe(new Consumer<ActivityResult>() {
                    @Override
                    public void accept(ActivityResult activityResult) throws Exception {
                        Toast.makeText(MainActivity.this, "start3", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void start_last(View view) {
        RxActivityResultHelper.with(this)
                // .target(ThirdAct.class)
                .target(new Intent(this, ThirdAct.class))
                .requestCodeRandom()
                .intentCall(RESULT_OK)
                .subscribe(new Consumer<Intent>() {
                    @Override
                    public void accept(Intent intent) throws Exception {
                        Toast.makeText(MainActivity.this, "start_last", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
