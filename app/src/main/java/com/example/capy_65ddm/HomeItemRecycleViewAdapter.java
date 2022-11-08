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

class HomeItemRecyclerViewAdapter extends RecyclerView.Adapter<HomeItemRecyclerViewAdapter.ViewHolder> {

    private List<Usuario> usuarios;

    public HomeItemRecyclerViewAdapter(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNome;
        TextView txtEmail;
        ConstraintLayout layout;
        ImageView imagem;
        public ViewHolder(View itemView){
            super(itemView);
            txtNome = itemView.findViewById(R.id.txtNome);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            layout = itemView.findViewById(R.id.container);

//            imagem = itemView.findViewById(R.id.imgAluno);
        }
    }

    @NonNull
    @Override
    public HomeItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_adapter_homeitem, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeItemRecyclerViewAdapter.ViewHolder holder, int position) {
        Usuario usuario = usuarios.get(position);
        holder.txtEmail.setText(usuario.getEmail());
        holder.txtNome.setText(usuario.getNome());
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
}
