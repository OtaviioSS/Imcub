package com.example.imcub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class Activity_Perfil_Usuario extends AppCompatActivity {

    private ImageView imguser;
    private TextView nomuser;
    private TextView idadeuser;
    private RecyclerView minhaIdeias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__perfil__usuario);
        inicializarcomponentes();
        carregaruser();
    }

    private void inicializarcomponentes(){
        imguser = findViewById(R.id.imgUsuarioPerfil);
        nomuser = findViewById(R.id.txtNomeUsuarioPerfil);
        idadeuser = findViewById(R.id.txtIdadeUsuarioPerfil);
        minhaIdeias = findViewById(R.id.recyclerIdeiasPerfil);
    }

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    //[INICIO RECUPERAR DADOS DO USUARIO]
    private void carregaruser(){

        if(user!=null){
            imguser.setImageURI(user.getPhotoUrl());
            nomuser.setText(user.getDisplayName());

        }
        Picasso.get()
                .load(user.getPhotoUrl())
                .resize(50,50)
                .error(R.drawable.ic_person_black_24dp)
                .into(imguser);
    }
    //[FIM RECUOERAR DADOS DO USUSARIO]

}
