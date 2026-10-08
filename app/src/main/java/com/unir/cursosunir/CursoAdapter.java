package com.unir.cursosunir;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class CursoAdapter extends RecyclerView.Adapter<CursoAdapter.CursoViewHolder> {

    private final ArrayList<Curso> listaCursos;

    public CursoAdapter(ArrayList<Curso> listaCursos) {
        this.listaCursos = listaCursos;
    }

    @NonNull
    @Override
    public CursoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_curso, parent, false);
        return new CursoViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull CursoViewHolder holder, int position) {
        Curso cursoSelecionado = listaCursos.get(position);
        holder.txtNome.setText(cursoSelecionado.getNome());
        holder.txtGrau.setText(cursoSelecionado.getGrau());
        holder.txtCampus.setText(cursoSelecionado.getCampus());
        Glide.with(holder.itemView)
                .load(cursoSelecionado.getImagem())
                .placeholder(R.drawable.ic_educacao)
                .error(R.drawable.ic_educacao)
                .centerCrop()
                .into(holder.imagemCurso);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetalhesCursoActivity.class);
            intent.putExtra("nome", cursoSelecionado.getNome());
            intent.putExtra("campus", cursoSelecionado.getCampus());
            intent.putExtra("grau", cursoSelecionado.getGrau());
            intent.putExtra("turno", cursoSelecionado.getTurno());
            intent.putExtra("descricao", cursoSelecionado.getDescricao());
            intent.putExtra("imagem", cursoSelecionado.getImagem());
            intent.putExtra("site", cursoSelecionado.getSite());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listaCursos.size();
    }

    public static class CursoViewHolder extends RecyclerView.ViewHolder {
        ImageView imagemCurso;
        TextView txtNome;
        TextView txtGrau;
        TextView txtCampus;

        public CursoViewHolder(@NonNull View itemView) {
            super(itemView);
            imagemCurso = itemView.findViewById(R.id.imagemCurso);
            txtNome = itemView.findViewById(R.id.txtNome);
            txtGrau = itemView.findViewById(R.id.txtGrau);
            txtCampus = itemView.findViewById(R.id.txtCampus);
        }
    }
}
