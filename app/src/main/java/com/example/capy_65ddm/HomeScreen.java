package com.example.capy_65ddm;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.example.capy_65ddm.databinding.ActivityHomeScreenBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.inappmessaging.FirebaseInAppMessaging;

import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity {
    ActivityHomeScreenBinding binding;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeScreenBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    if(savedInstanceState == null){
                        System.out.println(FirebaseInAppMessaging.getInstance().areMessagesSuppressed());
                        replaceFragment(new HomeFragment());
                    }
                    break;
                case R.id.usuarios:
                    if(savedInstanceState == null){
                        System.out.println(FirebaseInAppMessaging.getInstance().areMessagesSuppressed());
                        replaceFragment(new UsuariosFragment());
                    }
                    break;
                case R.id.configuracoes:
                    if(savedInstanceState == null){
                        System.out.println(FirebaseInAppMessaging.getInstance().areMessagesSuppressed());
                        FirebaseInAppMessaging.getInstance().setMessagesSuppressed(true);
                        replaceFragment(new ConfiguracoesFragment());
                    }
                    break;
            }
            return true;
        });


    }


    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}