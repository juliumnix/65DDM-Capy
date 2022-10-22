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

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    List<Aluno> alunos = new ArrayList<>();
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
        MaterialButton btnLogin = view.findViewById(R.id.btn_login);
        EditText txtMsg = view.findViewById(R.id.txtMsg);
        adapter = new HomeItemRecyclerViewAdapter(alunos);

        recyclerView = view.findViewById(R.id.recycler);


            for(int i=0; i< 10; i++) {
                alunos.add(new Aluno("Usuario " + i, "Credo que delicia", i+"\n"));
            }

            Collections.reverse(alunos);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(new HomeItemRecyclerViewAdapter(alunos));
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                criarPost(txtMsg.getText().toString(), "usuario gostoso", adapter);
                    Aluno aluno = new Aluno("Capy user", txtMsg.getText().toString(), (Math.random() + "oi"));
                    alunos.add(aluno);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(new HomeItemRecyclerViewAdapter(alunos));
                    Collections.reverse(alunos);
                }
            });


        return view;
    }


}