package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

//intent切換在切換方(ActivityMain)設計，而bundle的部分這邊會再寫東西來取得
public class RentParkingLit extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rent_parking_lot);
        Button btrent = findViewById(R.id.btrent);
        Button btrent1 = findViewById(R.id.btrent1);
        Button btrent2 = findViewById(R.id.btrent2);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference addParkingLot = db.collection("addParkingLot");
        addParkingLot
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println(document.getId() + " => " + document.getData());
                            }
                        } else {
                            System.out.println("Error getting documents: " + task.getException());
                        }
                    }
                });

        btrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RentParkingLit.this, Menu.class);
                startActivity(intent);
            }
        });
        btrent1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RentParkingLit.this, Menu.class);
                startActivity(intent);
            }
        });
        btrent2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RentParkingLit.this, Menu.class);
                startActivity(intent);
            }
        });
    }
}