package com.example.capy_65ddm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatItemRecycleViewAdapter extends RecyclerView.Adapter<ChatItemRecycleViewAdapter.ViewHolder>{

    private List<Usuario> usuarios;

    public ChatItemRecycleViewAdapter(List<Usuario> usuarios) {
        this.usuarios = usuarios;
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
        Usuario usuario = usuarios.get(position);
        holder.txtUsuarioMsg.setText(usuario.getNome());
        holder.txtMensagem.setText(usuario.getEmail());


    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }
}