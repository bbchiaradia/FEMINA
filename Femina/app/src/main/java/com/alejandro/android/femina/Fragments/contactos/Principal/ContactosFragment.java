package com.alejandro.android.femina.Fragments.contactos.Principal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alejandro.android.femina.BD.Contactos.ContactosBD;
import com.alejandro.android.femina.Fragments.contactos.Agregar_editar.ContactosAEFragment;
import com.alejandro.android.femina.Fragments.icono.IconoFragment;
import com.alejandro.android.femina.Fragments.testimonios.Principal.TestimoniosFragment;
import com.alejandro.android.femina.R;

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

        ContactosBD cont = new ContactosBD(getContext(),lcontactos,no_hay,"Listar");
        cont.execute();

        btn_agregar_contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fm.beginTransaction().replace(R.id.content_main, new ContactosAEFragment()).commit();
            }
        });

        contactosViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
        //        textView.setText(s);
            }
        });
        return root;
    }
}
