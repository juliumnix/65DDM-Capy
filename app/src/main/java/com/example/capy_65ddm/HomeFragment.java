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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    Deque<MensagemReceiverModel> mensagens = new LinkedList<>();
    private HomeItemRecyclerViewAdapter adapter;
    private FirebaseFirestore db;
    private int validadorDownload = 0;

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


    private void retornaImage(Usuario user){
        System.out.println(user.getNome());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        StringBuilder imagemURL = new StringBuilder();

        MaterialButton btnEnviar = view.findViewById(R.id.btn_enviar);
        EditText txtMsg = view.findViewById(R.id.txtMsg);


        ImageView imgUser = view.findViewById(R.id.img_user_home);
        ProgressBar loading_img_user_home = view.findViewById(R.id.loading_img_user_home);

        ImageButton like_button = view.findViewById(R.id.like_button);


        db = FirebaseFirestore.getInstance();


        adapter = new HomeItemRecyclerViewAdapter((List<MensagemReceiverModel>) mensagens, getActivity());
        recyclerView = view.findViewById(R.id.recycler);
        ProgressBar progressBar = view.findViewById(R.id.progressBarHomeFrag);


        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);



        String[] nome = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString().split("@");
        db.collection("Usuarios").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if(document.getData().get("nome").toString().equalsIgnoreCase(nome[0])){
                            new DownloadImage(imgUser, loading_img_user_home).execute(document.getData().get("img").toString());
                            imagemURL.append(document.getData().get("img").toString());
                        }
                    }
                }
            }
        });

        db.collection("Postagens").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        db.collection("Usuarios").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if(document.getData().get("nome").toString().equalsIgnoreCase(documentSnapshot.getData().get("nome").toString())){
                                            size++;
                                            MensagemReceiverModel user = new MensagemReceiverModel(documentSnapshot.getData().get("nome").toString(),
                                                    documentSnapshot.getData().get("mensagem").toString(),
                                                    documentSnapshot.getData().get("like").toString(),
                                                    documentSnapshot.getData().get("id").toString());
                                            user.setImg(document.getData().get("img").toString());
                                            mensagens.addFirst(user);
                                            recyclerView.setAdapter(new HomeItemRecyclerViewAdapter((List<MensagemReceiverModel>) mensagens, getActivity()));
                                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
                                            recyclerView.setLayoutManager(linearLayoutManager);
                                        }
                                    }
                                }
                            }
                        });
                    }
                }
                recyclerView.setVisibility(View.VISIBLE);
            }
        });

            String[] usuarioNome = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString().split("@");

            btnEnviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressBar.setVisibility(view.VISIBLE);
                    if(txtMsg.getText().toString().isEmpty()){
                        return;
                    }
                    db.collection("Usuarios").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if(document.getData().get("nome").toString().equalsIgnoreCase(usuarioNome[0])){
                                        MensagemReceiverModel usuario = new MensagemReceiverModel(usuarioNome[0], txtMsg.getText().toString(), "0", Integer.toString(size));
                                        HashMap<String, Object> postagem = new HashMap<>();
                                        postagem.put("nome", usuarioNome[0]);
                                        postagem.put("mensagem", txtMsg.getText().toString());
                                        postagem.put("id",size);
                                        postagem.put("like", 0);
                                        size++;
                                        usuario.setImg(document.getData().get("img").toString());
                                        db.collection("Postagens").document(String.valueOf(size-1)).set(postagem).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                adapter.notifyDataSetChanged();
                                                mensagens.addFirst(usuario);
                                                recyclerView.setAdapter(new HomeItemRecyclerViewAdapter((List<MensagemReceiverModel>) mensagens, getActivity()));
                                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
                                                recyclerView.setLayoutManager(linearLayoutManager);
                                                progressBar.setVisibility(view.INVISIBLE);
                                                txtMsg.setText("");
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    });
                }
            });




        progressBar.setVisibility(view.INVISIBLE);
        return view;
    }



}

