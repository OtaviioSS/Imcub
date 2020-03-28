package com.imcub.imcubApp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.imcub.imcubApp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import firebase.Conexao;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lo" ;
    private static final int RC_SIGN_IN = 0;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText barsenha;
    private EditText baremail;
    private  FirebaseAuth auth;
    private TextView esqueci;
    private Button btnlogar;
    private TextView btnCad;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference ;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarComponentes();
        inicializarfirebase();
        mAuth = FirebaseAuth.getInstance();
        firebaseauth();
        cliquesdebotao();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestId()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

    }
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null){
            Intent intent = new Intent(MainActivity.this,Activity_Inicio.class);
        }



    }
    private void inicializarComponentes() {
        esqueci = findViewById(R.id.btnEsqueci);
        baremail = findViewById(R.id.barEmailLogin);
        barsenha = findViewById(R.id.barSenhaLogin);
        btnlogar = findViewById(R.id.btnLogarLogin);
        btnCad = findViewById(R.id.btnCadEmail);


    }
    private void cliquesdebotao() {
        btnlogar.setOnClickListener(logar);
        btnCad.setOnClickListener(telacadastro);
        esqueci.setOnClickListener(resetarsenha);
    }
    private void inicializarfirebase() {

        FirebaseApp.initializeApp(MainActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    private void firebaseauth() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {


            }
        };
    }




    //[INICIO CLIQUE DE BOTÃO DE LOGAR]
    View.OnClickListener logar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                    String email = baremail.getText().toString().trim();
                    String senha = barsenha.getText().toString().trim();
                    login(email, senha);
            }catch (NullPointerException nullEx){
                Toast.makeText(MainActivity.this,"Por favor preencha todos os campos",Toast.LENGTH_LONG).show();
            }catch (IllegalArgumentException illegalEx){
                Toast.makeText(MainActivity.this,"Senha ou email invalidos",Toast.LENGTH_LONG).show();
            }
        }
    };
    //[FIM DE CLIQUE DE BOTÃO DE LOGAR]

    //[INICIO DE CLIQUE ABRIR TELA CADASTRO]
    View.OnClickListener telacadastro = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this,Activity_Cadastro_Usuario.class);
            startActivity(intent);
        }};
    //[FIM DE CLIQUE ABRIR TELA CADASTRO]

    //[INICIO DE CLIQUE ABRIR TELA RECUPERAR CONTA]
    View.OnClickListener resetarsenha = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, Activity_RecuperarConta.class);
            startActivity(intent);

        }
    };
    //[FIM DE CLIQUE ABRIR TELA RECUOERAR CONTA]

    //[INICIO DE FUNÇÃO DE AUTENTICAR LOGIN]
    private void login(String email,String senha) {
        try {
            auth.signInWithEmailAndPassword(email,senha).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Intent intent = new Intent(MainActivity.this, Activity_Inicio.class);
                        startActivity(intent);
                        finish();
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                    }else{
                        Toast.makeText(MainActivity.this,"Erro ao efetuar login",Toast.LENGTH_SHORT).show();
                    }



                }
            });
        }catch (NullPointerException nullEx){
            nullEx.printStackTrace();

        }catch (IllegalArgumentException argEx){
            Toast.makeText(MainActivity.this,"O campo de email e senha deve ser preenchida",Toast.LENGTH_LONG).show();

        }
    }
    //[FIM DE FUNÇÃ DE AUTENTICAR LOGIN]




}
