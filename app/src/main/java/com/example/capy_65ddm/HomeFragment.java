package com.example.capy_65ddm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    Deque<Usuario> usuarios = new LinkedList<>();
    private HomeItemRecyclerViewAdapter adapter;
    private FirebaseFirestore db;

    int size = 0;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        MaterialButton btnEnviar = view.findViewById(R.id.btn_enviar);
        EditText txtMsg = view.findViewById(R.id.txtMsg);


        ImageView imgUser = view.findViewById(R.id.img_user_home_1);
        ProgressBar loading_img_user_home = view.findViewById(R.id.loading_img_user_home);


        db = FirebaseFirestore.getInstance();


        adapter = new HomeItemRecyclerViewAdapter((List<Usuario>) usuarios);
        recyclerView = view.findViewById(R.id.recycler);
        ProgressBar progressBar = view.findViewById(R.id.progressBarHomeFrag);


        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        db.collection("Postagens").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){

                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        size++;
                        Usuario user = new Usuario(documentSnapshot.getData().get("nome").toString(), documentSnapshot.getData().get("mensagem").toString());
                        usuarios.addFirst(user);
                        recyclerView.setAdapter(new HomeItemRecyclerViewAdapter((List<Usuario>) usuarios));
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
                        recyclerView.setLayoutManager(linearLayoutManager);

                    }
                }
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
        String[] nome = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString().split("@");
        db.collection("Usuarios").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if(document.getData().get("nome").toString().equalsIgnoreCase(nome[0])){
                            new DownloadImage(imgUser).execute(document.getData().get("img").toString());
                        }

                    }
                }
            }
        });



            String[] usuarioNome = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString().split("@");

            btnEnviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressBar.setVisibility(view.VISIBLE);
                    Usuario usuario = new Usuario(usuarioNome[0], txtMsg.getText().toString());
                    HashMap<String, Object> postagem = new HashMap<>();
                    postagem.put("nome", usuarioNome[0]);
                    postagem.put("mensagem", txtMsg.getText().toString());
                    postagem.put("id",size);
                    size++;
                    db.collection("Postagens").document(String.valueOf(size-1)).set(postagem).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            adapter.notifyDataSetChanged();
                            usuarios.addFirst(usuario);
                            recyclerView.setAdapter(new HomeItemRecyclerViewAdapter((List<Usuario>) usuarios));
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
                            recyclerView.setLayoutManager(linearLayoutManager);
                            progressBar.setVisibility(view.INVISIBLE);
                        }
                    });

                }
            });

        progressBar.setVisibility(view.INVISIBLE);
        return view;
    }


}

