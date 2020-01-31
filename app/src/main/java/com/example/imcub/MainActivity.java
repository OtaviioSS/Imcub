package com.example.imcub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarComponentes();
        inicializarfirebase();
        mAuth = FirebaseAuth.getInstance();
        firebaseauth();
        cliquesdebotao();
        findViewById(R.id.sign_in_button).setOnClickListener(cliquegoogle);
        setUpGoogleApiClient();
    }
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();



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
            if(baremail == null & barsenha !=null){
                Toast.makeText(MainActivity.this,"Por favor, preencha o campo de email",Toast.LENGTH_LONG).show();
            }if (barsenha == null & baremail !=null  ){
                Toast.makeText(MainActivity.this,"Por favor, preencha o campo de senha",Toast.LENGTH_LONG).show();

            }if (barsenha == null & baremail == null){
                Toast.makeText(MainActivity.this,"Por favor, preencha todos os campos",Toast.LENGTH_LONG).show();
            }else {
                String email = baremail.getText().toString().trim();
                String senha = barsenha.getText().toString().trim();
                login(email, senha);
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
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);


                    }else{
                        Toast.makeText(MainActivity.this,"Erro ao efetuar login",Toast.LENGTH_SHORT).show();
                    }



                }
            });
        }catch (Exception e){
            e.printStackTrace();

        }
    }
    //[FIM DE FUNÇÃ DE AUTENTICAR LOGIN]

    public void  updateUI(GoogleSignInAccount account){
        if(account != null){
            Toast.makeText(this,"U Signed In successfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,Activity_Inicio.class));
        }else {
            Toast.makeText(this,"U Didnt signed in",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            Intent intent = new Intent(MainActivity.this,Activity_Inicio.class);
            startActivity(intent);
            Toast.makeText(this,"Seja bem vindo!",Toast.LENGTH_LONG).show();

            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }

    }
    public void setUpGoogleApiClient(){
        //GoogleSignInOptions
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
    View.OnClickListener cliquegoogle = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.sign_in_button:
                    signIn();
                    break;
                // ...
            }
        }
    };
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }
    public void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);
        } catch (ApiException e) {

            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(acct);
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }

                        // ...0
                    }
                });
    }
}
