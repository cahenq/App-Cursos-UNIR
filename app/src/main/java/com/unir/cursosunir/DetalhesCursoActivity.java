package com.unir.cursosunir;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.bumptech.glide.Glide;

public class DetalhesCursoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalhes_curso);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tela), (tela, insets) -> {
            Insets barras = insets.getInsets(WindowInsetsCompat.Type.systemBars()
                    | WindowInsetsCompat.Type.displayCutout());
            tela.setPadding(barras.left, barras.top, barras.right, barras.bottom);
            return insets;
        });

        ImageButton btnVoltar = findViewById(R.id.btnVoltar);
        ImageView imagemCurso = findViewById(R.id.imagemCurso);
        TextView txtNome = findViewById(R.id.txtNome);
        TextView txtCampus = findViewById(R.id.txtCampus);
        TextView txtGrau = findViewById(R.id.txtGrau);
        TextView txtTurno = findViewById(R.id.txtTurno);
        TextView txtDescricao = findViewById(R.id.txtDescricao);
        Button btnSite = findViewById(R.id.btnSite);
        Button btnCompartilhar = findViewById(R.id.btnCompartilhar);
        btnVoltar.setOnClickListener(v -> finish());

        String nome = getIntent().getStringExtra("nome");
        String campus = getIntent().getStringExtra("campus");
        String grau = getIntent().getStringExtra("grau");
        String turno = getIntent().getStringExtra("turno");
        String descricao = getIntent().getStringExtra("descricao");
        String imagem = getIntent().getStringExtra("imagem");
        String site = getIntent().getStringExtra("site");

        if (nome == null || nome.trim().isEmpty() || campus == null || grau == null
                || turno == null || descricao == null) {
            Toast.makeText(this, R.string.dados_ausentes, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        txtNome.setText(nome);
        txtCampus.setText(getString(R.string.campus_valor, campus));
        txtGrau.setText(getString(R.string.grau_valor, grau));
        txtTurno.setText(getString(R.string.turno_valor, turno));
        txtDescricao.setText(descricao);
        Glide.with(this).load(imagem)
                .placeholder(R.drawable.ic_educacao)
                .error(R.drawable.ic_educacao)
                .centerCrop().into(imagemCurso);

        btnSite.setOnClickListener(v -> {
            if (site == null || site.trim().isEmpty()) {
                Toast.makeText(this, R.string.site_ausente, Toast.LENGTH_SHORT).show();
                return;
            }
            Uri endereco = Uri.parse(site.trim());
            String protocolo = endereco.getScheme();
            if ((!"https".equalsIgnoreCase(protocolo) && !"http".equalsIgnoreCase(protocolo))
                    || endereco.getHost() == null || endereco.getHost().isEmpty()) {
                Toast.makeText(this, R.string.site_invalido, Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, endereco);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, R.string.sem_navegador, Toast.LENGTH_SHORT).show();
            }
        });

        btnCompartilhar.setOnClickListener(v -> {
            String link = site == null || site.trim().isEmpty()
                    ? getString(R.string.link_indisponivel) : site;
            String mensagem = nome + "\nCampus: " + campus + "\n" + link;
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, mensagem);
            try {
                startActivity(Intent.createChooser(intent, getString(R.string.compartilhar_curso)));
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, R.string.sem_compartilhamento, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
