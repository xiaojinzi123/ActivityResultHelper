package com.xiaojinzi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.xiaojinzi.activityresult.ActivityResultHelper;
import com.xiaojinzi.activityresult.Callback;
import com.xiaojinzi.activityresult.bean.ActivityResult;

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
                .startForResult(new Callback<ActivityResult>() {
                    @Override
                    public void accept(@NonNull ActivityResult activityResult) {
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

}
