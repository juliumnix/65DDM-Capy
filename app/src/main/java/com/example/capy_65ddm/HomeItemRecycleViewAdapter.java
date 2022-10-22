package com.example.capy_65ddm;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class HomeItemRecyclerViewAdapter extends RecyclerView.Adapter<HomeItemRecyclerViewAdapter.ViewHolder> {

    private List<Aluno> alunos;

    public HomeItemRecyclerViewAdapter(List<Aluno> alunos) {
        this.alunos = alunos;
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
        Aluno aluno = alunos.get(position);
        holder.txtEmail.setText(aluno.getEmail());
        holder.txtNome.setText(aluno.getNome());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(holder.layout.getContext(), PerfilScreen.class);
                in.putExtra("NomeUsuario", aluno.getNome());
                holder.layout.getContext().startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return alunos.size();
    }
}
