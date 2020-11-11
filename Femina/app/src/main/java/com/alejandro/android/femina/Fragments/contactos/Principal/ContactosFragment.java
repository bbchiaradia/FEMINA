package com.alejandro.android.femina.Fragments.contactos.Principal;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alejandro.android.femina.BD.Contactos.ContactosBD;
import com.alejandro.android.femina.Dialogos.DialogoAEContactos;
import com.alejandro.android.femina.Entidades.ContactosEmergencia;
import com.alejandro.android.femina.Entidades.Usuarios;
import com.alejandro.android.femina.Fragments.contactos.Agregar_editar.ContactosAEFragment;
import com.alejandro.android.femina.Fragments.icono.IconoFragment;
import com.alejandro.android.femina.Fragments.testimonios.Principal.TestimoniosFragment;
import com.alejandro.android.femina.Main.MainActivity;
import com.alejandro.android.femina.R;
import com.alejandro.android.femina.Session.SessionContactos;

public class ContactosFragment extends Fragment {

    private ContactosViewModel contactosViewModel;
    private ListView lcontactos;
    private TextView no_hay;
    private Button btn_agregar_contacto;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        contactosViewModel =
                ViewModelProviders.of(this).get(ContactosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_contactos, container, false);

        lcontactos = root.findViewById(R.id.list_contactos);
        no_hay = root.findViewById(R.id.no_hay_contactos);
        btn_agregar_contacto = root.findViewById(R.id.bnt_agregar_contacto);

        ((MainActivity)getContext()).esperando_contactos_true();

        ContactosBD cont = new ContactosBD(getContext(), lcontactos, no_hay, "Listar");
        cont.execute();

        SharedPreferences pref = getContext().getSharedPreferences("accion_contactos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();

        SharedPreferences pref_ = getContext().getSharedPreferences("contactos_vuelta", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_ = pref_.edit();
        editor_.clear();
        editor_.apply();

        SessionContactos sessionContactos = new SessionContactos();
        sessionContactos.setContext(getContext());
        sessionContactos.cerrar_session();

        ContactosBD contactosBD = new ContactosBD(getContext(),"TraerContactosContactos");
        contactosBD.execute();

        btn_agregar_contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fm.beginTransaction().replace(R.id.content_main, new ContactosAEFragment()).commit();

                SharedPreferences preferencias = getContext().getSharedPreferences("accion_contactos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString("accion","Insertar");
                editor.apply();


            }
        });

        lcontactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ContactosEmergencia cont = new ContactosEmergencia();
                Usuarios user = new Usuarios();

                TextView id_contacto = (TextView) view.findViewById(R.id.txt_id_contacto);
                TextView nombre = (TextView) view.findViewById(R.id.txt_nombre_contacto);
                TextView numero = (TextView) view.findViewById(R.id.txt_numero_contacto);

                cont.setId_contacto_emergencia(Integer.parseInt(id_contacto.getText().toString()));
                cont.setNombre_contacto(nombre.getText().toString());
                cont.setTelefono(numero.getText().toString());

                DialogoAEContactos dialog = new DialogoAEContactos(cont);
                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                dialog.show(fragmentManager, "");

            }
        });

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1000);
        }


        contactosViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
        //        textView.setText(s);
            }
        });
        return root;
    }
}
