package com.example.myapplication.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.RentParkingLotAdapter;
import com.example.myapplication.pojo.ParkingLot;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

//intent切換在切換方(ActivityMain)設計，而bundle的部分這邊會再寫東西來取得
public class RentParkingLotActivity extends AppCompatActivity {
    private ArrayList<ParkingLot> rowData = new ArrayList<>();
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rent_parking_lot);
        Context context = this;
        db = FirebaseFirestore.getInstance();

        // 連結元件
        RecyclerView recycler_view = (RecyclerView) findViewById(R.id.recycler_order_view);
        // 設置RecyclerView為列表型態
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        // 設置格線
        recycler_view.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        // 取得資料並設置Adapter
        getParkingLotData(context, recycler_view);
    }

    private void getParkingLotData(Context context, RecyclerView recycler_view) {
        CollectionReference parkingLotList = db.collection("ParkingLot");
        parkingLotList
                .whereEqualTo("status", 0)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ParkingLot parkingLot = new ParkingLot();
                                parkingLot.setName(Objects.requireNonNull(document.getData().get("name")).toString());
                                parkingLot.setAddress(Objects.requireNonNull(document.getData().get("address")).toString());
                                parkingLot.setPrice(Integer.parseInt(Objects.requireNonNull(document.getData().get("price")).toString()));
                                parkingLot.setStatus(Integer.parseInt(Objects.requireNonNull(document.getData().get("status")).toString()));
                                parkingLot.setId(document.getId());
                                rowData.add(parkingLot);
                            }
                            // 將資料交給adapter
                            RentParkingLotAdapter adapter = new RentParkingLotAdapter(context, rowData);
                            // 設置adapter給recycler_view
                            recycler_view.setAdapter(adapter);
                        } else {
                            System.out.println("Error getting documents: " + task.getException());
                        }
                    }
                });
    }
}