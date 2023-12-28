package com.Users.beefood.activity;

import androidx.annotation.NonNull;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.Users.beefood.R;
import com.Users.beefood.adapter.LoaiSpAdapter;
import com.Users.beefood.adapter.SanPhamMoiAdapter;
import com.Users.beefood.model.LoaiSp;
import com.Users.beefood.model.SanPhamMoi;
import com.Users.beefood.model.User;
import com.Users.beefood.retrofit.ApiBanHang;
import com.Users.beefood.retrofit.RetrofitClient;
import com.Users.beefood.utils.Utils;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
//    ViewFlipper viewFlipper;

    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView listViewManHinhChinh;
    DrawerLayout drawerLayout;
    LoaiSpAdapter loaiSpAdapter;
    List<LoaiSp> mangloaisp;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    List<SanPhamMoi> mangSpMoi;
    SanPhamMoiAdapter spAdapter;
    NotificationBadge badge;
    FrameLayout frameLayout;
    ImageView imagesearch, imageMess;
    ImageSlider imageSlider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        Paper.init(this);
        //ktra tk user khác null thì sẽ đc dùng cho tất cả như mua hàng...
        if (Paper.book().read("user") != null) {
            User user = Paper.book().read("user");
            Utils.user_current = user;
        }

        getToken();
        Anhxa();
        ActionBar();

        if (isConneted(this)) {
            ActionViewFlipper();
            getLoaiSanPham();
            getSpMoi();
            getEventClick();
        }else {
            Toast.makeText(getApplicationContext(), "Vui lòng kiểm tra đường truyền mạng !", Toast.LENGTH_LONG).show();
        }
    }
    private void getToken(){
        FirebaseMessaging.getInstance().getToken()
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if(!TextUtils.isEmpty(s)){
                            compositeDisposable.add(apiBanHang.updateToken(Utils.user_current.getId(),s)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            messageModel -> {

                                            },
                                            throwable -> {
                                                Log.d("log",throwable.getMessage());
                                            }
                                    ));
                        }
                    }
                });
        compositeDisposable.add(apiBanHang.gettoken(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if (userModel.isSuccess()){
                                com.Users.beefood.utils.Utils.ID_RECEIVED = String.valueOf(userModel.getResult().get(0).getId());
                            }
                        }, throwable -> {

                        }
                ));

    }

    private void getEventClick() {
        listViewManHinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Intent trangchu = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(trangchu);
                        break;
                    case 1:
                        //lấy điện thoại sp loại 1
                        Intent monan = new Intent(getApplicationContext(), MonAnActivity.class);
                        monan.putExtra("loai", 1);
                        startActivity(monan);
                        break;
                    case 2:
                        Intent nuoc = new Intent(getApplicationContext(), MonAnActivity.class);
                        nuoc.putExtra("loai", 2);
                        startActivity(nuoc);
                        break;
                    case 3:
                        Intent donhang = new Intent(getApplicationContext(), XemDonHangActivity.class);
                        startActivity(donhang);
                        break;
                    case 4:
                        Intent cuahang = new Intent(getApplicationContext(), ThongtinActivity.class);
                        startActivity(cuahang);
                        finish();
                        break;
                    case 5:
                        Intent User = new Intent(getApplicationContext(), thongtin_User.class);
                        startActivity(User);
                        finish();
                        break;

                    case 6:
                        // xóa key user
                        Paper.book().delete("user");
                        FirebaseAuth.getInstance().signOut();
                        Intent dangxuat = new Intent(getApplicationContext(), DangnhapActivity.class);
                        startActivity(dangxuat);
                        finish();
                        break;
                }
            }
        });
    }

    private void getSpMoi() {
        compositeDisposable.add(apiBanHang.getSpMoi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if (sanPhamMoiModel.isSuccess()){
                                mangSpMoi = sanPhamMoiModel.getResult();
                                spAdapter = new SanPhamMoiAdapter(getApplicationContext(), mangSpMoi);
                                recyclerViewManHinhChinh.setAdapter(spAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không kết nối được với server"+throwable.getMessage(),Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void getLoaiSanPham() {
        compositeDisposable.add(apiBanHang.getLoaiSp()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        loaiSpModel -> {
                            if (loaiSpModel.isSuccess()){
                                mangloaisp = loaiSpModel.getResult();
                                mangloaisp.add(new LoaiSp("Thông tin cửa hàng", "https://itservices.tdtu.edu.vn/sites/dccs/files/dccs/image/form-icon.png"));
                                mangloaisp.add(new LoaiSp("Thông tin cá nhân", "https://th.bing.com/th/id/R.6b0022312d41080436c52da571d5c697?rik=ejx13G9ZroRrcg&riu=http%3a%2f%2fpluspng.com%2fimg-png%2fuser-png-icon-young-user-icon-2400.png&ehk=NNF6zZUBr0n5i%2fx0Bh3AMRDRDrzslPXB0ANabkkPyv0%3d&risl=&pid=ImgRaw&r=0"));
                                mangloaisp.add(new LoaiSp("Đăng xuất", "https://banner2.cleanpng.com/20180218/lre/kisspng-abmeldung-button-icon-shut-cliparts-5a89cae3634e92.2358717915189798114068.jpg"));
                                loaiSpAdapter = new LoaiSpAdapter(getApplicationContext(), mangloaisp);
                                listViewManHinhChinh.setAdapter(loaiSpAdapter);
                            }
                        }
                ));
    }

//    private void ActionViewFlipper() {
//        List<String> mangquangcao = new ArrayList<>();
//        mangquangcao.add("https://static.kfcvietnam.com.vn/images/content/home/carousel/lg/GiangSinh.webp?v=gz2EQg");
//        mangquangcao.add("https://static.kfcvietnam.com.vn/images/content/home/carousel/lg/GaQueKem.webp?v=gz2EQg");
//        mangquangcao.add("https://static.kfcvietnam.com.vn/images/content/home/carousel/lg/combo-dinner.webp?v=gz2EQg");
//        for (int i = 0; i<mangquangcao.size(); i++){
//            ImageView imageView = new ImageView(getApplicationContext());
//            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            viewFlipper.addView(imageView);
//        }
//        viewFlipper.setFlipInterval(3000);
//        viewFlipper.setAutoStart(true);
//        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
//        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
//        viewFlipper.setInAnimation(slide_in);
//        viewFlipper.setOutAnimation(slide_out);
//
//    }
private void ActionViewFlipper() {
    List<SlideModel> imageList = new ArrayList<>();
    compositeDisposable.add(apiBanHang.getKhuyenmai()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    khuyenMaiModel -> {
                        if (khuyenMaiModel.isSuccess()){
                            for (int i = 0; i<khuyenMaiModel.getResult().size();i++){
                                imageList.add(new SlideModel(khuyenMaiModel.getResult().get(i).getUrl(), null));
                            }
                            imageSlider.setImageList(imageList, ScaleTypes.FIT);
                        }else {
                            Toast.makeText(getApplicationContext(),"Lỗi",Toast.LENGTH_LONG).show();
                        }
                    },
                    throwable -> {
                        Log.d("log", throwable.getMessage());
                    }
            ));



    imageSlider.setItemClickListener(new ItemClickListener() {
        @Override
        public void onItemSelected(int i) {
            Intent km = new Intent(getApplicationContext(), KhuyenMaiActivity.class);
            startActivity(km);
        }

        @Override
        public void doubleClick(int i) {

        }
    });




}

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Anhxa() {
        //Tìm kiếm
        imagesearch = findViewById( R.id.imgsearch);
        imageMess =findViewById(R.id.image_mess);
        toolbar = findViewById(R.id.toobarmanhinhchinh);
//        viewFlipper = findViewById(R.id.viewlipper);
        imageSlider = findViewById(R.id.image_slider);
        //đưa dl lên recyclerViewManHinhChinh
        recyclerViewManHinhChinh = findViewById(R.id.recycleview);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerViewManHinhChinh.setLayoutManager(layoutManager);
        recyclerViewManHinhChinh.setHasFixedSize(true);

        listViewManHinhChinh = findViewById(R.id.listviewmanhinhchinh);
        navigationView = findViewById(R.id.navigationview);
        drawerLayout = findViewById(R.id.drawerlayout);

        //giỏ hàng màn hình chính
        badge = findViewById(R.id.menu_sl);
        frameLayout = findViewById(R.id.framegiohang);

        //khởi tạo list
        mangloaisp = new ArrayList<>();
        mangSpMoi = new ArrayList<>();
        if (Utils.manggiohang == null) {
            Utils.manggiohang = new ArrayList<>();
        } else {
            int totalItem = 0;
            for (int i=0; i<Utils.manggiohang.size(); i++){
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(giohang);
            }
        });

        imagesearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
        imageMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(intent);
            }
        });
//        imageSlider.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void onItemSelected(int i) {
//                Intent km = new Intent(getApplicationContext(), KhuyenMaiActivity.class);
//                startActivity(km);
//            }
//
//            @Override
//            public void doubleClick(int i) {
//
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int totalItem = 0;
        for (int i=0; i<Utils.manggiohang.size(); i++){
            totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
        }
        badge.setText(String.valueOf(totalItem));
    }

    //kết nối server láya dữ liệu
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