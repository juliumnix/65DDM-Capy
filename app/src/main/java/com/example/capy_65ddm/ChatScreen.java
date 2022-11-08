package com.example.capy_65ddm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewChat);

        List<Usuario> usuarios = new ArrayList<>();
        for(int i=0; i< 10; i++) {
            usuarios.add(new Usuario("Usuario " + i, "Credo que delicia", i+"\n"));
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition(usuarios.size() - 1);
        recyclerView.setAdapter(new ChatItemRecycleViewAdapter(usuarios));


        Intent in = getIntent();
        TextView nomeUsuario = findViewById(R.id.nomeUsuarioChat);
        nomeUsuario.setText(in.getStringExtra("NomeUsuario"));

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView campoTexto = findViewById(R.id.mensagemTxt);
        ImageButton sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuarios.add(new Usuario("Teste", campoTexto.getText().toString(), usuarios.size()+1+""));
                campoTexto.setText("");
                recyclerView.setAdapter(new ChatItemRecycleViewAdapter(usuarios));
                recyclerView.scrollToPosition(usuarios.size() - 1);
            }
        });


    }
}