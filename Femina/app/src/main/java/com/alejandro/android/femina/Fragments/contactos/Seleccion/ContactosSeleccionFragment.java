package com.alejandro.android.femina.Fragments.contactos.Seleccion;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;

import com.alejandro.android.femina.Adaptadores.AdaptadorContactos;
import com.alejandro.android.femina.Adaptadores.AdaptadorSeleccionarContacto;
import com.alejandro.android.femina.Entidades.ContactosEmergencia;
import com.alejandro.android.femina.Fragments.contactos.Agregar_editar.ContactosAEFragment;
import com.alejandro.android.femina.Main.MainActivity;
import com.alejandro.android.femina.R;

import java.util.ArrayList;

public class ContactosSeleccionFragment extends Fragment {

    private ArrayList<ContactosEmergencia> arrayList;
    private ArrayList<ContactosEmergencia> array_auxiliar;
    private ArrayList<ContactosEmergencia> array_final;
    private ContactosEmergencia cont;
    private ListView list_contactos;
    private SearchView buscar;
    private int id_contacto;
    private int REQUEST_CONTACT = 2;

    private ContactosSeleccionViewModel contactosSeleccionViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        contactosSeleccionViewModel =
                ViewModelProviders.of(this).get(ContactosSeleccionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_contactos_seleccionar, container, false);
        //final TextView textView = root.findViewById(R.id.txt_contactos);
        contactosSeleccionViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //        textView.setText(s);
            }
        });

        final Bundle datosRecuperados = getArguments();

        id_contacto = -1;

        if (datosRecuperados != null) {
            id_contacto = (datosRecuperados.getInt("idContacto"));
        }

        arrayList = new ArrayList<ContactosEmergencia>();
        array_final = new ArrayList<ContactosEmergencia>();
        array_auxiliar = new ArrayList<ContactosEmergencia>();
        list_contactos = root.findViewById(R.id.list_sel_contactos);
        buscar = root.findViewById(R.id.buscar_contacto);

        list_contactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView numero = (TextView) view.findViewById(R.id.txt_numero_contacto_sel);
                TextView nombre = (TextView) view.findViewById(R.id.txt_nombre_contacto_sel);

                Bundle datosAEnviar = new Bundle();

                Fragment fragmento = new ContactosAEFragment();

                if(id_contacto!=-1){

                    datosAEnviar.putInt("idContacto", id_contacto);
                    datosAEnviar.putString("nombre", nombre.getText().toString());
                    datosAEnviar.putString("telefono", numero.getText().toString());

                    fragmento.setArguments(datosAEnviar);
                    FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_main, fragmento).commit();

                }else{

                    datosAEnviar.putString("nombre", nombre.getText().toString());
                    datosAEnviar.putString("telefono", numero.getText().toString());
                    datosAEnviar.putInt("idContacto", id_contacto);

                    fragmento.setArguments(datosAEnviar);
                    FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_main, fragmento).commit();

                }
            }
        });


        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CONTACT);
        } else {
            getContact();
        }


        return root;
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CONTACT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getContact();
            } else {
                //Toast.makeText(getApplicationContext(), "Permiso RECHAZADO", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void getContact(){


    Cursor cursor = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,null,null,null);

        arrayList.clear();
        array_auxiliar.clear();
        array_final.clear();

    while(cursor.moveToNext()){

        Log.d("Contacto:", cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
        Log.d("Contacto-Numero:", cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));

        String nombre = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
        String numero = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

        cont = new ContactosEmergencia();
        cont.setNombre_contacto(nombre);
        cont.setTelefono(numero);

        arrayList.add(cont);

    }

    for(int i = 0; i< arrayList.size(); i++){

        for(int j = 0; j<arrayList.size(); j++){

            if(i==j){


            }else if (arrayList.get(i).getTelefono().equals(arrayList.get(j).getTelefono()) &&
            arrayList.get(i).getNombre_contacto().equals(arrayList.get(i).getNombre_contacto())){

                arrayList.remove(j);

            }
        }
    }

        final AdaptadorSeleccionarContacto adapter = new AdaptadorSeleccionarContacto(getContext(), arrayList);
        list_contactos.setAdapter(adapter);

        buscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return true;
            }
        });

    }

}