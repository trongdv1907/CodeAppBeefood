package com.Users.beefood.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.Users.beefood.R;
import com.Users.beefood.databinding.ActivityDangnhapBinding;
import com.Users.beefood.databinding.ActivityUpdateUserBinding;

import com.Users.beefood.model.User;
import com.Users.beefood.retrofit.ApiBanHang;
import com.Users.beefood.retrofit.RetrofitClient;
import com.Users.beefood.utils.Utils;
import com.github.dhaval2404.imagepicker.ImagePicker;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUser extends AppCompatActivity {
    Toolbar toolbar;
    ActivityUpdateUserBinding binding;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Button mButtonChange;
    User user;
    ApiBanHang apiBanHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateUser.this, thongtin_User.class);
                startActivity(intent);
                finish();
            }
        });
        binding.confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                suaUser();
            }
        });

        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        if (getIntent().hasExtra("user")) {
            user = (User) getIntent().getSerializableExtra("user");
            // Hiển thị dữ liệu User trên giao diện
            binding.tenUser.setText(user.getUsername());
            binding.sdtUser.setText(user.getPhone());
            binding.emailUser.setText(user.getEmail());

        } else {
            Toast.makeText(getApplicationContext(), "Không tìm thấy đối tượng User", Toast.LENGTH_LONG).show();
            finish();
        }
    }

//    private void suaUser() {
//        String str_ten = binding.tenUser.getText().toString().trim();
//        String str_sdt = binding.sdtUser.getText().toString().trim();
//
//        if (TextUtils.isEmpty(str_ten) || TextUtils.isEmpty(str_sdt)) {
//            Toast.makeText(getApplicationContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_LONG).show();
//        } else {
////            if (user != null) {
////                compositeDisposable.add(apiBanHang.updateuser(str_ten, str_sdt ,user.getId())
////                        .subscribeOn(Schedulers.io())
////                        .observeOn(AndroidSchedulers.mainThread())
////                        .subscribe(
////                                messageModel -> {
////                                    if (messageModel.isSuccess()) {
////                                        Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
////                                        Intent intent = new Intent(getApplicationContext(), thongtin_User.class);
////                                        startActivity(intent);
////                                        finish();
////                                    } else {
////                                        Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
////                                    }
////                                },
////                                throwable -> {
////                                    Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
////                                }
////                        ));
////            } else {
////                Toast.makeText(getApplicationContext(), "Đối tượng User null", Toast.LENGTH_LONG).show();
////            }
////        }
//            if (user != null) {
//                compositeDisposable.add(apiBanHang.updateuser(str_ten, str_sdt, user.getId())
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(
//                                messageModel -> {
//                                    runOnUiThread(() -> {
//                                        if (messageModel.isSuccess()) {
//                                            Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
//                                            Utils.user_current.setUsername(str_ten);
//                                            Utils.user_current.setPhone(str_sdt);
//                                            Intent intent = new Intent();
//                                            setResult(RESULT_OK, intent);
//                                            finish();
//                                        } else {
//                                            Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
//                                        }
//                                    });
//                                },
//                                throwable -> {
//                                    runOnUiThread(() -> {
//                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
//                                    });
//                                }
//                        ));
//            } else {
//                runOnUiThread(() -> {
//                    Toast.makeText(getApplicationContext(), "Đối tượng User null", Toast.LENGTH_LONG).show();
//                });
//            }
//        }
//    }
private void suaUser() {
    String str_ten = binding.tenUser.getText().toString().trim();
    String str_sdt = binding.sdtUser.getText().toString().trim();

    if (TextUtils.isEmpty(str_ten) || TextUtils.isEmpty(str_sdt)) {
        Toast.makeText(getApplicationContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_LONG).show();
    } else {
        if (user != null) {
            compositeDisposable.add(apiBanHang.updateuser(str_ten, str_sdt, user.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            messageModel -> {
                                if (messageModel.isSuccess()) {
                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                                    Utils.user_current.setUsername(str_ten);
                                    Utils.user_current.setPhone(str_sdt);
//                                    Utils.user_current.setEmail();
                                    Intent intent = new Intent();
                                    setResult(RESULT_OK, intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            },
                            throwable -> {
                                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                            }
                    ));
        } else {
            Toast.makeText(getApplicationContext(), "Đối tượng User null", Toast.LENGTH_LONG).show();
        }
    }
}


    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}