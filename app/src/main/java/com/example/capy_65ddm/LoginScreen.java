package com.example.capy_65ddm;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

//import com.example.capy_65ddm.databinding.ActivityLoginScreenBinding;

public class LoginScreen extends AppCompatActivity {

    private boolean isVisible = false;

    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_login_screen);

        ImageView btn_eye =findViewById(R.id.btn_eye);
        EditText edt_senha = findViewById(R.id.edt_senha);

        btn_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isVisible == false) {
                    btn_eye.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                    edt_senha.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    setVisible(true);
                    edt_senha.setSelection(edt_senha.length());
                } else{
                    btn_eye.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);
                    edt_senha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    edt_senha.setSelection(edt_senha.length());
                    setVisible(false);
                }
            }
        });

    }
}