package com.alejandro.android.femina.Fragments.que_hacer.Principal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alejandro.android.femina.BD.Articulos.ArticulosBD;
import com.alejandro.android.femina.Dialogos.DialogoSINOArticulos;
import com.alejandro.android.femina.R;

public class QueHacerFragment extends Fragment {

    private QueHacerViewModel queHacerViewModel;
    private Button btn_agregar_articulo;
    private SearchView buscar_articulo;
    private TextView no_hay_articulos;
    private Spinner cat_articulos;
    private RadioButton radio_fecha_articulo,radio_relevancia_articulo;
    private ListView lista_articulos;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        queHacerViewModel =
                ViewModelProviders.of(this).get(QueHacerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_que_hacer_principal, container, false);
        //final TextView textView = root.findViewById(R.id.txt_que_hacer);
        queHacerViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);
            }
        });

        btn_agregar_articulo = (Button) root.findViewById(R.id.btn_agregar_articulo);
        buscar_articulo = (SearchView) root.findViewById(R.id.buscar_articulo);
        no_hay_articulos = (TextView) root.findViewById(R.id.no_hay_articulos);
        cat_articulos = (Spinner) root.findViewById(R.id.spn_categoria_articulos);
        radio_fecha_articulo = (RadioButton) root.findViewById(R.id.radio_fecha_articulo);
        radio_relevancia_articulo = (RadioButton) root.findViewById(R.id.radio_relevancia_articulo);
        lista_articulos = (ListView) root.findViewById(R.id.list_articulos);

        btn_agregar_articulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogoSINOArticulos dialog = new DialogoSINOArticulos();
                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                dialog.show(fragmentManager, "");
            }
        });

        ArticulosBD articulosBD = new ArticulosBD(getContext(),lista_articulos,no_hay_articulos,cat_articulos,
                buscar_articulo,"Listar");
        articulosBD.execute();


        return root;
    }
}