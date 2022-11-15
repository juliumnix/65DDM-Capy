package com.example.capy_65ddm;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;

class HomeItemRecyclerViewAdapter extends RecyclerView.Adapter<HomeItemRecyclerViewAdapter.ViewHolder> {

    private List<MensagemReceiverModel> usuarios;
    private FirebaseFirestore db;
    private Activity activity;


    public HomeItemRecyclerViewAdapter(List<MensagemReceiverModel> usuarios, Activity activity) {
        this.usuarios = usuarios;
        this.activity = activity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNome;
        TextView txtEmail;
        ConstraintLayout layout;
        ImageView imagem;
        ProgressBar progressBar;
        ImageButton like_button;
        boolean isClicled;
        TextView contador_like;
        int agregaValor = 0;
        public ViewHolder(View itemView){
            super(itemView);
            txtNome = itemView.findViewById(R.id.txtNome);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            layout = itemView.findViewById(R.id.container);
            imagem = itemView.findViewById(R.id.img_user_home);
            progressBar = itemView.findViewById(R.id.loading_img_tweets);
            like_button = itemView.findViewById(R.id.like_button);
            this.isClicled = false;
            this.contador_like = itemView.findViewById(R.id.contador_like);

//            imagem = itemView.findViewById(R.id.imgAluno);
        }
    }

    @NonNull
    @Override
    public HomeItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_adapter_homeitem, parent, false);
        db = FirebaseFirestore.getInstance();
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeItemRecyclerViewAdapter.ViewHolder holder, int position) {
        MensagemReceiverModel usuario = usuarios.get(position);
        holder.txtEmail.setText(usuario.getMensagem());
        holder.txtNome.setText(usuario.getNome());
        holder.contador_like.setText(usuario.getLikes());
        holder.agregaValor = Integer.parseInt(usuario.getLikes());
        listenerLikes(usuario, holder);
        retornaLikes(usuario,holder);
//        if(!holder.isClicled){
//            holder.like_button.setImageResource(R.drawable.ic_baseline_favorite_24_colored);
//        }


        new DownloadImage(holder.imagem, holder.progressBar).execute(usuario.getImg());


       holder.like_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(!holder.isClicled){
                    holder.like_button.setImageResource(R.drawable.ic_baseline_favorite_24_colored);
                    holder.isClicled = true;
                    holder.agregaValor++;
                    atualizaLike(usuario, holder.agregaValor);
                    retornaLikes(usuario,holder);
                    holder.contador_like.setText(Integer.toString(holder.agregaValor));
                } else {
                    holder.like_button.setImageResource(R.drawable.ic_baseline_favorite_24);
                    holder.isClicled = false;
                    holder.agregaValor--;
                    atualizaLike(usuario, holder.agregaValor);
                    retornaLikes(usuario,holder);
                    holder.contador_like.setText(Integer.toString(holder.agregaValor));
                }

            }
        });

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(holder.layout.getContext(), PerfilScreen.class);
                in.putExtra("NomeUsuario", usuario.getNome());
                holder.layout.getContext().startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    private void atualizaLike(MensagemReceiverModel mensagemReceiverModel, int novoValor){
        db.collection("Postagens").document(mensagemReceiverModel.getId()).update("like", novoValor);
    }

    private void retornaLikes(MensagemReceiverModel mensagemReceiverModel, ViewHolder holder){
        StringBuilder retorno = new StringBuilder();
        db.collection("Postagens").document(mensagemReceiverModel.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    holder.agregaValor = Math.toIntExact(task.getResult().getLong("like"));
                }
            }
        });
    }

    private void listenerLikes(MensagemReceiverModel mensagemReceiverModel, ViewHolder holder){
        db.collection("Postagens").document(mensagemReceiverModel.getId()).addSnapshotListener(activity, (value, error) ->{
            if(error != null){
                return;
            }

            if (value != null){
                if(value.getLong("like") != null){
                    holder.contador_like.setText(value.getLong("like").toString());
                    holder.agregaValor = value.getLong("like").intValue();
                }
            }
        });
    }

}
