package com.Users.beefood.activity;

import static com.Users.beefood.utils.Utils.user_current;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Users.beefood.R;
import com.Users.beefood.model.User;
import com.Users.beefood.retrofit.ApiBanHang;
import com.Users.beefood.retrofit.RetrofitClient;
import com.Users.beefood.utils.Utils;

import java.text.DecimalFormat;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class thongtin_User extends AppCompatActivity {

    private TextView tvTenND,txtPhone;
    Toolbar toolbar;
    private static final int REQUEST_UPDATE_USER = 1;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    User user;
    private ImageView imgAVT;
    private CardView cvChangeInfoUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtin_user);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        initView();
        initControl();
        cvChangeInfoUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(thongtin_User.this, UpdateUser.class);
                intent.putExtra("user", user_current);
                startActivity(intent);

            }
        });

    }
    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        toolbar = findViewById(R.id.toobar1);
        tvTenND = findViewById(R.id.tvTenND);
        txtPhone = findViewById(R.id.txtPhone);
        cvChangeInfoUser = findViewById(R.id.cvChangeInfoUser);
    }
    private void initControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(thongtin_User.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        updateUserInfo();
    }
    private void updateUserInfo() {
        tvTenND.setText(Utils.user_current.getUsername());
        txtPhone.setText(Utils.user_current.getPhone());
    }
    @Override
    protected void onResume() {
        super.onResume();
        updateUserInfo();
    }

}