package com.example.capy_65ddm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContatosItemRecycleViewAdapter extends RecyclerView.Adapter<ContatosItemRecycleViewAdapter.ViewHolder>{

    private List<Aluno> alunos;

    public ContatosItemRecycleViewAdapter(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNome;
        TextView txtEmail;
        ImageView imagem;
        public ViewHolder(View itemView){
            super(itemView);
            txtNome = itemView.findViewById(R.id.txtNome);
//            txtEmail = itemView.findViewById(R.id.txtEmail);

//            imagem = itemView.findViewById(R.id.imgAluno);
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
        Aluno aluno = alunos.get(position);
//        holder.txtEmail.setText(aluno.getEmail());
        holder.txtNome.setText(aluno.getNome());
//        holder.imagem.setImageResource(R.drawable.ic_launcher_background);
    }

    @Override
    public int getItemCount() {
        return alunos.size();
    }
}
