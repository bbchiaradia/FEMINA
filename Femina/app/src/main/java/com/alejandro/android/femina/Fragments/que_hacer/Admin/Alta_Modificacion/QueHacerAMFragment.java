package com.alejandro.android.femina.Fragments.que_hacer.Admin.Alta_Modificacion;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alejandro.android.femina.BD.Articulos.ArticulosBD;
import com.alejandro.android.femina.BD.Contactos.ContactosBD;
import com.alejandro.android.femina.BD.Videos.VideosBD;
import com.alejandro.android.femina.Dialogos.DialogoEditarFoto;
import com.alejandro.android.femina.Dialogos.DialogoSINOBajaArticulos;
import com.alejandro.android.femina.Entidades.Articulos;
import com.alejandro.android.femina.Entidades.Categorias;
import com.alejandro.android.femina.R;

public class QueHacerAMFragment extends Fragment {

    private QueHacerAMViewModel queHacerAMViewModel;
    private int id_articulo;
    private ImageButton imageButton;
    private EditText txt_titulo,descripcion;
    private Button btn_guardar_cambios,btn_borrar_articulo;
    private Spinner spn_cat;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        queHacerAMViewModel =
                ViewModelProviders.of(this).get(QueHacerAMViewModel.class);
        View root = inflater.inflate(R.layout.fragment_que_hacer_am, container, false);
        //final TextView textView = root.findViewById(R.id.txt_que_hacer);
        queHacerAMViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // textView.setText(s);
            }
        });

        id_articulo = -1;


        final Bundle datosRecuperados = getArguments();

        if (datosRecuperados != null) {
            id_articulo = datosRecuperados.getInt("id_articulo");
        }

        imageButton = (ImageButton) root.findViewById(R.id.editar_foto);
        txt_titulo = (EditText) root.findViewById(R.id.txt_titulo_articulo_edit);
        descripcion = (EditText) root.findViewById(R.id.txt_descrip_articulo_edit);
        btn_guardar_cambios = (Button) root.findViewById(R.id.btn_guardar_articulo);
        btn_borrar_articulo = (Button) root.findViewById(R.id.btn_borrar_articulo);
        spn_cat = (Spinner) root.findViewById(R.id.spn_cat_articulos);



        if(id_articulo != -1){

            Log.d("ID_ARTICULO","ENTRA");

            Articulos art_ = new Articulos();
            art_.setId_articulo(id_articulo);

            ArticulosBD art = new ArticulosBD(getContext(),art_,imageButton,txt_titulo,descripcion,
                    spn_cat,"CargarArticulo");
            art.execute();


        }


        btn_guardar_cambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id_articulo!=-1){

                    if(!txt_titulo.getText().toString().isEmpty() && !descripcion.getText().toString().isEmpty()){

                        Articulos art_ = new Articulos();
                        Categorias cat = new Categorias();

                        cat.setDescripcion(spn_cat.getSelectedItem().toString());
                        art_.setId_articulo(id_articulo);
                        art_.setDescripcion(descripcion.getText().toString());
                        art_.setTitulo(txt_titulo.getText().toString());
                        art_.setId_categoria(cat);

                        ArticulosBD art = new ArticulosBD(getContext(),art_,"Modificar");
                        art.execute();

                    }else
                        Toast.makeText(getContext(),"Complete todos los campos!",Toast.LENGTH_SHORT).show();

                }else
                    Toast.makeText(getContext(),"Error al buscar id articulo",Toast.LENGTH_SHORT).show();
            }
        });

        btn_borrar_articulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id_articulo!=-1){

                    Articulos art_ = new Articulos();
                    art_.setId_articulo(id_articulo);

                    DialogoSINOBajaArticulos dialogoSINOBajaArticulos = new DialogoSINOBajaArticulos(art_);
                    FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                    dialogoSINOBajaArticulos.show(fragmentManager,"");

                }else
                    Toast.makeText(getContext(),"Error al buscar id articulo",Toast.LENGTH_SHORT).show();
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id_articulo !=-1) {


                    if((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) &&
                            (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_MEDIA_LOCATION)
                                    != PackageManager.PERMISSION_GRANTED)){
                        requestPermissions(new String[]{Manifest.permission.ACCESS_MEDIA_LOCATION},1);
                    }else
                    {
                        Articulos art = new Articulos();
                        art.setId_articulo(id_articulo);
                        DialogoEditarFoto dialog = new DialogoEditarFoto(art);
                        FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                        dialog.show(fragmentManager, "");
                    }

                }
                else
                    Toast.makeText(getContext(),"Error al recuperar idArticulo",Toast.LENGTH_SHORT).show();
            }
        });


        return root;
    }
}