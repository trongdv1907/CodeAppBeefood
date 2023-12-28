package com.Users.beefood.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.Users.beefood.R;
import com.Users.beefood.retrofit.ApiBanHang;
import com.Users.beefood.retrofit.RetrofitClient;
import com.Users.beefood.utils.Utils;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ResetPassActivity extends AppCompatActivity {
    EditText email;
    AppCompatButton btnreset;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        initView();
        initControl();
    }

    private void initControl() {
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_email = email.getText().toString().trim();
                if (TextUtils.isEmpty(str_email)){
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập email!",Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    FirebaseAuth.getInstance().sendPasswordResetEmail(str_email)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "Kiểm tra Email của bạn", Toast.LENGTH_SHORT).show();
                                }
                                finish();
                            });
//                    compositeDisposable.add(apiBanHang.resetPass(str_email)
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe(
//                                    userModel -> {
//                                        if (userModel.isSuccess()){
//                                            Toast.makeText(getApplicationContext(), userModel.getMessage(),Toast.LENGTH_SHORT).show();
//                                            Intent intent = new Intent(getApplicationContext(), DangnhapActivity.class);
//                                            startActivity(intent);
//                                            finish();
//                                        } else {
//                                            Toast.makeText(getApplicationContext(), userModel.getMessage(),Toast.LENGTH_SHORT).show();
//                                        }
//                                        progressBar.setVisibility(View.VISIBLE);
//                                    },
//                                    throwable -> {
//                                        Toast.makeText(getApplicationContext(), throwable.getMessage(),Toast.LENGTH_SHORT).show();
//                                        progressBar.setVisibility(View.VISIBLE);
//                                    }
//                            ));
                }
            }
        });
    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        email = findViewById(R.id.edtresetpass);
        btnreset = findViewById(R.id.btnresetpass);
        progressBar = findViewById(R.id.progressbar);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}