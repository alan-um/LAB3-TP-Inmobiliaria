package com.example.inmoprop.ui.inmuebles;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.inmoprop.R;
import com.example.inmoprop.models.Inmueble;
import com.example.inmoprop.request.ApiClient;

import java.util.List;

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.InmuebleViewHolder> {
    private List<Inmueble> listaInmueble;
    private Context context;
    private LayoutInflater li;
    public InmuebleAdapter(List<Inmueble> listaInmueble, Context context, LayoutInflater li) {
        this.listaInmueble = listaInmueble;
        this.context = context;
        this.li = li;
    }

    @NonNull
    @Override
    public InmuebleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=li.inflate(R.layout.card_inmueble,parent,false);
        return new InmuebleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InmuebleViewHolder holder, int position) {
        Inmueble i = listaInmueble.get(position);
        holder.tvTipo.setText(i.getTipo());
        holder.tvDireccion.setText(i.getDireccion());
        holder.tvPrecio.setText("$ "+i.getValor());
        Glide.with(context)
                .load(ApiClient.URLBASE+i.getImagen())
                .placeholder(null)
                .error("null")
                .into(holder.img);
        holder.itemView.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            bundle.putSerializable("inmueble",i);
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_nav).navigate(R.id.detalleInmuebleFragment,bundle);
        });
    }

    @Override
    public int getItemCount() {
        return listaInmueble.size();
    }

    public class InmuebleViewHolder extends RecyclerView.ViewHolder{
        TextView tvTipo, tvDireccion, tvPrecio;
        ImageView img;

        public InmuebleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTipo=itemView.findViewById(R.id.tvTipoInmuebleCard);
            tvDireccion=itemView.findViewById(R.id.tvDireccionInmuebleCard);
            tvPrecio=itemView.findViewById(R.id.tvPrecioInmuebleCard);
            img=itemView.findViewById(R.id.imgInmuebleCard);
        }
    }
}
