package com.example.inmoprop.ui.contratos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class ContratoAdapter extends RecyclerView.Adapter<ContratoAdapter.ContratoViewHolder> {
    private List<Inmueble> listaInmueble;
    private Context context;
    private LayoutInflater li;
    public ContratoAdapter(List<Inmueble> listaInmueble, Context context, LayoutInflater li) {
        this.listaInmueble = listaInmueble;
        this.context = context;
        this.li = li;
    }

    @NonNull
    @Override
    public ContratoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=li.inflate(R.layout.card_contrato,parent,false);
        return new ContratoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContratoViewHolder holder, int position) {
        Inmueble i = listaInmueble.get(position);
        holder.tvTipo.setText(i.getTipo());
        holder.tvDireccion.setText(i.getDireccion());
        Glide.with(context)
                .load(ApiClient.URLBASE+i.getImagen())
                .placeholder(R.drawable.inmueble_null)
                .error("null")
                .into(holder.img);
        //holder.verInquilino.setText(holder.verInquilino.getText()+" "+i.getIdInmueble());
        holder.verInquilino.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            bundle.putInt("idInmueble",i.getIdInmueble());
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_nav).navigate(R.id.detalleInquilinoFragment,bundle);
        });
        //holder.verContrato.setText(holder.verContrato.getText()+" "+i.getIdInmueble());
        holder.verContrato.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            bundle.putInt("idInmueble",i.getIdInmueble());
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_nav).navigate(R.id.detalleContratoFragment,bundle);
        });
    }

    @Override
    public int getItemCount() {
        return listaInmueble.size();
    }

    public class ContratoViewHolder extends RecyclerView.ViewHolder{
        TextView tvTipo, tvDireccion;
        ImageView img;
        Button verInquilino, verContrato;

        public ContratoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTipo=itemView.findViewById(R.id.tvIdPagoCardPago);
            tvDireccion=itemView.findViewById(R.id.tvDetalleCardPago);
            img=itemView.findViewById(R.id.imgInmuebleCardContrato);
            verInquilino=itemView.findViewById(R.id.btVerInquilino);
            verContrato=itemView.findViewById(R.id.btVerContrato);
        }
    }
}
