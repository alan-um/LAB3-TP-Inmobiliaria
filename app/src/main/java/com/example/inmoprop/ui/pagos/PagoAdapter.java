package com.example.inmoprop.ui.pagos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inmoprop.R;
import com.example.inmoprop.models.Inmueble;
import com.example.inmoprop.models.Pago;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PagoAdapter extends RecyclerView.Adapter<PagoAdapter.PagoViewHolder> {
    private List<Pago> listaPagos;
    private Context context;
    private LayoutInflater li;
    public PagoAdapter(List<Pago> listaPagos, Context context, LayoutInflater li) {
        this.listaPagos = listaPagos;
        this.context = context;
        this.li = li;
    }

    @NonNull
    @Override
    public PagoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=li.inflate(R.layout.card_pago,parent,false);
        return new PagoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PagoViewHolder holder, int position) {
        Pago p = listaPagos.get(position);
        holder.tvIdPago.setText("N° Pago: "+p.getIdPago());
        holder.tvMonto.setText("Monto: $ "+p.getMonto());
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate ldFecha = LocalDate.ofInstant(p.getFechaPago().toInstant(), ZoneId.of("GMT"));
        holder.tvFecha.setText("Fecha: "+ldFecha.format(formatoFecha));
        holder.tvDetalle.setText(p.getDetalle());
    }

    @Override
    public int getItemCount() {
        return listaPagos.size();
    }

    public class PagoViewHolder extends RecyclerView.ViewHolder{
        TextView tvIdPago, tvMonto, tvFecha, tvDetalle;
        public PagoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdPago=itemView.findViewById(R.id.tvIdPagoCardPago);
            tvMonto=itemView.findViewById(R.id.tvMontoCardPago);
            tvFecha=itemView.findViewById(R.id.tvFechaCardPago);
            tvDetalle=itemView.findViewById(R.id.tvDetalleCardPago);
        }
    }
}
