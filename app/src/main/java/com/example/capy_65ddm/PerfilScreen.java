package com.example.capy_65ddm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class PerfilScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_screen);

        Intent in = getIntent();
        ImageButton goBack = findViewById(R.id.goback);
        TextView nomeUsuario = findViewById(R.id.nomeUsuario);
        nomeUsuario.setText(in.getStringExtra("NomeUsuario"));
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}