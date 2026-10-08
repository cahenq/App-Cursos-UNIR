package com.unir.cursosunir;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Spinner;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tela), (tela, insets) -> {
            Insets barras = insets.getInsets(WindowInsetsCompat.Type.systemBars()
                    | WindowInsetsCompat.Type.displayCutout());
            tela.setPadding(barras.left, barras.top, barras.right, barras.bottom);
            return insets;
        });

        Spinner spinnerCampus = findViewById(R.id.spinnerCampus);
        RadioGroup grupoGrau = findViewById(R.id.grupoGrau);
        CheckBox checkNoturno = findViewById(R.id.checkNoturno);
        Button btnVerCursos = findViewById(R.id.btnVerCursos);

        ArrayList<String> campi = new ArrayList<>();
        campi.add("Todos os campi");
        ArrayList<Curso> listaCursos = CursosData.getCursos();
        for (Curso curso : listaCursos) {
            if (!campi.contains(curso.getCampus())) {
                campi.add(curso.getCampus());
            }
        }

        ArrayAdapter<String> adapterCampus = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, campi);
        adapterCampus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCampus.setAdapter(adapterCampus);

        btnVerCursos.setOnClickListener(v -> {
            String campus = spinnerCampus.getSelectedItem().toString();
            String grau = "Todos";
            if (grupoGrau.getCheckedRadioButtonId() == R.id.radioBacharelado) {
                grau = "Bacharelado";
            } else if (grupoGrau.getCheckedRadioButtonId() == R.id.radioLicenciatura) {
                grau = "Licenciatura";
            }

            Intent intent = new Intent(MainActivity.this, ListaCursosActivity.class);
            intent.putExtra("campus", campus);
            intent.putExtra("grau", grau);
            intent.putExtra("noturno", checkNoturno.isChecked());
            startActivity(intent);
        });
    }
}
