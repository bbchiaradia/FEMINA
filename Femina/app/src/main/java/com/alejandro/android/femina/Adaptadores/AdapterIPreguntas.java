package com.alejandro.android.femina.Adaptadores;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alejandro.android.femina.Entidades.EntidadIcono;
import com.alejandro.android.femina.Entidades.PreguntasTest;
import com.alejandro.android.femina.R;

import java.util.ArrayList;
public class AdapterIPreguntas extends BaseAdapter {


    private Context context;
    private ArrayList<PreguntasTest> listPreguntas;

    public AdapterIPreguntas(Context context, ArrayList<PreguntasTest> listItems) {
        this.context = context;
        this.listPreguntas = listItems;
    }

    @Override
    public int getCount() {
        return listPreguntas.size();
    }

    @Override
    public Object getItem(int position) {
        return listPreguntas.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PreguntasTest entidad = (PreguntasTest) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item_preguntas_list,null);
        TextView tvTitulo = (TextView) convertView.findViewById(R.id.PreguntaTitulo);
        tvTitulo.setText(entidad.getTexto_pregunta());
        return convertView;
    }
}
