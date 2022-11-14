package com.example.capy_65ddm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class ChatItemRecycleViewAdapter extends RecyclerView.Adapter<ChatItemRecycleViewAdapter.ViewHolder>{

    private List<MensagemModel> mensagens;
    private Context context;

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

        public ViewHolder(View itemView){
            super(itemView);
            txtUsuarioMsg = itemView.findViewById(R.id.txtUsuarioMsg);
            txtMensagem = itemView.findViewById(R.id.txtMensagem);
            layout = itemView.findViewById(R.id.layoutMsg);

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


    }

    @Override
    public int getItemCount() {
        return mensagens.size();
    }
}