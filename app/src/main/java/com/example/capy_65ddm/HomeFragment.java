package com.example.capy_65ddm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    Stack<Usuario> usuarios = new Stack<>();
    private HomeItemRecyclerViewAdapter adapter;


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
        // Inflate the layout for this fragment
        MaterialButton btnLogin = view.findViewById(R.id.btn_enviar);
        EditText txtMsg = view.findViewById(R.id.txtMsg);
        adapter = new HomeItemRecyclerViewAdapter(usuarios);

        recyclerView = view.findViewById(R.id.recycler);


//            for(int i=0; i< 10; i++) {
//                usuarios.add(new Usuario("Usuario " + i, "Credo que delicia", i+"\n"));
//            }
//
//            Collections.reverse(usuarios);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(new HomeItemRecyclerViewAdapter(usuarios));
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Usuario usuario = new Usuario("Capy user", txtMsg.getText().toString(), usuarios.size()+1+"");
                    usuarios.add(usuario);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(new HomeItemRecyclerViewAdapter(usuarios));

                }
            });


        return view;
    }


}