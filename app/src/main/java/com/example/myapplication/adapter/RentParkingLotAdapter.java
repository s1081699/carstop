package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activity.MenuActivity;
import com.example.myapplication.pojo.Order;
import com.example.myapplication.pojo.ParkingLot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class RentParkingLotAdapter extends RecyclerView.Adapter<RentParkingLotAdapter.ViewHolder> {
    private List<ParkingLot> rowData;
    Context context;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public RentParkingLotAdapter(Context context, List<ParkingLot> data) {
        rowData = data;
        context = context;
    }

    // 建立ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder {
        // 宣告元件
        private TextView textName;
        private TextView textAddress;
        private TextView textPrice;
        private Button btnRent;



        ViewHolder(View itemView) {
            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.textOrderName);
            textAddress = (TextView) itemView.findViewById(R.id.textOrderAddress);
            textPrice = (TextView) itemView.findViewById(R.id.textOrderPrice);
            btnRent = (Button) itemView.findViewById(R.id.btnRent);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 連結項目布局檔list_item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rent_parking_lot_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        int price = rowData.get(position).getPrice();
        holder.textName.setText(rowData.get(position).getName());
        holder.textAddress.setText(rowData.get(position).getAddress());
        holder.textPrice.setText("月租： NT$" + price + "元");
        holder.btnRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("ParkingLot")
                        .document(rowData.get(position).getId()).update("status", 1).addOnSuccessListener(aVoid -> {
                            Order order = new Order();
                            order.setName(rowData.get(position).getName());
                            order.setAddress(rowData.get(position).getAddress());
                            order.setPrice(rowData.get(position).getPrice());
                            long tsLong = System.currentTimeMillis();
                            String time = Long.toString(tsLong);
                            order.setTimestamp(time);
                            order.setUserId(mAuth.getUid());
                            db.collection("Order").document().set(order).addOnSuccessListener(aVoid1 -> {
                                Toast.makeText(v.getContext(), "租借成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, MenuActivity.class);
                                context.startActivity(intent);
                            });
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return rowData.size();
    }
}
