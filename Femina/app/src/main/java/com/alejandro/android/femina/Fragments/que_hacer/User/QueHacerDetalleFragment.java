package com.alejandro.android.femina.Fragments.que_hacer.User;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alejandro.android.femina.BD.Articulos.ArticulosBD;
import com.alejandro.android.femina.Entidades.Articulos;
import com.alejandro.android.femina.R;

public class QueHacerDetalleFragment extends Fragment {

    private QueHacerDetalleViewModel queHacerDetalleViewModel;
    private ImageView img;
    private TextView titulo,descripcion,fecha;
    private int id_articulo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        queHacerDetalleViewModel =
                ViewModelProviders.of(this).get(QueHacerDetalleViewModel.class);
        View root = inflater.inflate(R.layout.fragment_que_hacer_detalle, container, false);
        //final TextView textView = root.findViewById(R.id.txt_que_hacer);

        id_articulo = -1;

        img = (ImageView) root.findViewById(R.id.imagen_articulo_detalle);
        titulo = (TextView) root.findViewById(R.id.txt_titulo_articulo_detalle);
        descripcion = (TextView) root.findViewById(R.id.txt_descrip_articulo_detalle);
        fecha = (TextView) root.findViewById(R.id.txt_fecha_articulo_detalle);

        if(getArguments()!=null)
            id_articulo = getArguments().getInt("id_articulo");


        if(id_articulo!=-1){
            Articulos art = new Articulos();
            art.setId_articulo(id_articulo);
            ArticulosBD articulosBD = new ArticulosBD(getContext(),art,img,titulo,descripcion,fecha,"CargarDetalle");
            articulosBD.execute();

        }

        return root;

    }
}