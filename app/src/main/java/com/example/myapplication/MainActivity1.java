package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//intent切換在切換方(ActivityMain)設計，而bundle的部分這邊會再寫東西來取得
public class MainActivity1 extends AppCompatActivity {
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page2);
        mAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        TextView txtrecord = (TextView) findViewById(R.id.txtrecord);
        if(email!=null){
            txtrecord.setText(email);
        }

        Button btreservepark = findViewById(R.id.btnReservePark);
        Button btpayment = findViewById(R.id.btnPayment);
        Button btrecord = findViewById(R.id.btnRecord);
        Button bteditInfo = findViewById(R.id.btnEditInfo);
        Button btlogout = findViewById(R.id.btnLogout);
        btreservepark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity1.this,MainActivity2.class);
                startActivity(intent);
            }
        });
        btpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity1.this,MainActivity3.class);
                startActivity(intent);
            }
        });
        btrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity1.this,MainActivity4.class);
                startActivity(intent);
            }
        });
        bteditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity1.this,MainActivity5.class);
                startActivity(intent);
            }
        });
        btlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(MainActivity1.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}