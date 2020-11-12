package com.alejandro.android.femina.Adaptadores;


import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alejandro.android.femina.Entidades.Ayuda;
import com.alejandro.android.femina.Entidades.AyudaList;
import com.alejandro.android.femina.R;

import java.util.ArrayList;


public class AdapterAyuda extends BaseAdapter {

    private Context context;
    private ArrayList<AyudaList> listAyuda;

    public AdapterAyuda(Context context, ArrayList<AyudaList> listAyuda) {
        this.context = context;
        this.listAyuda = listAyuda;
    }

    @Override
    public int getCount() {
        return listAyuda.size();
    }

    @Override
    public Object getItem(int position) {
        return listAyuda.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AyudaList entidad = (AyudaList) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item_ayuda_list,null);
        ImageView imgIcono=convertView.findViewById(R.id.imgAyuda) ;
        TextView tvTitulo = (TextView) convertView.findViewById(R.id.ayudaTitulo);
        TextView tvDescripcion = (TextView) convertView.findViewById(R.id.ayudaDescripcion);


        imgIcono.setImageResource(entidad.getImgAyuda());
        tvTitulo.setText(entidad.getTitulo());
        tvDescripcion.setText(entidad.getDescripcion());

    return convertView;
    }
}
