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

public class Adaptador extends RecyclerView.Adapter<Adaptador.MyHolder> {

    private Context context;
    private List<Usuario> usuarioList;
    private int juego;
    private int puntaje;

    public Adaptador(Context context, List<Usuario> usuarioList, int juego) {
        this.context = context;
        this.usuarioList = usuarioList;
        this.juego = juego;
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

        switch (juego){
            case 1:
                puntaje = usuarioList.get(position).getOrdenscore();
                break;
            case 2:
                puntaje = usuarioList.get(position).getGatoscore();
                break;

            case 3:
                puntaje = usuarioList.get(position).getBreakscore();
                break;
        }



        String puntajeS = String.valueOf(puntaje);
        holder.nombreJugador.setText(name);
        holder.aliasJugador.setText(alias);
        holder.puntajeText.setText(puntajeS);


        try {
           Picasso.get().load(imagen).into(holder.imagenJugador);


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
        TextView puntajeText;



        public MyHolder(@NonNull View itemView){
            super(itemView);

           imagenJugador = itemView.findViewById(R.id.imagenJugador);
            nombreJugador = itemView.findViewById(R.id.nombreJugador);
            aliasJugador = itemView.findViewById(R.id.aliasJugador);
            puntajeText = itemView.findViewById(R.id.Puntaje);
        }
    }

}
