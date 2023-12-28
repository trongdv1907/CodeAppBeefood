package com.Users.beefood.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.Users.beefood.R;
import com.Users.beefood.model.User;
import com.Users.beefood.retrofit.ApiBanHang;
import com.Users.beefood.retrofit.RetrofitClient;
import com.Users.beefood.utils.Utils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class ThongtinActivity extends AppCompatActivity {
    Toolbar toolbar;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ViewFlipper viewFlipper;
    ApiBanHang apiBanHang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtin);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        Paper.init(this);
        //ktra tk user khác null thì sẽ đc dùng cho tất cả như mua hàng...
        Anhxa();
        toolbar = findViewById(R.id.toobar1);
        ActionToolBar();

        if (isConneted(this)) {
            ActionViewFlipper();
        }else {
            Toast.makeText(getApplicationContext(), "Vui lòng kiểm tra đường truyền mạng !", Toast.LENGTH_LONG).show();
        }
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThongtinActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void Anhxa() {

        viewFlipper = findViewById(R.id.viewflipper);
        //đưa dl lên recyclerViewManHinhChinh

    }

    private void ActionViewFlipper() {
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://tuyendung.kfcvietnam.com.vn/Data/Sites/1/News/79/3.png");
        mangquangcao.add("https://tindoanhnghiep.net/uploads/images/KFC.jpg");
        mangquangcao.add("https://lienhehotro.vn//uploads/tong-dai-kfc-viet-nam.jpg");
        for (int i = 0; i<mangquangcao.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);

    }
    private boolean isConneted(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}