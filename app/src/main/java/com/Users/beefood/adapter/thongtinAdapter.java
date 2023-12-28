package com.Users.beefood.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Users.beefood.R;

import java.util.List;

public class thongtinAdapter extends RecyclerView.Adapter<thongtinAdapter.MyViewHolder>{
    private List<String> thongTinList;
    RecyclerView recyclerViewManHinhChinh;

    public thongtinAdapter(List<String> thongTinList) {
        this.thongTinList = thongTinList;
    }
    @NonNull
    @Override
    public thongtinAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_thongtin, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull thongtinAdapter.MyViewHolder holder, int position) {
        String thongTin = thongTinList.get(position);
        holder.txtshop.setText(thongTin);
        holder.txtproducts.setText(thongTin);
        holder.description.setText(thongTin);
        holder.linkfanpage.setText(thongTin);
        holder.address.setText(thongTin);
    }

    @Override
    public int getItemCount() {
        return thongTinList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtshop,txtproducts,description,linkfanpage,address;

        public MyViewHolder(@NonNull View view) {
            super(view);

            recyclerViewManHinhChinh = view.findViewById(R.id.recycleview);
            txtshop = view.findViewById(R.id.shop);
            txtproducts = view.findViewById(R.id.Products);
            description = view.findViewById(R.id.Description);
            linkfanpage = view.findViewById(R.id.LinkFanPage);
            address = view.findViewById(R.id.Address);

        }
    }
}
