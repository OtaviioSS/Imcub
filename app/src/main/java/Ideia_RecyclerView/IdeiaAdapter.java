package Ideia_RecyclerView;

import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.imcub.imcubApp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelos.Modelo_Ideia;

public class IdeiaAdapter extends RecyclerView.Adapter<IdeiaAdapter.MyViewHolder> {
    private String nomeImagem;
    private String nomeUsuario;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference reference;


    List<Modelo_Ideia> ideias ;
    int cor =0;

    public IdeiaAdapter(List<Modelo_Ideia> ideias) {
        this.ideias = ideias;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_view_1, parent, false);
        return new MyViewHolder(itemLista);



    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int i) {

        try {
            Modelo_Ideia ideia = ideias.get(i);
            holder.tituloIdeia.setText(ideia.getIdeiaTitulo());
            holder.descricao.setText(ideia.getIdeiaDescricao());
            holder.nome.setText(ideia.getIdeianomeUser());
            holder.imgUser.setImageURI(Uri.parse(ideia.getIdeiaImagemPerfil()));
            holder.time.setText("Publicado dia "+ideia.getIdeiaDataDaPub());
        }catch (Exception e){
            Log.i("Erro","erro",e);
        }
        //Carregar imagem
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        holder.btnGostei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cor == 0) {
                    holder.btnGostei.setColorFilter(Color.parseColor("#ff8800"));
                    cor = 1;

                    holder.btnGostei.setColorFilter(Color.parseColor("#ffffff"));
                    cor =0;
                }

            }
        });
        holder.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cor == 0) {
                    holder.btnSave.setColorFilter(Color.parseColor("#00ee55"));
                    cor = 1;
                }else{
                    holder.btnSave.setColorFilter(Color.parseColor("#ffffff"));
                    cor =0;
                }

            }
        });

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        if(user!=null){
         StorageReference imagesRef = storageRef.child("images/"+user.getEmail());
            nomeUsuario = holder.nome.getText().toString();
            nomeImagem = imagesRef.toString();
            holder.BaixarImagemPerfil();}



    }
    private void contCurtidas(DatabaseReference reference){
        Map<String,Object> mapLikes = new HashMap<>();
        mapLikes.put("ideiaCurtidas", true);
        reference.child("ideiaCurtidas").updateChildren(mapLikes);
    }
    @Override
    public int getItemCount() {
        return ideias.size();

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgUser;
        TextView nome;
        TextView time;
        TextView descricao;
        TextView tituloIdeia;
        ImageButton btnGostei;
        ImageButton btnSave;



        MyViewHolder(View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.usernameItemView1);
            time = itemView.findViewById(R.id.timeItemView1);
            imgUser = itemView.findViewById(R.id.imgPerfilItemView1);
            tituloIdeia = itemView.findViewById(R.id.txtTitleItemView1);
            btnSave = itemView.findViewById(R.id.btnSaveItemVIew1);
            btnGostei = itemView.findViewById(R.id.btnLikeItemView1);
            descricao = itemView.findViewById(R.id.txtDescricaoItemView1);
        }

        private void BaixarImagemPerfil(){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();

            storageRef.child("images/"+nomeUsuario).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'

                        Picasso.get().load(uri).into(imgUser);


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });


        }

    }

}