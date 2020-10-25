package com.example.lab05mejoraaspectovisual;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.text.LineBreaker;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.FutbolistaViewHolder> {

    List<Futbolista> futbolistas;
    Context context;

    public RVAdapter(List<Futbolista> futbolistas, Context context) {
        this.futbolistas = futbolistas;
        this.context = context;
    }

    @NonNull
    @Override
    public FutbolistaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.futbolista,parent,false);
        return new FutbolistaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FutbolistaViewHolder holder, int position) {
        //elementos
        holder.nombreFutbolista.setText(futbolistas.get(position).nombre);
        holder.seleccionFutbolista.setText(context.getResources().getString(R.string.seleccion) + futbolistas.get(position).seleccion);
        holder.clubFutbolista.setText(context.getResources().getString(R.string.club) + futbolistas.get(position).club);
        holder.edadFutbolista.setText(context.getResources().getString(R.string.edad) + futbolistas.get(position).edad + context.getResources().getString(R.string.años));
        holder.infoFutbolista.setText(futbolistas.get(position).info);
        holder.linkFutbolista.setText(futbolistas.get(position).link);
        Drawable original = context.getResources().getDrawable(futbolistas.get(position).foto,null);
        Bitmap originalBitmap = ((BitmapDrawable)original).getBitmap();
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(),originalBitmap);
        roundedBitmapDrawable.setCornerRadius(originalBitmap.getHeight());
        holder.fotoFutbolista.setImageDrawable(roundedBitmapDrawable);
        //eventos del boton
        holder.setOnClickListeners();
    }

    @Override
    public int getItemCount() { return futbolistas.size(); }

    public static class FutbolistaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardView;
        TextView nombreFutbolista;
        TextView seleccionFutbolista;
        TextView clubFutbolista;
        TextView edadFutbolista;
        TextView infoFutbolista;
        TextView linkFutbolista;
        ImageView fotoFutbolista;
        Button btnMasInfo;
        Dialog dialog;

        public FutbolistaViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.cv);
            nombreFutbolista = (TextView) itemView.findViewById(R.id.nombreFutbolista);
            seleccionFutbolista = (TextView) itemView.findViewById(R.id.seleccionFutbolista);
            clubFutbolista = (TextView) itemView.findViewById(R.id.clubFutbolista);
            edadFutbolista = (TextView) itemView.findViewById(R.id.edadFutbolista);
            infoFutbolista = (TextView) itemView.findViewById(R.id.infoFutbolista);
            linkFutbolista = (TextView) itemView.findViewById(R.id.linkFutbolista);
            fotoFutbolista = (ImageView) itemView.findViewById(R.id.fotoFutbolista);
            btnMasInfo = (Button)itemView.findViewById(R.id.btnMasInfo);
        }

        public void setOnClickListeners() {
            btnMasInfo.setOnClickListener(this);
            fotoFutbolista.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnMasInfo:
                    crearPopUpDesdeBoton(itemView,nombreFutbolista,infoFutbolista,linkFutbolista);
                    break;
                case R.id.fotoFutbolista:
                    crearPopUpDesdeImages(itemView,nombreFutbolista,fotoFutbolista);
                    break;
            }
        }

        private void crearPopUpDesdeBoton(final View itemView, TextView nombreFutbolista, TextView infoFutbolista, final TextView linkFutbolista) {
            //creando dialog
            dialog = new Dialog(itemView.getContext());
            dialog.setContentView(R.layout.pop_up_boton);
            dialog.setTitle(nombreFutbolista.getText().toString());
            //creando objetos
            TextView titulo = (TextView) dialog.findViewById(R.id.nombreFutbolista);
            TextView descripcion = (TextView) dialog.findViewById(R.id.infoFutbolista);
            Button btnVerMas = (Button)dialog.findViewById(R.id.btnVerMas);
            //dando valor a los objetos
            titulo.setText(nombreFutbolista.getText().toString());
            descripcion.setText(infoFutbolista.getText().toString());
            //evento del boton
            btnVerMas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse((String)linkFutbolista.getText());
                    Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                    itemView.getContext().startActivity(intent);
                }
            });
            descripcion.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
            //Iniciando pop-up
            dialog.show();
        }

        private void crearPopUpDesdeImages(final View itemView, TextView nombreFutbolista, ImageView fotoFutbolista) {
            try {
                //creando dialog
                dialog = new Dialog(itemView.getContext());
                dialog.setContentView(R.layout.pop_up_imagen);
                dialog.setTitle(nombreFutbolista.getText().toString());
                //creando objetos
                TextView titulo = (TextView) dialog.findViewById(R.id.nombreFutbolista);
                ImageView imagen = (ImageView) dialog.findViewById(R.id.fotoFutbolista);
                //dando valor a los objetos
                titulo.setText(nombreFutbolista.getText().toString());
                imagen.setImageDrawable(fotoFutbolista.getDrawable());
                //Iniciando pop-up
                dialog.show();
            } catch (Exception e){
                Toast.makeText(itemView.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}