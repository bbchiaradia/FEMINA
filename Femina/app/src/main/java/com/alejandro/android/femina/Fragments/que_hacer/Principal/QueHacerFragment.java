package com.alejandro.android.femina.Fragments.que_hacer.Principal;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
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
import com.alejandro.android.femina.BD.Videos.VideosBD;
import com.alejandro.android.femina.Dialogos.DialogoArticulos;
import com.alejandro.android.femina.Dialogos.DialogoSINOArticulos;
import com.alejandro.android.femina.Entidades.Articulos;
import com.alejandro.android.femina.Fragments.contactos.Agregar_editar.ContactosAEFragment;
import com.alejandro.android.femina.Fragments.que_hacer.User.Detalle.QueHacerDetalleFragment;
import com.alejandro.android.femina.R;
import com.alejandro.android.femina.Session.Session;

public class QueHacerFragment extends Fragment {

    private QueHacerViewModel queHacerViewModel;
    private Button btn_agregar_articulo;
    private SearchView buscar_articulo;
    private TextView no_hay_articulos;
    private Spinner cat_articulos;
    private RadioButton radio_fecha_articulo,radio_relevancia_articulo;
    private ListView lista_articulos;
    private boolean spinner_arranco = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        queHacerViewModel =
                ViewModelProviders.of(this).get(QueHacerViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_que_hacer_principal, container, false);
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


        lista_articulos.addFooterView(new View(getContext()), null, true);
        lista_articulos.addHeaderView(new View(getContext()), null, true);

        radio_fecha_articulo.setChecked(true);

        final Session ses = new Session();
        ses.setCt(getContext());
        ses.cargar_session();

        if(ses.getEs_admin())
            btn_agregar_articulo.setVisibility(View.VISIBLE);
        else
            btn_agregar_articulo.setVisibility(View.GONE);

        btn_agregar_articulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogoSINOArticulos dialog = new DialogoSINOArticulos();
                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                dialog.show(fragmentManager, "");
            }
        });

        ArticulosBD articulosBD = new ArticulosBD(getContext(),lista_articulos,no_hay_articulos,buscar_articulo,
                "",cat_articulos,false,false,"Listar");
        articulosBD.execute();

        cat_articulos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(spinner_arranco) {
                    Log.d("ENTRANDO SPINNER", "ENTRO");
                    if(radio_fecha_articulo.isChecked()){
                        ArticulosBD articulosBD_ = new ArticulosBD(getContext(), lista_articulos, no_hay_articulos, buscar_articulo,
                                cat_articulos.getSelectedItem().toString(), cat_articulos, true, false, "ListarSegunCategoria");
                        articulosBD_.execute();
                    }

                    if(radio_relevancia_articulo.isChecked()) {
                        ArticulosBD articulosBD1 = new ArticulosBD(getContext(), lista_articulos, no_hay_articulos, buscar_articulo,
                                cat_articulos.getSelectedItem().toString(), cat_articulos, false, true, "ListarSegunCategoria");
                        articulosBD1.execute();
                    }
                }
                spinner_arranco = true;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        radio_relevancia_articulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArticulosBD articulosBD = new ArticulosBD(getContext(),lista_articulos,no_hay_articulos,buscar_articulo,
                        cat_articulos.getSelectedItem().toString(),cat_articulos,false,false,"Relevancia");
                articulosBD.execute();
                radio_fecha_articulo.setChecked(false);
            }
        });

        radio_fecha_articulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArticulosBD articulosBD = new ArticulosBD(getContext(),lista_articulos,no_hay_articulos,buscar_articulo,
                        cat_articulos.getSelectedItem().toString(),cat_articulos,false,false,"Fecha");
                articulosBD.execute();
                radio_relevancia_articulo.setChecked(false);
            }
        });

        lista_articulos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Bundle datosAEnviar = new Bundle();

                TextView id_articulo = (TextView) view.findViewById(R.id.txt_id_articulo);

                if(ses.getEs_admin()){

                    Articulos articulos = new Articulos();
                    articulos.setId_articulo(Integer.parseInt(id_articulo.getText().toString()));
                    DialogoArticulos dialogoArticulos = new DialogoArticulos(articulos);
                    FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                    dialogoArticulos.show(fragmentManager, "");

                }else{

                    datosAEnviar.putInt("id_articulo", Integer.parseInt(id_articulo.getText().toString()));

                    Fragment fragmento = new QueHacerDetalleFragment();
                    fragmento.setArguments(datosAEnviar);
                    FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_main, fragmento).commit();

                }


            }
        });


        return root;
    }
}