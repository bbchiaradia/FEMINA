package com.alejandro.android.femina.Fragments.contactos.Agregar_editar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alejandro.android.femina.BD.Contactos.ContactosBD;
import com.alejandro.android.femina.Entidades.ContactosEmergencia;
import com.alejandro.android.femina.Entidades.Usuarios;
import com.alejandro.android.femina.Fragments.contactos.Seleccion.ContactosSeleccionFragment;
import com.alejandro.android.femina.R;
import com.alejandro.android.femina.Session.Session;

import static android.view.MenuItem.SHOW_AS_ACTION_ALWAYS;
import static android.view.MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW;
import static android.view.MenuItem.SHOW_AS_ACTION_IF_ROOM;
import static android.view.MenuItem.SHOW_AS_ACTION_WITH_TEXT;

public class ContactosAEFragment extends Fragment {

    private ContactosAEViewModel contactosAEViewModel;
    private EditText nombre,telefono;
    private Button guardar_cambios_contacto;
    private ContactosEmergencia cont;
    private Usuarios user;
    private Session ses;
    private int id_contacto;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        contactosAEViewModel =
                ViewModelProviders.of(this).get(ContactosAEViewModel.class);
        View root = inflater.inflate(R.layout.fragment_contactos_ae, container, false);
        setHasOptionsMenu(true);

        guardar_cambios_contacto = (Button) root.findViewById(R.id.btn_confirmar_registro_contactos);
        nombre = (EditText) root.findViewById(R.id.txt_nombre_contacto_ae);
        telefono = (EditText) root.findViewById(R.id.txt_telefono_contacto_ae);

        ses = new Session();
        ses.setCt(getContext());
        ses.cargar_session();

        id_contacto = -1;


        final Bundle datosRecuperados = getArguments();

        if (datosRecuperados != null) {
            telefono.setText(datosRecuperados.getString("telefono"));
            nombre.setText(datosRecuperados.getString("nombre"));
            //if(id_contacto!=-1)
            id_contacto = (datosRecuperados.getInt("idContacto"));

            Log.d("id_contacto","" + id_contacto);
        }

        guardar_cambios_contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!telefono.getText().toString().isEmpty() && !nombre.getText().toString().isEmpty()){

                    cont = new ContactosEmergencia();
                    user = new Usuarios();

                    user.setId_usuario(ses.getId_usuario());
                    cont.setTelefono(telefono.getText().toString());
                    cont.setNombre_contacto(nombre.getText().toString());
                    cont.setId_usuario(user);

                    if(id_contacto !=-1)
                        cont.setId_contacto_emergencia(id_contacto);

                    if(cont.validarContacto().equals("si")){

                        SharedPreferences preferences = getContext().getSharedPreferences("accion_contactos", Context.MODE_PRIVATE);

                        if(preferences.getString("accion","").equals("Insertar")) {
                            ContactosBD contbd = new ContactosBD(cont, getContext(), "Insertar");
                            contbd.execute();
                        }

                        if(preferences.getString("accion","").equals("Modificar")) {
                            ContactosBD contbd = new ContactosBD(cont, getContext(), "Modificar");
                            contbd.execute();
                        }

                    }else
                        Toast.makeText(getContext(),cont.validarContacto(), Toast.LENGTH_SHORT).show();


                }else
                    Toast.makeText(getContext(),"Completa todos los campos!",Toast.LENGTH_SHORT).show();
            }
        });


        //final TextView textView = root.findViewById(R.id.txt_contactos);
        contactosAEViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //        textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();

        inflater.inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.btn_agregar_existente);
        item.setVisible(true);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);

        //item.setShowAsAction(SHOW_AS_ACTION_WITH_TEXT);


       /* Button button = (Button) item.getActionView();
        //button.setImeOptions(EditorInfo.IME_ACTION_NONE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new ContactosSeleccionFragment();
                Bundle datosAEnviar = new Bundle();

                SharedPreferences preferences = getContext().getSharedPreferences("accion_contactos", Context.MODE_PRIVATE);

                Log.d("Accion",preferences.getString("accion",""));

                if(preferences.getString("accion","").equals("Insertar")) {

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fm.beginTransaction().replace(R.id.content_main, fragment).commit();
                }

                if(preferences.getString("accion","").equals("Modificar")) {
                    datosAEnviar.putInt("idContacto", id_contacto);
                    fragment.setArguments(datosAEnviar);
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fm.beginTransaction().replace(R.id.content_main, fragment).commit();
                }

            }
        });*/

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_agregar_existente:

                Fragment fragment = new ContactosSeleccionFragment();
                Bundle datosAEnviar = new Bundle();

                SharedPreferences preferences = getContext().getSharedPreferences("accion_contactos", Context.MODE_PRIVATE);

                Log.d("Accion",preferences.getString("accion",""));

                if(preferences.getString("accion","").equals("Insertar")) {

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fm.beginTransaction().replace(R.id.content_main, fragment).commit();
                }

                if(preferences.getString("accion","").equals("Modificar")) {
                    datosAEnviar.putInt("idContacto", id_contacto);
                    fragment.setArguments(datosAEnviar);
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fm.beginTransaction().replace(R.id.content_main, fragment).commit();
                }

                return true;


        }

        return false;

    }
}