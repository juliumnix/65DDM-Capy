package com.example.capy_65ddm;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContatosItemRecycleViewAdapter extends RecyclerView.Adapter<ContatosItemRecycleViewAdapter.ViewHolder>{

    private List<Usuario> usuarios;

    public ContatosItemRecycleViewAdapter(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNome;
        TextView txtEmail;
        ImageView imagem;
        ConstraintLayout layout;
        public ViewHolder(View itemView){
            super(itemView);
            txtNome = itemView.findViewById(R.id.txtNome);
            layout = itemView.findViewById(R.id.layoutContato);

        }
    }

    @NonNull
    @Override
    public ContatosItemRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_adapter_contato, parent, false);

        ContatosItemRecycleViewAdapter.ViewHolder viewHolder = new ContatosItemRecycleViewAdapter.ViewHolder(itemView);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull ContatosItemRecycleViewAdapter.ViewHolder holder, int position) {
        Usuario usuario = usuarios.get(position);
        holder.txtNome.setText(usuario.getNome());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(holder.layout.getContext(), ChatScreen.class);
                in.putExtra("NomeUsuario", usuario.getNome());
                holder.layout.getContext().startActivity(in);
            }
        });
//        holder.imagem.setImageResource(R.drawable.ic_launcher_background);
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }
}
