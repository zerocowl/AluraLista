package com.alura.zero.lista;

import android.widget.EditText;
import android.widget.RatingBar;

import com.alura.zero.lista.modelo.Aluno;

/**
 * Created by zero on 30/05/16.
 */
public class FormularioHelper{
    private final EditText edtNome;
    private final EditText edtSite;
    private final EditText edtTelefone;
    private final EditText edtEndereco;
    private final RatingBar edtNota;

    public FormularioHelper(FormularioActivity activity){
             edtNome = (EditText) activity.findViewById(R.id.formulario_nome);
            edtSite = (EditText) activity.findViewById(R.id.formulario_site);
             edtTelefone = (EditText) activity.findViewById(R.id.formulario_telefone);
            edtEndereco = (EditText) activity.findViewById(R.id.formulario_endereco);
            edtNota = (RatingBar) activity.findViewById(R.id.formulario_nota);

        }

    public Aluno pegaAluno() {
        Aluno aluno = new Aluno();
        aluno.setNome(edtNome.getText().toString());
        aluno.setTelefone(edtTelefone.getText().toString());
        aluno.setEndereco(edtEndereco.getText().toString());
        aluno.setSite(edtSite.getText().toString());
        aluno.setNota(Double.valueOf(edtNota.getProgress()));
        return aluno;
    }
}
