package com.Users.beefood.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.Users.beefood.R;

import io.paperdb.Paper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Paper.init(this);
        Thread thread = new Thread(){
            public void run(){
                try {
                    sleep(3000);
                } catch (Exception ex) {

                } finally {
                    //ktra đã đăng nhập hay chưa
                    //nếu chưa thì sẽ tới màn hình đăng nhập
                    if (Paper.book().read("user") == null){
                        Intent intent = new Intent(getApplicationContext(), DangnhapActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                       //rồi thì vào main
                        Intent home = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(home);
                        finish();
                    }
                }
            }
        };
        thread.start();
    }
}