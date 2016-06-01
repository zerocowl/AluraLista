package com.alura.zero.lista;

import android.widget.EditText;
import android.widget.RatingBar;

import com.alura.zero.lista.modelo.Aluno;

/**
 * Created by zero on 30/05/16.
 */
public class FormularioHelper {
    private final EditText edtNome;
    private final EditText edtSite;
    private final EditText edtTelefone;
    private final EditText edtEndereco;
    private final RatingBar edtNota;

    private Aluno aluno;

    public FormularioHelper(FormularioActivity activity) {
        edtNome = (EditText) activity.findViewById(R.id.formulario_nome);
        edtSite = (EditText) activity.findViewById(R.id.formulario_site);
        edtTelefone = (EditText) activity.findViewById(R.id.formulario_telefone);
        edtEndereco = (EditText) activity.findViewById(R.id.formulario_endereco);
        edtNota = (RatingBar) activity.findViewById(R.id.formulario_nota);

        aluno = new Aluno();

    }

    public Aluno pegaAluno() {
        aluno.setNome(edtNome.getText().toString());
        aluno.setTelefone(edtTelefone.getText().toString());
        aluno.setEndereco(edtEndereco.getText().toString());
        aluno.setSite(edtSite.getText().toString());
        aluno.setNota(Double.valueOf(edtNota.getProgress()));
        return aluno;
    }

    public void preencheFormulario(Aluno aluno) {

        edtNome.setText(aluno.getNome());
        edtTelefone.setText(aluno.getTelefone());
        edtSite.setText(aluno.getSite());
        edtEndereco.setText(aluno.getEndereco());
        edtNota.setProgress(aluno.getNota().intValue());
        this.aluno = aluno;
    }

}
