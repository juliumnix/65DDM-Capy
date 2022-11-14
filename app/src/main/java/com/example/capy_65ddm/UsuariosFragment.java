package com.example.capy_65ddm;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

public class UsuariosFragment extends Fragment {
    private RecyclerView recyclerView;
    private FirebaseFirestore db;

    private ContatosItemRecycleViewAdapter adapter;
    public UsuariosFragment() {
        // Required empty public constructor
    }

    public static UsuariosFragment newInstance(String param1, String param2) {
        UsuariosFragment fragment = new UsuariosFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onPause() {
        super.onPause();
        String[] nome = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString().split("@");
        db.collection("Usuarios").document(nome[0]).update("disponivel", 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        String[] nome = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString().split("@");
        db.collection("Usuarios").document(nome[0]).update("disponivel", 1);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usuarios, container, false);
        db = FirebaseFirestore.getInstance();

        NotificationManager nMgr = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.cancelAll();

        List<Usuario> usuarios = new ArrayList<>();

        Activity activity = getActivity();

        adapter = new ContatosItemRecycleViewAdapter(usuarios, getActivity());

        recyclerView = view.findViewById(R.id.recyclerUsuarios);

        ProgressBar progressBar = view.findViewById(R.id.loadingProgressBar);
        progressBar.setVisibility(view.VISIBLE);
        recyclerView.setVisibility(view.INVISIBLE);

        db.collection("Usuarios").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        list.add(document.getId());
                        Usuario user = new Usuario(document.getData().get("nome").toString(),document.getData().get("email").toString());
                        usuarios.add(user);
                    }
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(new ContatosItemRecycleViewAdapter(usuarios, getActivity()));
                    recyclerView.setVisibility(view.VISIBLE);
                    progressBar.setVisibility(view.INVISIBLE);

                }
            }
        });



        return view;

    }
}