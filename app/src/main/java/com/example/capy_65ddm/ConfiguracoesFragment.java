package com.example.capy_65ddm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfiguracoesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfiguracoesFragment extends Fragment {

    private FirebaseFirestore db;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseAuth mAuth;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ConfiguracoesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfiguracoesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfiguracoesFragment newInstance(String param1, String param2) {
        ConfiguracoesFragment fragment = new ConfiguracoesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configuracoes, container, false);
        Button btnSair = view.findViewById(R.id.btn_sair_logout);
        TextView nomeUsuarioConfig = view.findViewById(R.id.nomeUsuarioConfig);
        ImageView imgUser = view.findViewById(R.id.imgUserConfiguracao);
        ProgressBar loading = view.findViewById(R.id.loading_img_config);
        EditText edit_url_config = view.findViewById(R.id.edit_url_config);
        TextView amigos_tela_config = view.findViewById(R.id.amigos_tela_config);
        Button btn_editar = view.findViewById(R.id.btn_editar);
        String[] nome = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString().split("@");


        nomeUsuarioConfig.setText("CAPYBARA " + nome[0].toUpperCase());
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent in = new Intent(view.getContext(), LoginScreen.class);
                startActivity(in);
                getActivity().finish();

            }
        });

        db.collection("Usuarios").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        if(documentSnapshot.getData().get("nome").toString().equalsIgnoreCase(nome[0])){
                            new DownloadImage(imgUser, loading).execute(documentSnapshot.getData().get("img").toString());
                            List<String> amigos = (List<String>) documentSnapshot.getData().get("amigos");
                            amigos_tela_config.setText(Integer.toString(amigos.size()));
                            edit_url_config.setHint(documentSnapshot.getData().get("img").toString());
                        }
                    }
                }
            }
        });

        btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edit_url_config.getText().toString().isEmpty()){
                    return;
                }
                db.collection("Usuarios").document(nome[0]).update("img", edit_url_config.getText().toString());
                loading.setVisibility(View.VISIBLE);
                new DownloadImage(imgUser, loading).execute(edit_url_config.getText().toString());
                edit_url_config.setText("");
                edit_url_config.setHint(edit_url_config.getText().toString());
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}