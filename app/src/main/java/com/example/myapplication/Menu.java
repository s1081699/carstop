package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

//intent切換在切換方(ActivityMain)設計，而bundle的部分這邊會再寫東西來取得
public class Menu extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        TextView txtrecord = (TextView) findViewById(R.id.txtrecord);
        if(email!=null){
            txtrecord.setText(email);
        }

        Button btnAddParkingLot = findViewById(R.id.btnAddParkingLot);
        Button btnReservePark = findViewById(R.id.btnReservePark);
        Button btnPayment = findViewById(R.id.btnPayment);
        Button btnRecord = findViewById(R.id.btnRecord);
        Button btnEditInfo = findViewById(R.id.btnEditInfo);
        Button btnLogout = findViewById(R.id.btnLogout);

        btnAddParkingLot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this,AddParkingLotActivity.class);
                startActivity(intent);
            }
        });

        btnReservePark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, RentParkingLot.class);
                startActivity(intent);
            }
        });

        btnReservePark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, RentParkingLot.class);
                startActivity(intent);
            }
        });
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this,MainActivity3.class);
                startActivity(intent);
            }
        });
        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, RentOrder.class);
                startActivity(intent);
            }
        });
        btnEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, EditProfile.class);
                startActivity(intent);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(Menu.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}