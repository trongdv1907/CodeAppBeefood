package com.Users.beefood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.Users.beefood.R;
import com.Users.beefood.model.User;

import java.util.List;

public class thongtin_UserAdapter extends RecyclerView.Adapter<thongtin_UserAdapter.MyViewHolder>{
    private List<User> UserList;
    Context context;
    public thongtin_UserAdapter(Context context, List<User> UserList) {
        this.context = context;
        this.UserList = UserList;
    }

    Toolbar toolbar;
    @NonNull
    @Override
    public thongtin_UserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_thongtin_user, parent, false);
        return new thongtin_UserAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull thongtin_UserAdapter.MyViewHolder holder, int position) {
        User Users = UserList.get(position);
        holder.tvTenND.setText(Users.getUsername());
        holder.txtphone.setText(Users.getPhone());
    }

    @Override
    public int getItemCount() {
        return UserList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvTenND,txtphone;
        public MyViewHolder(@NonNull View view) {
            super(view);
            toolbar = view.findViewById(R.id.toobar);
            tvTenND = view.findViewById(R.id.tvTenND);
            txtphone = view.findViewById(R.id.txtPhone);

        }

    }

}
