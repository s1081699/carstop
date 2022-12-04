package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.pojo.ParkingLot;

import java.util.List;
import java.util.Optional;

public class RentParkingLotAdapter extends RecyclerView.Adapter<RentParkingLotAdapter.ViewHolder> {
    private List<ParkingLot> rowData;

    RentParkingLotAdapter(List<ParkingLot> data) {
        rowData = data;
    }

    // 建立ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder{
        // 宣告元件
        private TextView textName;
        private TextView textAddress;
        private TextView textPrice;

        ViewHolder(View itemView) {
            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.textRentName);
            textAddress = (TextView) itemView.findViewById(R.id.textRentAddress);
            textPrice = (TextView) itemView.findViewById(R.id.textRentPrice);
        }
        //        btrent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(RentParkingLot.this, Menu.class);
//                startActivity(intent);
//            }
//        });
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        int price = rowData.get(position).getPrice();
        holder.textName.setText(rowData.get(position).getName());
        holder.textAddress.setText(rowData.get(position).getAddress());
        holder.textPrice.setText("月租： NT$" + price + "元");
    }

    @Override
    public int getItemCount() {
        return rowData.size();
    }
}
