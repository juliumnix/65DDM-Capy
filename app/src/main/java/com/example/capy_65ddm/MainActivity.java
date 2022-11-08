package com.example.capy_65ddm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null){
            Intent in = new Intent(MainActivity.this, LoginScreen.class);
            in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(in);
            setContentView(R.layout.activity_main);
            finish();
        } else {
            Intent in = new Intent(MainActivity.this, HomeScreen.class);
            in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(in);
            setContentView(R.layout.activity_main);
            finish();
        }

        //funcao temporaria, aqui vai consultar o banco de dados e depois fazer a navegacao
//        new android.os.Handler(Looper.getMainLooper()).postDelayed(
//                new Runnable() {
//                    public void run() {
//                        Intent in = new Intent(MainActivity.this, LoginScreen.class);
//                        in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                        startActivity(in);
//                        setContentView(R.layout.activity_main);
//                        finish();
//                    }
//                },
//                3000);

    }
}