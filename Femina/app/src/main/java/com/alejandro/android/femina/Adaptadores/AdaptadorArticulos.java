package com.alejandro.android.femina.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alejandro.android.femina.Entidades.Articulos;
import com.alejandro.android.femina.Entidades.ArticulosExtra;
import com.alejandro.android.femina.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorArticulos extends ArrayAdapter<ArticulosExtra> implements Filterable {

    protected List<ArticulosExtra> objetos;
    CustomFilter filter;
    List<ArticulosExtra> filterlist;

    public AdaptadorArticulos(Context context, List<ArticulosExtra> objetos) {
        super(context, R.layout.fragment_que_hacer_principal, objetos);
        this.objetos = objetos;
        this.filterlist = objetos;
    }

    @Override
    public int getCount() {
        return objetos.size();
    }

    @Nullable
    @Override
    public ArticulosExtra getItem(int position) {
        return objetos.get(getCount() - position - 1);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.item_articulos, null);


        ImageView imagen_articulo = (ImageView)item.findViewById(R.id.imagen_articulo);
        TextView titulo = (TextView) item.findViewById(R.id.txt_articulo_titulo);
        TextView fecha = (TextView) item.findViewById(R.id.txt_articulo_fecha);
        TextView vistas = (TextView) item.findViewById(R.id.txt_articulo_visualizaciones);
        TextView id_articulo = (TextView) item.findViewById(R.id.txt_id_articulo);

        titulo.setText(getItem(position).getArticulo().getTitulo());
        fecha.setText("" + getItem(position).getArticulo().getFecha_carga());
        vistas.setText(getItem(position).getArticulo().getVistas() + " Visualizaciones");
        id_articulo.setText("" + getItem(position).getArticulo().getId_articulo());
        imagen_articulo.setImageBitmap(getItem(position).getImagen_articulo());


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
                ArrayList<ArticulosExtra> filters = new ArrayList<>();
                for(int i = 0;i<filterlist.size();i++){
                    if(filterlist.get(i).getArticulo().getTitulo().toUpperCase().contains(charSequence)){
                        Articulos art = new Articulos();
                        ArticulosExtra art_ext = new ArticulosExtra();

                        art.setTitulo(filterlist.get(i).getArticulo().getTitulo());
                        art.setId_articulo(filterlist.get(i).getArticulo().getId_articulo());
                        art.setDescripcion(filterlist.get(i).getArticulo().getDescripcion());
                        art.setFecha_carga(filterlist.get(i).getArticulo().getFecha_carga());
                        art.setVistas(filterlist.get(i).getArticulo().getVistas());
                        art_ext.setImagen_articulo(filterlist.get(i).getImagen_articulo());
                        art_ext.setArticulo(art);

                        filters.add(art_ext);

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
            objetos = (ArrayList<ArticulosExtra>)filterResults.values;
            notifyDataSetChanged();
        }
    }

}
