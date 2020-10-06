package com.alejandro.android.femina.Adaptadores;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alejandro.android.femina.Entidades.EntidadIcono;
import com.alejandro.android.femina.R;

import java.util.ArrayList;


public class AdapterIconos extends BaseAdapter {

    private Context context;
    private ArrayList<EntidadIcono> listIcono;

    public AdapterIconos(Context context, ArrayList<EntidadIcono> listItems) {
        this.context = context;
        this.listIcono = listItems;
    }

    @Override
    public int getCount() {
        return listIcono.size();
    }

    @Override
    public Object getItem(int position) {
        return listIcono.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EntidadIcono entidad = (EntidadIcono) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item_iconos_list,null);
        ImageView imgIcono=    convertView.findViewById(R.id.imgIcono) ;
        TextView tvTitulo = (TextView) convertView.findViewById(R.id.iconoTitulo);

        imgIcono.setImageResource(entidad.getImgIcono());
        tvTitulo.setText(entidad.getTitulo());

    return convertView;
    }
}
