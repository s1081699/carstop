package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
//intent切換在切換方(ActivityMain)設計，而bundle的部分這邊會再寫東西來取得
public class MainActivity2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page3);
        Button btrent = findViewById(R.id.btrent);
        Button btrent1 = findViewById(R.id.btrent1);
        Button btrent2 = findViewById(R.id.btrent2);
        btrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this,MainActivity1.class);
                startActivity(intent);
            }
        });
        btrent1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this,MainActivity1.class);
                startActivity(intent);
            }
        });
        btrent2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this,MainActivity1.class);
                startActivity(intent);
            }
        });
    }
}