package com.unir.cursosunir;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ListaCursosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_cursos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tela), (tela, insets) -> {
            Insets barras = insets.getInsets(WindowInsetsCompat.Type.systemBars()
                    | WindowInsetsCompat.Type.displayCutout());
            tela.setPadding(barras.left, barras.top, barras.right, barras.bottom);
            return insets;
        });

        ImageButton btnVoltar = findViewById(R.id.btnVoltar);
        TextView txtFiltros = findViewById(R.id.txtFiltros);
        TextView txtQuantidade = findViewById(R.id.txtQuantidade);
        TextView txtVazio = findViewById(R.id.txtVazio);
        RecyclerView recyclerCursos = findViewById(R.id.recyclerCursos);
        btnVoltar.setOnClickListener(v -> finish());

        String campus = getIntent().getStringExtra("campus");
        String grau = getIntent().getStringExtra("grau");
        boolean noturno = getIntent().getBooleanExtra("noturno", false);
        if (campus == null) {
            campus = "Todos os campi";
        }
        if (grau == null) {
            grau = "Todos";
        }

        String resumo = getString(R.string.filtros_valor, campus, grau);
        if (noturno) {
            resumo += getString(R.string.filtro_noturno);
        }
        txtFiltros.setText(resumo);

        ArrayList<Curso> cursos = CursosData.getCursos();
        ArrayList<Curso> cursosFiltrados = new ArrayList<>();
        for (Curso curso : cursos) {
            boolean campusCorreto = campus.equals("Todos os campi")
                    || campus.equals(curso.getCampus());
            boolean grauCorreto = grau.equals("Todos") || grau.equals(curso.getGrau());
            boolean turnoCorreto = !noturno || curso.getTurno().equalsIgnoreCase("Noturno");
            if (campusCorreto && grauCorreto && turnoCorreto) {
                cursosFiltrados.add(curso);
            }
        }

        txtQuantidade.setText(getResources().getQuantityString(R.plurals.quantidade_cursos,
                cursosFiltrados.size(), cursosFiltrados.size()));
        recyclerCursos.setLayoutManager(new LinearLayoutManager(this));
        recyclerCursos.setAdapter(new CursoAdapter(cursosFiltrados));
        if (cursosFiltrados.isEmpty()) {
            txtVazio.setVisibility(View.VISIBLE);
            recyclerCursos.setVisibility(View.GONE);
        }
    }
}
