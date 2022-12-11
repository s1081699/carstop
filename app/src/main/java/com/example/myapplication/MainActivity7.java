package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.activity.MenuActivity;

//intent切換在切換方(ActivityMain)設計，而bundle的部分這邊會再寫東西來取得
public class MainActivity7 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page8);
        Button btpay = findViewById(R.id.btpay);
        btpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity7.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }
}