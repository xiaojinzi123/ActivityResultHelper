package com.xiaojinzi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ThirdAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_act);
    }

    public void returnData(View view) {
        Intent intent = new Intent();
        intent.putExtra("data", "xiaojinzi");
        setResult(RESULT_OK, intent);
        finish();
    }

}
