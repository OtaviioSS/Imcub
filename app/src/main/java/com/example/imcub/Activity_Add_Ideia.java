package com.example.imcub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.UUID;

import modelos.Modelo_Ideia;

public class Activity_Add_Ideia extends AppCompatActivity {
    private EditText txtTituloIdeia;
    private EditText txtDescricaoIdeia;
    private EditText txtWhastAppIdeia;
    private EditText txtEmail;
    private TextView txtNomeUsuario;
    private ImageView imgPerfilUsuario;
    private Button concluir;
    private DatabaseReference databaseReference;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Uri imagemSelecionada;
    private String IdIdeia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__add__ideia);
        inicializarcompnetente();
        cliquesdebotoes();
        inicializarfirebase();

        /*conteudo.setHint("Titulo");
        descricaodadideia.setHint("Descreva aqui sua ideia denegocio!");
        invest.setHint("R$ 10.000,00");
        numeroTele.setHint("(XX)XXXXX-XXXX");*/

    }
    public void inicializarfirebase() {
        FirebaseApp.initializeApp(Activity_Add_Ideia.this);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    private void cliquesdebotoes() {
        concluir.setOnClickListener(adicionarIdeia);
    }
    private void inicializarcompnetente() {
        txtTituloIdeia = findViewById(R.id.txtTituloAdd);
        txtDescricaoIdeia = findViewById(R.id.txtDescricaoAddIdei);
        txtWhastAppIdeia = findViewById(R.id.txtWhastAppAddIdei);
        txtEmail = findViewById(R.id.txtEmailAddIdei);
        txtNomeUsuario = findViewById(R.id.txtNomeUsuarioAddIdeia);
        imgPerfilUsuario = findViewById(R.id.imgUserAddNovaIdeia);
        concluir = findViewById(R.id.btnConcluirAddIdeia);



    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            imagemSelecionada = data.getData();

        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        RecuperarEmpreendedor();

    }

   //[Inicio Cliques]

        //[Inicio salvar ideia]
            View.OnClickListener adicionarIdeia = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                Modelo_Ideia ideia = new Modelo_Ideia();
                ideia.setIdeiaTitulo(txtTituloIdeia.getText().toString());
                ideia.setIdeiaDescricao(txtDescricaoIdeia.getText().toString());
                ideia.setIdeiaWhatsApp(txtWhastAppIdeia.getText().toString().trim());
                ideia.setIdeiaEmail(txtEmail.getText().toString().trim());
                ideia.setIdeianomeUser(txtNomeUsuario.getText().toString());
                ideia.setIdeiaImagemPerfil(imgPerfilUsuario.toString());
                ideia.setIdeiaCurtidas(0);
                ideia.setIdeiaId(UUID.randomUUID().toString());
                databaseReference.child("Ideias").child(ideia.getIdeiaId()).setValue(ideia);
                Intent intent = new Intent(Activity_Add_Ideia.this, Activity_Inicio.class);
                startActivity(intent);
                limparcampos();
                Toast.makeText(Activity_Add_Ideia.this, "Ideia Adicionada com Sucesso", Toast.LENGTH_LONG).show();
            } catch (RuntimeException err) {
                    Toast.makeText(Activity_Add_Ideia.this, "Erro  " + err, Toast.LENGTH_LONG).show();
                    } }};
        //[Fim salvar ideia]

          //[Inicio selecao de imagens]
                View.OnClickListener selecionarimagem = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         Intent intent = new Intent(Intent.ACTION_PICK);
                         intent.setType("image/*");
                         startActivityForResult(intent,0); }};
                //[Fim Selecao de Imagens]
    //[Fim Clique]

    //[Inicio limpar campos do formulario]
                private void limparcampos() {
        txtTituloIdeia.setText("");
        txtDescricaoIdeia.setText("");
        txtWhastAppIdeia.setText("");
        txtEmail.setText("");
        txtNomeUsuario.setText("");


    }
    //[Fim limpar campos do formulario]

    //[Inicio recuperar dados do usuario logado]
                 private void RecuperarEmpreendedor() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            txtNomeUsuario.setText(user.getDisplayName());
        } else {

            Toast.makeText(Activity_Add_Ideia.this,"Erro ao recuperar email",Toast.LENGTH_LONG).show();
        }
        Picasso.get()
                .load(user.getPhotoUrl())
                .resize(50,50)
                .error(R.drawable.ic_person_black_24dp)
                .into(imgPerfilUsuario);
    }
    //[Fim recuperar dados do usuario logado]

    // [Inicio da selecao e carregamento de imagem da ideia]

    //[Inicio upload de imagem]
                private void uploadimg() {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String filename = UUID.randomUUID().toString();
        IdIdeia = filename;
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/images/" + filename);
        ref.putFile(imagemSelecionada)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Log.i("Sucesso", uri.toString());
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Erro", e.getMessage(), e);
            }
        });


    }
    //[Fim upload de imagem]



}
