package com.example.capy_65ddm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class UsuariosFragment extends Fragment {
    private RecyclerView recyclerView;
    List<Usuario> usuarios = new ArrayList<>();
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usuarios, container, false);

        adapter = new ContatosItemRecycleViewAdapter(usuarios);

        recyclerView = view.findViewById(R.id.recyclerUsuarios);

        for(int i=0; i< 10; i++) {
            usuarios.add(new Usuario("Usuario " + i, "Credo que delicia", i+"\n"));
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new ContatosItemRecycleViewAdapter(usuarios));

        return view;

    }
}