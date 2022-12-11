package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.pojo.ParkingLot;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddParkingLotActivity extends AppCompatActivity {
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_parking_lot);
        db = FirebaseFirestore.getInstance();
        Button btnAddParkingLot = findViewById(R.id.btnAddParkingLot);
        btnAddParkingLot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParkingLot parkingLot = new ParkingLot();
                EditText textName = (EditText)findViewById(R.id.textName);
                EditText textAddress = (EditText)findViewById(R.id.textAddress);
                EditText textPrice = (EditText)findViewById(R.id.textPrice);
                parkingLot.setName(textName.getText().toString());
                parkingLot.setAddress(textAddress.getText().toString());
                parkingLot.setPrice(Integer.parseInt(textPrice.getText().toString()));
                parkingLot.setStatus(0);
                db.collection("ParkingLot")
                        .document()
                        .set(parkingLot)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Intent intent = new Intent(AddParkingLotActivity.this, MenuActivity.class);
                                startActivity(intent);
                            }
                        });

            }
        });
    }

}