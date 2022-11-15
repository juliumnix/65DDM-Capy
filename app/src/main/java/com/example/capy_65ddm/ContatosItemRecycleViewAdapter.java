package com.example.capy_65ddm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public class ContatosItemRecycleViewAdapter extends RecyclerView.Adapter<ContatosItemRecycleViewAdapter.ViewHolder>{

    private List<Usuario> usuarios;
    private FirebaseFirestore db;
    private Boolean isReceiverAvailable = false;
    private Activity activity;

    public ContatosItemRecycleViewAdapter(List<Usuario> usuarios, Activity activity) {
        this.usuarios = usuarios;
        this.activity = activity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNome;
        ImageView imagem;
        View onlineIndicator;
        ConstraintLayout layout;
        ProgressBar progressBar;
        public ViewHolder(View itemView){
            super(itemView);
            txtNome = itemView.findViewById(R.id.txtNome);
            layout = itemView.findViewById(R.id.layoutContato);
            imagem = itemView.findViewById(R.id.img_capy_contato);
            onlineIndicator = itemView.findViewById(R.id.online_indicator_usuario);
            progressBar = itemView.findViewById(R.id.loading_img_contato);
        }
    }



    @NonNull
    @Override
    public ContatosItemRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_adapter_contato, parent, false);

        ContatosItemRecycleViewAdapter.ViewHolder viewHolder = new ContatosItemRecycleViewAdapter.ViewHolder(itemView);
        db = FirebaseFirestore.getInstance();

        return viewHolder;
    }

    private void listenAvailabilityOfReceiver(View view, String nomeUsuario){
        db.collection("Usuarios").document(nomeUsuario).addSnapshotListener(activity, (value, error)->{
            if(error != null){
                return;
            }
            if(value!= null){
                if(value.getLong("disponivel") != null){
                    int availability = Objects.requireNonNull(value.getLong("disponivel").intValue());
                    isReceiverAvailable = availability == 1;
                }
            }
            if(isReceiverAvailable){
                view.setVisibility(View.VISIBLE);
            } else{
                view.setVisibility(View.INVISIBLE);
            }
        });
    }


    @Override
    public void onBindViewHolder(@NonNull ContatosItemRecycleViewAdapter.ViewHolder holder, int position) {
        Usuario usuario = usuarios.get(position);
        StringBuilder tokenUsuarioRecebe = new StringBuilder();
        StringBuilder tokenUsuarioEnvia = new StringBuilder();


        String[] nome = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString().split("@");

        db.collection("Usuarios").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        if(documentSnapshot.getData().get("nome").toString().equalsIgnoreCase(usuario.getNome())){
                            tokenUsuarioRecebe.append(documentSnapshot.getData().get("token").toString());
                        }
                        if(documentSnapshot.getData().get("nome").toString().equalsIgnoreCase(nome[0])){
                            tokenUsuarioEnvia.append(documentSnapshot.getData().get("token").toString());
                        }
                    }
                }
            }
        });

        listenAvailabilityOfReceiver(holder.onlineIndicator, usuario.getNome());

        holder.txtNome.setText(usuario.getNome());
        new DownloadImage(holder.imagem, holder.progressBar).execute(usuario.getImg());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(holder.layout.getContext(), ChatScreen.class);
                in.putExtra("NomeUsuario", usuario.getNome());
                in.putExtra("UsuarioAtual", nome[0]);
                in.putExtra("TokenRecebe", tokenUsuarioRecebe.toString());
                in.putExtra("TokenEnvia", tokenUsuarioEnvia.toString());
                holder.layout.getContext().startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }
}
