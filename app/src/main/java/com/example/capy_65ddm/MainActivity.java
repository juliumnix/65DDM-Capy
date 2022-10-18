package com.example.capy_65ddm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//

        setContentView(R.layout.activity_main);

        //funcao temporaria, aqui vai consultar o banco de dados e depois fazer a navegacao
        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {
                        Intent in = new Intent(MainActivity.this, LoginScreen.class);
                        in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(in);
                        setContentView(R.layout.activity_main);
                    }
                },
                3000);

    }
}