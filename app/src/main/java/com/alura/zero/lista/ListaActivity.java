package com.alura.zero.lista;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.alura.zero.lista.dao.AlunoDAO;
import com.alura.zero.lista.modelo.Aluno;

import java.util.List;

public class ListaActivity extends AppCompatActivity {

    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        lista = (ListView) findViewById(R.id.listaAlunos);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> ls, View item, int position, long id) {
                Aluno aluno = (Aluno) ls.getItemAtPosition(position);
                Intent IntentFormulario = new Intent(ListaActivity.this, FormularioActivity.class);
                IntentFormulario.putExtra("aluno", aluno); //serrilhar a class
                startActivity(IntentFormulario);
                // Toast.makeText(ListaActivity.this, aluno.getNome(), Toast.LENGTH_SHORT).show();
            }
        });
        carregarLista();
        Button btnNovo = (Button) findViewById(R.id.novoAluno);

        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentFormulario = new Intent(ListaActivity.this, FormularioActivity.class);
                startActivity(IntentFormulario);
            }
        });

        registerForContextMenu(lista);
    }

    private void carregarLista() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, alunos);
        lista.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Aluno aluno = (Aluno) lista.getItemAtPosition(info.position);
                AlunoDAO dao = new AlunoDAO(ListaActivity.this);
                dao.deletar(aluno);
                dao.close();
                carregarLista();
                //Toast.makeText(ListaActivity.this, "Deletar: " + aluno.getNome(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
