package com.imcub.imcubApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.imcub.imcubApp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Ideia_RecyclerView.IdeiaAdapter;
import Ideia_RecyclerView.IdeiaAdapter2;
import modelos.Modelo_Ideia;
import modelos.Modelo_Usuario;

public class Activity_Perfil_Usuario extends AppCompatActivity {

    private ImageView imguser;
    private TextView nomuser;
    private TextView idadeuser;



    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private IdeiaAdapter2 ideiaAdapter;
    List<Modelo_Ideia> listadeideias = new ArrayList<>();
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__perfil__usuario);
        inicializarcomponentes();
        inicializarfirebase();
        carregaruser();
        recyclerView = findViewById(R.id.recyclerIdeiasPerfil);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        ideiaAdapter = new IdeiaAdapter2(listadeideias);
        recyclerView.setAdapter(ideiaAdapter);
        RecuperarIdeias();
    }

    private void inicializarcomponentes(){
        imguser = findViewById(R.id.imgUsuarioPerfil);
        nomuser = findViewById(R.id.txtNomeUsuarioPerfil);
    }

    private void inicializarfirebase() {
        FirebaseApp.initializeApp(Activity_Perfil_Usuario.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


    }

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    //[INICIO RECUPERAR DADOS DO USUARIO]
    private void carregaruser(){
        DatabaseReference ideiaRef = databaseReference.child("Usuario").child(user.getUid());
        ideiaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    Modelo_Usuario usuario = new Modelo_Usuario();
                    usuario = dataSnapshot.getValue(Modelo_Usuario.class);
                    nomuser.setText(usuario.getUsuarioNome());
                }catch (Exception e){

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       // if(user!=null){
           // nomuser.setText(user.getEmail());

       // }
        //Picasso.get()
          //      .load(user.getPhotoUrl())
            //    .resize(50,50)
              //  .error(R.drawable.ic_person_black_24dp)
                //.into(imguser);
    }
    //[FIM RECUOERAR DADOS DO USUSARIO]

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

}
