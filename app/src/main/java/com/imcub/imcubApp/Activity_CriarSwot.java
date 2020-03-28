package com.imcub.imcubApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import modelos.Modelo_Ideia;
import modelos.Modelo_Swot;

public class Activity_CriarSwot extends AppCompatActivity {
    private Button btnconcluir;
    private EditText barForcas;
    private EditText barFraquezasas;
    private EditText barOportunidades;
    private EditText barAmeacas;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__criar_swot);
        inicializarcomponentes();
        inicializarfirebase();
        cliquesdebotao();
    }

    public void inicializarfirebase() {
        FirebaseApp.initializeApp(Activity_CriarSwot.this);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    private void cliquesdebotao() {
        btnconcluir.setOnClickListener(criarswot);
    }

    private void inicializarcomponentes() {
        btnconcluir = findViewById(R.id.btnconcluirswot);
        barForcas = findViewById(R.id.barforca);
        barFraquezasas = findViewById(R.id.barfraqueza);
        barOportunidades = findViewById(R.id.baroportunidade);
        barAmeacas = findViewById(R.id.barameacas);
    }


    View.OnClickListener criarswot = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Modelo_Ideia modelo_ideia = new Modelo_Ideia();
            Modelo_Swot modeloSwot = new Modelo_Swot();
            modeloSwot.setForcas(barForcas.getText().toString());
            modeloSwot.setFraquezas(barFraquezasas.getText().toString());
            modeloSwot.setOportunidades(barOportunidades.getText().toString());
            modeloSwot.setAmeacas(barAmeacas.getText().toString());
            modeloSwot.setIdUser(user.getUid());
            databaseReference.child("Analises").child("Swots").child(modeloSwot.getIdUser()).setValue(modeloSwot);
            Intent intent = new Intent(Activity_CriarSwot.this,Activity_Add_Ideia.class);
            startActivity(intent);
        }
    };
}
