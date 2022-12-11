package com.example.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.RentOrderAdapter;
import com.example.myapplication.adapter.RentParkingLotAdapter;
import com.example.myapplication.pojo.Order;
import com.example.myapplication.pojo.ParkingLot;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.TimeZone;

//intent切換在切換方(ActivityMain)設計，而bundle的部分這邊會再寫東西來取得
public class RentOrderActivity extends AppCompatActivity {
    private ArrayList<Order> rowData = new ArrayList<>();
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rent_order);
        mAuth = FirebaseAuth.getInstance();
        Button btback = findViewById(R.id.btback);
        db = FirebaseFirestore.getInstance();
        // 連結元件
        RecyclerView recycler_view = (RecyclerView) findViewById(R.id.recycler_order_view);
        // 設置RecyclerView為列表型態
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        // 設置格線
        recycler_view.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        // 取得訂單資料並設置Adapter
        getUserOrder(recycler_view);

        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RentOrderActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getUserOrder (RecyclerView recycler_view) {
        CollectionReference parkingLotList = db.collection("Order");
        parkingLotList
                .whereEqualTo("userId", Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Order order = new Order();
                                order.setName(Objects.requireNonNull(document.getData().get("name")).toString());
                                order.setAddress(Objects.requireNonNull(document.getData().get("address")).toString());
                                order.setPrice(Integer.parseInt(Objects.requireNonNull(document.getData().get("price")).toString()));
                                String time = Objects.requireNonNull(document.getData().get("timestamp")).toString();
                                long timeL = Long.parseLong(time);
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeL),
                                            TimeZone.getDefault().toZoneId());
                                    String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm"));
                                    order.setTimestamp(format);
                                }
                                rowData.add(order);
                            }
                            // 將資料交給adapter
                            RentOrderAdapter adapter = new RentOrderAdapter(context, rowData);
                            // 設置adapter給recycler_view
                            recycler_view.setAdapter(adapter);
                        } else {
                            System.out.println("Error getting documents: " + task.getException());
                        }
                    }
                });
    }
}