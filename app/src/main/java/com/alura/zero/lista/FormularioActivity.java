package com.alura.zero.lista;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alura.zero.lista.dao.AlunoDAO;
import com.alura.zero.lista.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {
    String nome, site, telefone, endereco;
    private FormularioHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        helper = new FormularioHelper(FormularioActivity.this);
        Intent intent = getIntent();
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");
        if (aluno != null) {
            helper.preencheFormulario(aluno);
        }


        Button btnSalvar = (Button) findViewById(R.id.formulario_salvar);

        assert btnSalvar != null; //evite um nullPointerExc
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FormularioActivity.this, "Salvou!", Toast.LENGTH_SHORT).show();

                Aluno aluno = helper.pegaAluno();
                AlunoDAO dao = new AlunoDAO(FormularioActivity.this);
                if (aluno.getId() != null) {
                    dao.altera(aluno);
                } else {
                    dao.insere(aluno);
                }
                dao.close();
                finish(); //Mata a activity
            }
        });
    }

}
