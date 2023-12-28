package com.Users.beefood.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.Users.beefood.R;
import com.Users.beefood.model.NotiResponse;
import com.Users.beefood.model.NotisendData;
import com.Users.beefood.retrofit.ApiBanHang;

import com.Users.beefood.retrofit.ApiPushNotification;
import com.Users.beefood.retrofit.RetrofitClient;
import com.Users.beefood.retrofit.RetrofitClientNoti;
import com.Users.beefood.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import vn.momo.momo_partner.AppMoMoLib;
//import vn.momo.momo_partner.AppMoMoLib;

public class ThanhToanActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txttongtien, txtphone, txtemail;
    EditText edtdiachi;
    AppCompatButton btndathang,btnmomo;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    long tongtien;
    int totalItem;
    private String amount = "10000";
    private String fee = "0";
    int environment = 0;//developer default
    private String merchantName = "Thanh toán đơn hàng";
    private String merchantCode = "SCB01";
    private String merchantNameLabel = "HoQuang";
    private String description = "Mua hàng online";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT);
        initView();
        countItem();
        initControl();
    }

    private void countItem() {
        totalItem = 0;
        for (int i=0; i<Utils.mangmuahang.size(); i++){
            totalItem = totalItem + Utils.mangmuahang.get(i).getSoluong();
        }
    }

    //Get token through MoMo app
    private void requestPayment(String iddonhang) {
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);
//        if (edAmount.getText().toString() != null && edAmount.getText().toString().trim().length() != 0)
//            amount = edAmount.getText().toString().trim();

        Map<String, Object> eventValue = new HashMap<>();
        //client Required
        eventValue.put("merchantname", merchantName); //Tên đối tác. được đăng ký tại https://business.momo.vn. VD: Google, Apple, Tiki , CGV Cinemas
        eventValue.put("merchantcode", merchantCode); //Mã đối tác, được cung cấp bởi MoMo tại https://business.momo.vn
        eventValue.put("amount", amount); //Kiểu integer
        eventValue.put("orderId", iddonhang); //uniqueue id cho Bill order, giá trị duy nhất cho mỗi đơn hàng
        eventValue.put("orderLabel", iddonhang); //gán nhãn

        //client Optional - bill info
        eventValue.put("merchantnamelabel", "Dịch vụ");//gán nhãn
        eventValue.put("fee", "0"); //Kiểu integer
        eventValue.put("description", description); //mô tả đơn hàng - short description

        //client extra data
        eventValue.put("requestId",  merchantCode+"merchant_billId_"+System.currentTimeMillis());
        eventValue.put("partnerCode", merchantCode);
        //Example extra data
        JSONObject objExtraData = new JSONObject();
        try {
            objExtraData.put("site_code", "008");
            objExtraData.put("site_name", "CGV Cresent Mall");
            objExtraData.put("screen_code", 0);
            objExtraData.put("screen_name", "Special");
            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3");
            objExtraData.put("movie_format", "2D");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        eventValue.put("extraData", objExtraData.toString());

        eventValue.put("extra", "");
        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);


    }
    //Get token callback from MoMo app an submit to server side
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if(data != null) {
                if(data.getIntExtra("status", -1) == 0) {
                    //TOKEN IS AVAILABLE
                    Log.d("Thanh cong",data.getStringExtra("message"));
//                    tvMessage.setText("message: " + "Get token " + data.getStringExtra("message"));
                    String token = data.getStringExtra("data"); //Token response
                    String phoneNumber = data.getStringExtra("phonenumber");
                    String env = data.getStringExtra("env");
                    if(env == null){
                        env = "app";
                    }

                    if(token != null && !token.equals("")) {
                        // TODO: send phoneNumber & token to your server side to process payment with MoMo server
                        // IF Momo topup success, continue to process your order
                    } else {
                        Log.d("Thanh cong","Khong thanh cong");
                    }
                } else if(data.getIntExtra("status", -1) == 1) {
                    //TOKEN FAIL
                    String message = data.getStringExtra("message") != null?data.getStringExtra("message"):"Thất bại";
                    Log.d("Thanh cong","Khong thanh cong");
                } else if(data.getIntExtra("status", -1) == 2) {
                    //TOKEN FAIL
                    Log.d("Thanh cong","Khong thanh cong");
                } else {
                    //TOKEN FAIL
                    Log.d("Thanh cong","Khong thanh cong");
                }
            } else {
                Log.d("Thanh cong","Khong thanh cong");
            }
        } else {
            Log.d("Thanh cong","Khong thanh cong");
        }
    }

    private void initControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //get tổng tiền tự giỏ hàng
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtien = getIntent().getLongExtra("tongtien",0);
        txttongtien.setText( decimalFormat.format(tongtien));
        txtemail.setText(Utils.user_current.getEmail());
        txtphone.setText(Utils.user_current.getPhone());

        btndathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_diachi = edtdiachi.getText().toString().trim();
                if (TextUtils.isEmpty(str_diachi)){
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập địa chỉ giao hàng", Toast.LENGTH_SHORT).show();
                } else {
                    //post data
                    String str_email = Utils.user_current.getEmail();
                    String str_sdt = Utils.user_current.getPhone();
                    int id = Utils.user_current.getId();
                    Log.d("test", new Gson().toJson(Utils.mangmuahang));
                    compositeDisposable.add(apiBanHang.createOder(str_email, str_sdt, String.valueOf(tongtien), id, str_diachi, String.valueOf(totalItem), new Gson().toJson(Utils.mangmuahang))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    userModel -> {
                                        pushNotiToUser();
                                        
                                        Toast.makeText(getApplicationContext(), "Thành công !", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), XemDonHangActivity.class);
                                        startActivity(intent);
                                        finish();
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }
            }
        });
        btnmomo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_diachi = edtdiachi.getText().toString().trim();
                if (TextUtils.isEmpty(str_diachi)){
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập địa chỉ giao hàng", Toast.LENGTH_SHORT).show();
                } else {
                    //post data
                    String str_email = Utils.user_current.getEmail();
                    String str_sdt = Utils.user_current.getPhone();
                    int id = Utils.user_current.getId();
                    Log.d("test", new Gson().toJson(Utils.mangmuahang));
                    compositeDisposable.add(apiBanHang.createOder(str_email, str_sdt, String.valueOf(tongtien), id, str_diachi, String.valueOf(totalItem), new Gson().toJson(Utils.mangmuahang))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    userModel -> {
                                        pushNotiToUser();

                                        Toast.makeText(getApplicationContext(), "Thành công !", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), XemDonHangActivity.class);
                                        startActivity(intent);
                                        finish();
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }

            }
        });
    }


    private void pushNotiToUser() {
        compositeDisposable.add(apiBanHang.gettoken(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel ->{
                            if (userModel.isSuccess()){
                             for(int i = 0; i < userModel.getResult().size(); i++){
//                                 String token = "cbbD9IipQl2EQZ44AgLaS5:APA91bHPniz_q8tNXqsQ3hNK5Br5gEZzbx3zr0e1gMOrzetWHq326nqGtEDY3nIbRpfTKUrlUT6nHCyx_WSWLUitL8aIqz-s7Wj8N1CpWGk05TvvLtjHYNoSdgBfWjtu1lONgvRuEbg-";
                                 Map<String, String> data = new HashMap<>();
                                 data.put("title","Thong bao");
                                 data.put("Body","Ban co don hang moi");
                                 NotisendData notisendData = new NotisendData(userModel.getResult().get(i).getToken(),data);
                                 ApiPushNotification apiPushNofication = RetrofitClientNoti.getInstance().create(ApiPushNotification.class);
                                 compositeDisposable.add(apiPushNofication.sendNotification(notisendData)
                                         .subscribeOn(Schedulers.io())
                                         .observeOn(AndroidSchedulers.mainThread())
                                         .subscribe(
                                                 NotiResponse ->{

                                                 },
                                                 throwable ->{
                                                     Log.d("logg",throwable.getMessage());
                                                 }
                                         ));
                                }
                            }

                        },
                        throwable ->{
                            Log.d("logg",throwable.getMessage());
                        }
                ));


    }
