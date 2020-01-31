package com.example.imcub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Ideia_RecyclerView.IdeiaAdapter;
import modelos.Modelo_Ideia;

public class Activity_Inicio extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private IdeiaAdapter ideiaAdapter;
    List<Modelo_Ideia> listadeideias = new ArrayList<>();
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__inicio);
        inicializarcomponentes();
        inicializarfirebase();
        setUpGoogleApiClient();
        recyclerView = findViewById(R.id.recyclerInicio);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        ideiaAdapter = new IdeiaAdapter(listadeideias);
        recyclerView.setAdapter(ideiaAdapter);
        RecuperarIdeias();
        verificarAutenticacao();
        BottomNavigationView navigationView = findViewById(R.id.navegtionInicio);
        navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);



    }
    private void inicializarcomponentes() {
    }
    private void inicializarfirebase() {
        FirebaseApp.initializeApp(Activity_Inicio.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


    }

    //[INICIO DE VERIFICAR SE O USUARIO ESTA LOGADO]
    private void verificarAutenticacao() {
        if(FirebaseAuth.getInstance().getUid() == null){
            Intent intent =  new Intent(Activity_Inicio.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
    //[FIM DE VERIFICAR SE O USUARIO ESTA LOGADO]

    public void setUpGoogleApiClient(){
        //GoogleSignInOptions
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    //[INICIO RECUPERAR DADOS DO FIREBASE ]
    private void RecuperarIdeias(){
        DatabaseReference ideiaRef = databaseReference.child("Ideias");
        ideiaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listadeideias.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    listadeideias.add(ds.getValue(Modelo_Ideia.class) );
                }
                ideiaAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    //[FIM RECUPERAR DADOS DO FIREBASE]

    //[INICIO FUNÇÃO DE CLIQUES DO BOTTOMNAVIGATION]
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.addiadeiamenu:
                    Intent intent1 = new Intent(Activity_Inicio.this,Activity_Add_Ideia.class);
                    startActivity(intent1);
                    return  true;
                case R.id.sair:
                    FirebaseAuth.getInstance().signOut();
                    verificarAutenticacao();
                    return true;
                case R.id.perfil:
                    Intent intent2 = new Intent(Activity_Inicio.this, Activity_Perfil_Usuario.class);
                    startActivity(intent2);
                    break;
            }
            return false;
        }
    };
    //[FIM FUNÇÃO DE CLIQUES DO BOTTOMNAVIGATION]

}