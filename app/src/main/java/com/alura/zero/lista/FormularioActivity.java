package com.alura.zero.lista;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.alura.zero.lista.dao.AlunoDAO;
import com.alura.zero.lista.modelo.Aluno;

import java.io.File;

public class FormularioActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 423;
    String nome, site, telefone, endereco;
    private FormularioHelper helper;
    private String caminhoFoto;

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

        Button btnFoto = (Button) findViewById(R.id.formulario_botao_foto);
        assert btnFoto != null;
        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                File arquivoFoto = new File(caminhoFoto);
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
                startActivityForResult(intentCamera, REQUEST_CODE);
            }
        });


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
 if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
     ImageView foto = (ImageView)findViewById(R.id.formulario_foto);
     Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
     Bitmap bitmapRD = Bitmap.createScaledBitmap(bitmap,300,300, true);
     foto.setImageBitmap(bitmapRD);
     foto.setScaleType(ImageView.ScaleType.FIT_XY);

 }
    }
}
