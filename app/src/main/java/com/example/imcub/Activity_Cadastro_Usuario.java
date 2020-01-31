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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import modelos.Modelo_Usuario;


import java.util.UUID;

import firebase.Conexao;

public class Activity_Cadastro_Usuario extends AppCompatActivity {
    private EditText txtnome;
    private EditText txtemail;
    private EditText txtsenha;
    private Button concluir;
    private FirebaseAuth auth;
    private EditText data;
    private DatabaseReference databaseReference;
    private ImageButton addImgIN;
    private Uri imagemSelecionada;
    private ImageView viewImagePerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__cadastro__usuario);
        inicializarcomponentes();
        cliques();
        inicializarfirebase();

    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }

    private void inicializarfirebase() {
        FirebaseApp.initializeApp(Activity_Cadastro_Usuario.this);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void inicializarcomponentes() {
        txtnome = findViewById(R.id.barNomeCadUser);
        txtemail = findViewById(R.id.barEmailCadUser);
        txtsenha = findViewById(R.id.barSenhaCadUser);
        concluir = findViewById(R.id.btnConcluirCadUser);
        data = findViewById(R.id.barDataNacimentoCadUser);
        addImgIN = findViewById(R.id.btnselecionarFotoPerfil);
        viewImagePerfil = findViewById(R.id.btnselcfoto);



    }

    private void cliques(){
        concluir.setOnClickListener(registro);
        addImgIN.setOnClickListener(selecionarimagem);
    }

    //[Inicio Clique de Selecionar Imagem]
    View.OnClickListener selecionarimagem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent =new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult( intent,0);
        }
    };

    //[Fim Clique de Selecionar Imagem]


    //[Inicio Clique de Concluir Cadastro]
    View.OnClickListener registro = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = txtemail.getText().toString().trim();
            String senha = txtsenha.getText().toString().trim();
            criarUser(email,senha); //Executar Função de Criação de Usuario passando como paraetros o email e a senha digitada
        }
    };
    //[Fim Clique de Concluir Cadastro]


    //[Inicio Função Criar Usuario e Salvar Dados no BD Firebase Realtime DataBase]
    private void criarUser(String email,String senha) {
        auth.createUserWithEmailAndPassword(email,senha).addOnCompleteListener(Activity_Cadastro_Usuario.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Modelo_Usuario usuario = new Modelo_Usuario();
                    usuario.setUsuarioId(UUID.randomUUID().toString());
                    usuario.setUsuarioEmail(txtemail.getText().toString());
                    usuario.setUsuarioNome(txtnome.getText().toString());
                    usuario.setUsuarioDataNascimento(data.getText().toString());
                    uploadimg();
                    databaseReference.child("Usuario").child(usuario.getUsuarioId()).setValue(usuario);
                    Toast.makeText(Activity_Cadastro_Usuario.this,"Cadastrao com Sucesso",Toast.LENGTH_SHORT).show();
                    limparcampos();
                    Intent i = new Intent(Activity_Cadastro_Usuario.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(Activity_Cadastro_Usuario.this,"Erro ao cadastrar",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //[Fim Função Criar Usuario e Salvar Dados]

    //[Inicio de Função Carregar Imagem Para Firebase Storage]
    private void uploadimg() {
        String filename = UUID.randomUUID().toString();
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
                Log.e("Erro", e.getMessage(),e);
            }
        });

    }
    //[Fim de Função Carregar Imagm Para Firebase Storage]

    //[Inicio Função limpar campos ]
    private void limparcampos() {
        txtemail.setText("");
        txtnome.setText("");
        data.setText("");
        txtsenha.setText("");
    }
    //[Fim Função limpar campos]


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            imagemSelecionada = data.getData();
            viewImagePerfil.setImageURI(imagemSelecionada);
        }
    }



}
