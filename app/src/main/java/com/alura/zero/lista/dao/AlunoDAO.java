package com.alura.zero.lista.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.alura.zero.lista.modelo.Aluno;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zero on 30/05/16.
 */
public class AlunoDAO extends SQLiteOpenHelper {
    public AlunoDAO(Context context) {
        super(context, "Agenda", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Alunos(id INTEGER PRIMARY KEY, nome TEXT NOT NULL, endereco TEXT, telefone TEXT, site TEXT, nota REAL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXIST Alunos;";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insere(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = getDados(aluno);
        db.insert("Alunos", null, dados);
    }

    // usa um array de String para passar os parametros ao metodo, ? = indice
    public void deletar(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {aluno.getId().toString()};
        db.delete("Alunos", "id = ?", params);
    }

    public void altera(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = getDados(aluno);
        String[] params = {aluno.getId().toString()};
        db.update("Alunos", dados, "id = ?", params);
    }


    public List<Aluno> buscaAlunos() {
//O cursor inicia no zero e voce precisa ir percorrendo as linhas até acabar os registros
        String sql = "SELECT * FROM Alunos;";
        SQLiteDatabase rd = getReadableDatabase();
        Cursor cursor = rd.rawQuery(sql, null);
        List<Aluno> alunos = new ArrayList<Aluno>();

        while (cursor.moveToNext()) {
            Aluno aluno = new Aluno();
            aluno.setId(cursor.getLong(cursor.getColumnIndex("id")));
            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            aluno.setNota(cursor.getDouble(cursor.getColumnIndex("nota")));
            aluno.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
            aluno.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
            aluno.setSite(cursor.getString(cursor.getColumnIndex("site")));
            alunos.add(aluno);
        }
        cursor.close();
        return alunos;

    }

    private ContentValues getDados(Aluno aluno) {
        // Insere os dados no banco usando a class do SQLite como base e 'put' pra marcar os valores
        ContentValues dados = new ContentValues();
        dados.put("nome", aluno.getNome());
        dados.put("site", aluno.getSite());
        dados.put("telefone", aluno.getTelefone());
        dados.put("endereco", aluno.getEndereco());
        dados.put("nota", aluno.getNota());
        return dados;
    }
}
