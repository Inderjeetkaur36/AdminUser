package com.example.adminuser;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.adminuser.model.Util;
import com.example.adminuser.ui.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

        handler.sendEmptyMessageDelayed(101, 3000);

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 101) {
                if (Util.isInternetConnected(SplashActivity.this)) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SplashActivity.this, "Please Connect to Internet", Toast.LENGTH_LONG).show();
                }
            }
        }
    };
}