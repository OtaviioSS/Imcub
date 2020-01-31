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

import com.example.imcub.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

import modelos.Modelo_Ideia;

public class IdeiaAdapter extends RecyclerView.Adapter<IdeiaAdapter.MyViewHolder> {

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
            holder.time.setText(ideia.getIdeiaDataDaPub());


        }catch (Exception e){
            Log.i("Erro","erro",e);
        }
        //Carregar imagem
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        try {
        Picasso.get()
                .load( user.getPhotoUrl() )
                .resize(50,50)
                .error(R.drawable.ic_person_black_24dp)
                .into(holder.imgUser);}catch (NullPointerException e){
        }
        holder.btnGostei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cor == 0) {
                    holder.btnGostei.setColorFilter(Color.parseColor("#ff8800"));
                    cor = 1;
                }else{
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
        TextView cidadeUser;
        TextView tituloIdeia;
        ImageButton btnGostei;
        ImageButton btnSave;




        MyViewHolder(View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.usernameItemView);
            time = itemView.findViewById(R.id.timeItemView);
            imgUser = itemView.findViewById(R.id.imgPerfilItemView);
            cidadeUser = itemView.findViewById(R.id.cidadeItemView);
            tituloIdeia = itemView.findViewById(R.id.txtTitleItemView);
            btnSave = itemView.findViewById(R.id.btnSaveItemVIew);
            btnGostei = itemView.findViewById(R.id.btnLikeItemView1);
            descricao = itemView.findViewById(R.id.txtDescricaoItemView);
        }
    }


}