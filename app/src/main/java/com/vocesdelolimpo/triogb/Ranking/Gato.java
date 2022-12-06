package com.vocesdelolimpo.triogb.Ranking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vocesdelolimpo.triogb.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Gato extends RecyclerView.Adapter<Gato.MyHolder> {

    private Context context;
    private List<Usuario> usuarioList;

    public Gato(Context context, List<Usuario> usuarioList) {
        this.context = context;
        this.usuarioList = usuarioList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rankbreak, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        String imagen = usuarioList.get(position).getImagen();
        String name = usuarioList.get(position).getName();
        String alias = usuarioList.get(position).getAlias();
        int gatoscore = usuarioList.get(position).getGatoscore();

        String gscore = String.valueOf(gatoscore);

        holder.nombreJugador.setText(name);
        holder.aliasJugador.setText(alias);
        holder.gatoPuntaje.setText(gscore);

        try {
//            Picasso.get().load(imagen).into(holder.imagenJugador);


        }catch (Exception e){
            Picasso.get().load(R.drawable.userdef).into(holder.imagenJugador);
        }


    }

    @Override
    public int getItemCount() {
        return usuarioList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        CircleImageView imagenJugador;
        TextView nombreJugador;
        TextView aliasJugador;
        TextView gatoPuntaje;


        public MyHolder(@NonNull View itemView){
            super(itemView);

//           imagenJugador = itemView.findViewById(R.id.imagenJugador);
            nombreJugador = itemView.findViewById(R.id.nombreJugador);
            aliasJugador = itemView.findViewById(R.id.aliasJugador);
            gatoPuntaje = itemView.findViewById(R.id.Puntaje);
        }

    }

}
