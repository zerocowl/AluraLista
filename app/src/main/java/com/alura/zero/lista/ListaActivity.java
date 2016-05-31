package com.alura.zero.lista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.alura.zero.lista.dao.AlunoDAO;
import com.alura.zero.lista.modelo.Aluno;

import java.util.ArrayList;
import java.util.List;

public class ListaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        Button btnNovo = (Button) findViewById(R.id.novoAluno);

        carregarLista();

        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentFormulario = new Intent(ListaActivity.this, FormularioActivity.class);
                startActivity(IntentFormulario);
            }
        });
    }

    private void carregarLista() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();
        ListView lista =  (ListView) findViewById(R.id.listaAlunos);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, alunos);
        lista.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarLista();
    }
}
