package com.alura.zero.lista;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.support.v4.app.ActivityCompat;
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
import java.util.jar.Manifest;

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
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) lista.getItemAtPosition(info.position);
        final String numero = aluno.getTelefone();
        //Chamada
        MenuItem lg = menu.add("Ligar");
        lg.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(ActivityCompat.checkSelfPermission(ListaActivity.this, android.Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ListaActivity.this,new String[]{android.Manifest.permission.CALL_PHONE},123);
                }else {
                    Intent intLigar = new Intent(Intent.ACTION_CALL);
                    intLigar.setData(Uri.parse("tel:" + numero));
                }
                return false;
            }
        });



        //SMS
        MenuItem sms = menu.add("Enviar SMS");
        Intent intSMS = new Intent(Intent.ACTION_VIEW);
        intSMS.setData(Uri.parse("sms:"+numero));
        sms.setIntent(intSMS);

        //MAPA
        MenuItem mapa = menu.add("Ver no Mapa");
        Intent intMapa = new Intent(Intent.ACTION_VIEW);
        String end = aluno.getEndereco();
        intSMS.setData(Uri.parse("geo:0,0?q="+end));
        sms.setIntent(intMapa);

        //SITE
        MenuItem site = menu.add("Ver site");
        Intent intSite = new Intent(Intent.ACTION_VIEW);
        String link = aluno.getSite();

        if(!link.startsWith("http://")){
            link = "http://"+link;
        }
        intSite.setData(Uri.parse(link));
        site.setIntent(intSite);

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

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
