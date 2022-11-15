package com.example.capy_65ddm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChatItemRecycleViewAdapter extends RecyclerView.Adapter<ChatItemRecycleViewAdapter.ViewHolder>{

    private List<MensagemModel> mensagens;
    private Context context;
    private FirebaseFirestore db;

    public ChatItemRecycleViewAdapter(Context context) {
        this.mensagens = new ArrayList<>();
        this.context = context;
    }

    @Override
    public String toString() {
        return "ChatItemRecycleViewAdapter{" +
                "mensagens=" + mensagens +
                '}';
    }

    public List<MensagemModel> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<MensagemModel> mensagens) {
        this.mensagens = mensagens;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtUsuarioMsg;
        TextView txtMensagem;
        ConstraintLayout layout;
        ImageView imagem;
        ProgressBar loading_chat_img_live;

        public ViewHolder(View itemView){
            super(itemView);
            txtUsuarioMsg = itemView.findViewById(R.id.txtUsuarioMsg);
            txtMensagem = itemView.findViewById(R.id.txtMensagem);
            layout = itemView.findViewById(R.id.layoutMsg);
            loading_chat_img_live = itemView.findViewById(R.id.loading_chat_img_live);
            imagem = itemView.findViewById(R.id.img_capy);

        }
    }

    public void add(MensagemModel message){
        mensagens.add(message);
        notifyDataSetChanged();
    }

    public void clear(){
        mensagens.clear();
        notifyDataSetChanged();
    }

    public int getSize(){
        return mensagens.size();
    }



    @NonNull
    @Override
    public ChatItemRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_adapter_chat, parent, false);
        db = FirebaseFirestore.getInstance();
        ChatItemRecycleViewAdapter.ViewHolder viewHolder = new ChatItemRecycleViewAdapter.ViewHolder(itemView);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ChatItemRecycleViewAdapter.ViewHolder holder, int position) {
        MensagemModel mensagem = mensagens.get(position);

        String[] nome = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString().split("@");
//        if(mensagem.getUsuarioRecebeu().equals(nome[0])){
//
//        }else {
//            holder.txtUsuarioMsg.setText(mensagem.getUsuarioRecebeu());
//        }
        holder.txtUsuarioMsg.setText(mensagem.getUsuarioEnviou());
        holder.txtMensagem.setText(mensagem.getMensagem());
        holder.loading_chat_img_live.setVisibility(View.INVISIBLE);
//        db.collection("Usuarios").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()) {
//                    for (QueryDocumentSnapshot documentSnapshot: task.getResult()) {
//                        if (documentSnapshot.getData().get("nome").toString().equalsIgnoreCase(mensagem.getUsuarioEnviou())) {
//                            new DownloadImage(holder.imagem, holder.loading_chat_img_live).execute(documentSnapshot.getData().get("img").toString());
//                        }
//                    }
//                }
//            }
//        });



    }

    @Override
    public int getItemCount() {
        return mensagens.size();
    }
}