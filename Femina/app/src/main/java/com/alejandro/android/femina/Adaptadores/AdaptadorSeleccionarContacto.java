package com.alejandro.android.femina.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alejandro.android.femina.Entidades.ContactosEmergencia;
import com.alejandro.android.femina.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorSeleccionarContacto extends ArrayAdapter<ContactosEmergencia> implements Filterable {

    protected List<ContactosEmergencia> objetos;
    CustomFilter filter;
    List<ContactosEmergencia> filterlist;

    public AdaptadorSeleccionarContacto(Context context, List<ContactosEmergencia> objetos) {
        super(context, R.layout.fragment_contactos_seleccionar, objetos);
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
        View item = inflater.inflate(R.layout.item_contactos_seleccionar, null);

        TextView nombre = (TextView) item.findViewById(R.id.txt_nombre_contacto_sel);
        TextView numero = (TextView) item.findViewById(R.id.txt_numero_contacto_sel);

        nombre.setText(getItem(position).getNombre_contacto());
        numero.setText(getItem(position).getTelefono());

        return item;
    }


    @NonNull
    @Override
    public Filter getFilter() {
        if(filter==null){
            filter = new CustomFilter();
        }
        return filter;
    }

    class CustomFilter extends Filter{


        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            if(charSequence!=null && charSequence.length()>0){

                charSequence = charSequence.toString().toUpperCase();
                ArrayList<ContactosEmergencia> filters = new ArrayList<>();
                for(int i = 0;i<filterlist.size();i++){
                    if(filterlist.get(i).getNombre_contacto().toUpperCase().contains(charSequence)){
                        ContactosEmergencia c = new ContactosEmergencia();
                        c.setNombre_contacto(filterlist.get(i).getNombre_contacto());
                        c.setTelefono(filterlist.get(i).getTelefono());
                        filters.add(c);

                    }

                }
                results.count = filters.size();
                results.values = filters;

            }else{

                results.count = filterlist.size();
                results.values = filterlist;

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            objetos = (ArrayList<ContactosEmergencia>)filterResults.values;
            notifyDataSetChanged();
        }
    }

}
