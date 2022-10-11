package com.example.capy_65ddm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.capy_65ddm.databinding.ActivityHomeScreenBinding;

public class HomeScreen extends AppCompatActivity {
    ActivityHomeScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeScreenBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        replaceFragment(new MensagensFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.mensagens:
                    replaceFragment(new MensagensFragment());
                    break;
                case R.id.usuarios:
                    replaceFragment(new UsuariosFragment());
                    break;
                case R.id.configuracoes:
                    replaceFragment(new ConfiguracoesFragment());
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