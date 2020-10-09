package com.alejandro.android.femina.Adaptadores;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alejandro.android.femina.Entidades.ContactosEmergencia;
import com.alejandro.android.femina.R;

import java.util.List;

public class AdaptadorContactos extends ArrayAdapter<ContactosEmergencia> implements Filterable {

    protected List<ContactosEmergencia> objetos;
    List<ContactosEmergencia> filterlist;

    public AdaptadorContactos(Context context, List<ContactosEmergencia> objetos) {
        super(context, R.layout.fragment_contactos, objetos);
        this.objetos = objetos;
        this.filterlist = objetos;
    }

    @Override
    public int getCount() {
        return objetos.size();
    }

    @Nullable
    @Override
    public ContactosEmergencia getItem(int position) {
        return objetos.get(getCount() - position - 1);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.item_contactos_emergencia, null);


        TextView nombre = (TextView) item.findViewById(R.id.txt_nombre_contacto);
        TextView numero = (TextView) item.findViewById(R.id.txt_numero_contacto);
        TextView id_contacto = (TextView) item.findViewById(R.id.txt_id_contacto);

            nombre.setText( getItem(position).getNombre_contacto());
            numero.setText( getItem(position).getTelefono());
            id_contacto.setText("" + getItem(position).getId_contacto_emergencia());

        return item;
    }

}