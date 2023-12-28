package com.Users.beefood.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.Users.beefood.R;
import com.Users.beefood.model.GioHang;
import com.Users.beefood.model.SanPhamMoi;
import com.Users.beefood.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ChiTietActivity extends AppCompatActivity {
    TextView tensp, giasp, mota;
    Button btnthem;
    ImageView imghinhanh;
    Spinner spinner;
    Toolbar toolbar;
    SanPhamMoi sanPhamMoi;
    NotificationBadge badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);
        initView();
        ActionToolBar();
        initData();
        initControl();
    }
    private boolean daThemVaoGioHang = false;
    private void initControl() {
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!daThemVaoGioHang) {
                    themGioHang();
                    daThemVaoGioHang = true;
                } else {
                    Toast.makeText(getApplicationContext(), "Bạn đã thêm vào giỏ hàng! Xin hãy vào giỏ hàng để xem", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //    private void themGioHang() {
//        if (Utils.manggiohang.size() > 0){
//            boolean flag = false;
//            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
//            for (int i = 0; i < Utils.manggiohang.size(); i++) {
//                if (Utils.manggiohang.get(i).getIdsp() == sanPhamMoi.getId()){
//                    Utils.manggiohang.get(i).setSoluong(soluong+ Utils.manggiohang.get(i).getSoluong());
//                    long gia = Long.parseLong(sanPhamMoi.getGiasp()) * Utils.manggiohang.get(i).getSoluong();
//                    Utils.manggiohang.get(i).setGiasp(gia);
//                    flag = true;
//                }
//            }
//            if (flag == false) {
//
////            long gia = Long.parseLong(sanPhamMoi.getGiasp()) *soluong;
//                String giasp = sanPhamMoi.getGiasp();
//                giasp = giasp.replace(".", "");
//                giasp = giasp.replace(",", "");
//                giasp = giasp.trim();
//                long gia = Long.parseLong(giasp);
//
//                GioHang gioHang = new GioHang();
//                gioHang.setGiasp(gia);
//                gioHang.setSoluong(soluong);
//                gioHang.setIdsp(sanPhamMoi.getId());
//                gioHang.setTensp(sanPhamMoi.getTensp());
//                gioHang.setHinhsp(sanPhamMoi.getHinhanh());
//                Utils.manggiohang.add(gioHang);
//            }
//        } else {
//            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
//
////            long gia = Long.parseLong(sanPhamMoi.getGiasp()) *soluong;
//            String giasp = sanPhamMoi.getGiasp();
//            giasp = giasp.replace(".", "");
//            giasp = giasp.replace(",", "");
//            giasp = giasp.trim();
//            long gia = Long.parseLong(giasp);
//
//            GioHang gioHang = new GioHang();
//            gioHang.setGiasp(gia);
//            gioHang.setSoluong(soluong);
//            gioHang.setIdsp(sanPhamMoi.getId());
//            gioHang.setTensp(sanPhamMoi.getTensp());
//            gioHang.setHinhsp(sanPhamMoi.getHinhanh());
//            Utils.manggiohang.add(gioHang);
//        }
//        int totalItem = 0;
//        for (int i=0; i<Utils.manggiohang.size(); i++){
//            totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
//        }
//        badge.setText(String.valueOf(totalItem));
//    }
    private void themGioHang() {
        if (Utils.manggiohang.size() > 0){
            boolean flag = false;
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
            for (int i = 0; i < Utils.manggiohang.size(); i++) {
                if (Utils.manggiohang.get(i).getIdsp() == sanPhamMoi.getId()){
                    Utils.manggiohang.get(i).setSoluong(soluong+ Utils.manggiohang.get(i).getSoluong());
                    long gia = Long.parseLong(sanPhamMoi.getGiasp()) * Utils.manggiohang.get(i).getSoluong();
                    Utils.manggiohang.get(i).setGiasp(gia);
                    flag = true;
                }
            }
            if (flag == false) {

//            long gia = Long.parseLong(sanPhamMoi.getGiasp()) *soluong;
                String giasp = sanPhamMoi.getGiasp();
                giasp = giasp.replace(".", "");
                giasp = giasp.replace(",", "");
                giasp = giasp.trim();
                long gia = Long.parseLong(giasp);

                GioHang gioHang = new GioHang();
                gioHang.setGiasp(gia);
                gioHang.setSoluong(soluong);
                gioHang.setIdsp(sanPhamMoi.getId());
                gioHang.setTensp(sanPhamMoi.getTensp());
                gioHang.setHinhsp(sanPhamMoi.getHinhanh());
                Utils.manggiohang.add(gioHang);
            }
        } else {
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());

//            long gia = Long.parseLong(sanPhamMoi.getGiasp()) *soluong;
            String giasp = sanPhamMoi.getGiasp();
            giasp = giasp.replace(".", "");
            giasp = giasp.replace(",", "");
            giasp = giasp.trim();
            long gia = Long.parseLong(giasp);

            GioHang gioHang = new GioHang();
            gioHang.setGiasp(gia);
            gioHang.setSoluong(soluong);
            gioHang.setIdsp(sanPhamMoi.getId());
            gioHang.setTensp(sanPhamMoi.getTensp());
            gioHang.setHinhsp(sanPhamMoi.getHinhanh());
            Utils.manggiohang.add(gioHang);
        }
        int totalItem = 0;
        for (int i=0; i<Utils.manggiohang.size(); i++){
            totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
        }
        badge.setText(String.valueOf(totalItem));
    }

    private void initData() {
        sanPhamMoi = (SanPhamMoi) getIntent().getSerializableExtra("chitiet");
        tensp.setText(sanPhamMoi.getTensp());
        mota.setText(sanPhamMoi.getMota());
        Glide.with(getApplicationContext()).load(sanPhamMoi.getHinhanh()).into(imghinhanh);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        //vì giasp đg để kiểu string nên phải co double
        giasp.setText("Giá: "+decimalFormat.format(Double.parseDouble(sanPhamMoi.getGiasp()))+"Đ");
//        giasp.setText(sanPhamMoi.getGiasp());
        Integer[] so = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapterspin = new ArrayAdapter<>(this, io.paperdb.R.layout.support_simple_spinner_dropdown_item,so);
        spinner.setAdapter(adapterspin);
    }

    private void initView() {
        tensp = findViewById(R.id.txttensp);
        giasp = findViewById(R.id.txtgiasp);
        mota = findViewById(R.id.txtmotachitiet);
        btnthem = findViewById(R.id.btnthemvaogiohang);
        spinner = findViewById(R.id.spinner);
        imghinhanh = findViewById(R.id.imgchitiet);
        toolbar = findViewById(R.id.toobar);
        badge = findViewById(R.id.menu_sl);
        FrameLayout frameLayoutgiohang = findViewById(R.id.framegiohang);
        frameLayoutgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(giohang);
            }
        });
        //tăng giảm số lượng trên icon giỏ hàng
        if (Utils.manggiohang != null){
            int totalItem = 0;
            for (int i=0; i<Utils.manggiohang.size(); i++){
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
    }
    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }

    //cập nhật lại img giỏ hàng khi xóa sp
    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.manggiohang != null){
            int totalItem = 0;
            for (int i=0; i<Utils.manggiohang.size(); i++){
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
    }
}