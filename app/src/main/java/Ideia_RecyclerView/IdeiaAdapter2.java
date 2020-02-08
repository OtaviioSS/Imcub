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

import com.imcub.imcubApp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

import modelos.Modelo_Ideia;

public class IdeiaAdapter2 extends RecyclerView.Adapter<IdeiaAdapter2.MyViewHolder> {

    List<Modelo_Ideia> ideias ;
    int cor =0;

    public IdeiaAdapter2(List<Modelo_Ideia> ideias) {
        this.ideias = ideias;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity__perfil__item__view, parent, false);
        return new MyViewHolder(itemLista);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int i) {
        try {
            Modelo_Ideia ideia = ideias.get(i);
            holder.tituloIdeia.setText(ideia.getIdeiaTitulo());



        }catch (Exception e){
            Log.i("Erro","erro",e);
        }




    }

    @Override
    public int getItemCount() {
        return ideias.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tituloIdeia;





        MyViewHolder(View itemView) {
            super(itemView);
            tituloIdeia = itemView.findViewById(R.id.textView);

        }
    }


}