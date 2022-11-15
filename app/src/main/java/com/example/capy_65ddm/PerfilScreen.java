package com.example.capy_65ddm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PerfilScreen extends AppCompatActivity {

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_screen);
        db = FirebaseFirestore.getInstance();
        String[] usuarioNome = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString().split("@");

        Intent in = getIntent();
        ImageButton goBack = findViewById(R.id.goback);
        TextView nomeUsuario = findViewById(R.id.nomeUsuario);
        ImageView img_perfil = findViewById(R.id.img_perfil);
        ProgressBar loading_perfil = findViewById(R.id.loading_perfil);
        TextView numero_usuarios = findViewById(R.id.numero_usuarios);
        Button solicitar_amizade = findViewById(R.id.solicitar_amizade);




        nomeUsuario.setText(in.getStringExtra("NomeUsuario"));
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        db.collection("Usuarios").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot: task.getResult()) {
                        if (documentSnapshot.getData().get("nome").toString().equalsIgnoreCase(in.getStringExtra("NomeUsuario"))) {
                            new DownloadImage(img_perfil, loading_perfil).execute(documentSnapshot.getData().get("img").toString());
                        }
                    }
                }
            }
        });

        solicitar_amizade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("Usuarios").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                if(documentSnapshot.getData().get("nome").toString().equalsIgnoreCase(usuarioNome[0])){
                                    List<String> amigos = (List<String>) documentSnapshot.getData().get("amigos");
                                    System.out.println(amigos);
                                    if(!amigos.contains(in.getStringExtra("NomeUsuario"))){
                                        amigos.add(in.getStringExtra("NomeUsuario"));
                                        db.collection("Usuarios").document(usuarioNome[0]).update("amigos", amigos);
                                        solicitar_amizade.setText("Deixar de seguir");
                                    } else {
                                        for (String usuario: amigos){
                                            if(usuario.equalsIgnoreCase(in.getStringExtra("NomeUsuario"))){
                                                AlertDialog.Builder alert = new AlertDialog.Builder(PerfilScreen.this);
                                                alert.setTitle("Gostaria de deixar de seguir " + in.getStringExtra("NomeUsuario") + "?");
                                                alert.setMessage("Nosso amigo Capy vai ficar muito triste");
                                                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        amigos.remove(in.getStringExtra("NomeUsuario"));
                                                        db.collection("Usuarios").document(usuarioNome[0]).update("amigos", amigos);
                                                        solicitar_amizade.setText("Seguir usuário");
                                                        dialogInterface.dismiss();
                                                    }
                                                });
                                                alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        dialogInterface.dismiss();
                                                    }
                                                });
                                                alert.show();
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
            }
        });

        db.collection("Usuarios").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        if(documentSnapshot.getData().get("nome").toString().equalsIgnoreCase(usuarioNome[0])){
                            List<String> amigos = (List<String>) documentSnapshot.getData().get("amigos");
                            for (String usuario: amigos){
                                if(usuario.equalsIgnoreCase(in.getStringExtra("NomeUsuario"))){
                                    db.collection("Usuarios").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task2) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot snapshotUsuarioPagina: task2.getResult()){
                                                    if((snapshotUsuarioPagina.getData().get("nome").toString().equalsIgnoreCase(in.getStringExtra("NomeUsuario")))){
                                                        List<String> amigosUsuarioPagina = (List<String>) snapshotUsuarioPagina.getData().get("amigos");
                                                        numero_usuarios.setText(Integer.toString(amigosUsuarioPagina.size()));
                                                        solicitar_amizade.setText("Deixar de seguir");
                                                    }
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                            numero_usuarios.setText(Integer.toString(amigos.size()));
                        }


                    }
                }
            }
        });
    }
}