//private void pushNotiToUser() {
//    String token = "eWYfn7CDSY2aziBb803FTu:APA91bEfYdr97roGyHeYF6KpD5CoPN-aW7iCX1lmXovk0lJW6rvSjlwhcj6XevAqiB-2mxv6BQ-w5eTGUXSDHQDUbe9elL1U4w2brt3kk3ysAgqT_hcnCWk3BUuMjJuK3BG_OLKHeAgD";
//    Map<String, String> data = new HashMap<>();
//    data.put("title", "Thong bao");
//    data.put("body", "Ban co don hang moi");
//    NotisendData notisendData = new NotisendData(token,data);
//    ApiPushNotification apiPushNofication = RetrofitClientNoti.getInstance().create(ApiPushNotification.class);
//    compositeDisposable.add(apiPushNofication.sendNotification(notisendData)
//    .subscribeOn(Schedulers.io())
//    .observeOn(AndroidSchedulers.mainThread())
//    .subscribe(
//     NotiResponse ->{
//
//     },
//     throwable ->{
//     Log.d("logg",throwable.getMessage());
//     }
//    ));
//}
    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        toolbar = findViewById(R.id.toobar);
        txttongtien = findViewById(R.id.txttongtienmua);
        txtphone = findViewById(R.id.txtphone);
        txtemail = findViewById(R.id.txtemail);
        edtdiachi = findViewById(R.id.edtdiachi);
        btndathang = findViewById(R.id.btndathang);
        btnmomo = findViewById(R.id.btnmomo);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}