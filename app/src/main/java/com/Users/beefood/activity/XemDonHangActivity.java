package com.Users.beefood.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.Users.beefood.Interface.ItemClickDeleteListener;
import com.Users.beefood.R;
import com.Users.beefood.adapter.DonHangAdapter;
import com.Users.beefood.model.NotisendData;
import com.Users.beefood.retrofit.ApiBanHang;
import com.Users.beefood.retrofit.ApiPushNotification;
import com.Users.beefood.retrofit.RetrofitClient;
import com.Users.beefood.retrofit.RetrofitClientNoti;
import com.Users.beefood.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class  XemDonHangActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    RecyclerView redonhang;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_don_hang);
        initView();
        initToolbar();
        getOrder();
    }

    private void getOrder() {
        compositeDisposable.add(apiBanHang.xemDonHang(Utils.user_current.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        donHangModel -> {
                            DonHangAdapter adapter = new DonHangAdapter(getApplicationContext(), donHangModel.getResult(), new ItemClickDeleteListener() {
                                @Override
                                public void onClickDelete(int iddonhang) {
                                    showDeleteOrder(iddonhang);
                                }
                            });
                            redonhang.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        },
                        throwable -> {

                        }
                ));
    }

    private void showDeleteOrder(int iddonhang) {
        PopupMenu popupMenu = new PopupMenu(this,redonhang.findViewById(R.id.trangthaidonhang));
        popupMenu.inflate(R.menu.menu_delete);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.deleteOrder) {
                    deleteOrder(iddonhang);

                }
                return false;
            }
        });
        popupMenu.show();   
    }

    private void deleteOrder(int iddonhang) {
        compositeDisposable.add(apiBanHang.deleteOrder(iddonhang)
           .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            messageModel -> {
                                if (messageModel.isSuccess()){
                                    getOrder();
                                }

                            },
                            throwable -> {
                                Log.d("loggg",throwable.getMessage());
                            }
                    ));
        }

    private void initToolbar() {
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
//    private void pushNotiToUser() {
//        compositeDisposable.add(apiBanHang.gettoken(1)//,donHang.getIduser()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        userModel ->{
//                            if (userModel.isSuccess()){
//                                for(int i = 0; i < userModel.getResult().size(); i++){
////                                 String token = "cbbD9IipQl2EQZ44AgLaS5:APA91bHPniz_q8tNXqsQ3hNK5Br5gEZzbx3zr0e1gMOrzetWHq326nqGtEDY3nIbRpfTKUrlUT6nHCyx_WSWLUitL8aIqz-s7Wj8N1CpWGk05TvvLtjHYNoSdgBfWjtu1lONgvRuEbg-";
//                                    Map<String, String> data = new HashMap<>();
//                                    data.put("title","Thông báo");
//                                    data.put("body","Bạn có đơn hàng mới!!!");
//                                    NotisendData notisendData = new NotisendData(userModel.getResult().get(i).getToken(),data);
//                                    ApiPushNotification apiPushNofication = RetrofitClientNoti.getInstance().create(ApiPushNotification.class);
//                                    compositeDisposable.add(apiPushNofication.sendNotification(notisendData)
//                                            .subscribeOn(Schedulers.io())
//                                            .observeOn(AndroidSchedulers.mainThread())
//                                            .subscribe(
//                                                    NotiResponse ->{
//
//                                                    },
//                                                    throwable ->{
//                                                        Log.d("logg",throwable.getMessage());
//                                                    }
//                                            ));
//                                }
//                            }
//
//                        },
//                        throwable ->{
//                            Log.d("logg",throwable.getMessage());
//                        }
//                ));
//
//
//    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        redonhang = findViewById(R.id.recycleview_donhang);
        toolbar = findViewById(R.id.toobar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        redonhang.setLayoutManager(layoutManager);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}

