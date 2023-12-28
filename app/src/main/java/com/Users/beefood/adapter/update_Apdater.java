package com.Users.beefood.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.Users.beefood.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class update_Apdater extends RecyclerView.Adapter<update_Apdater.MyViewHolder>{
    private List<String> thongTinUserList;
    Toolbar toolbar;
    @NonNull
    @Override
    public update_Apdater.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull update_Apdater.MyViewHolder holder, int position) {
        String thongTinUser = thongTinUserList.get(position);
        holder.tvTenND.setText(thongTinUser);
//        holder.txtemail.setText(thongTinUser);
        holder.txtsdt.setText(thongTinUser);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextInputEditText tvTenND,txtemail,txtsdt;
        public MyViewHolder(@NonNull View view) {
            super(view);
            toolbar = view.findViewById(R.id.toolbar);
            tvTenND = view.findViewById(R.id.tenUser);
//            txtemail = view.findViewById(R.id.emailUser);
            txtsdt =    view.findViewById(R.id.sdtUser);
        }
    }
}
