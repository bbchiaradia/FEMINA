package com.alejandro.android.femina.Fragments.que_hacer.Principal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alejandro.android.femina.BD.Articulos.ArticulosBD;
import com.alejandro.android.femina.Dialogos.DialogoAEContactos;
import com.alejandro.android.femina.Dialogos.DialogoSINOArticulos;
import com.alejandro.android.femina.R;

public class QueHacerFragment extends Fragment {

    private QueHacerViewModel queHacerViewModel;
    private Button btn_agregar_contacto;

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

        btn_agregar_contacto = (Button) root.findViewById(R.id.btn_agregar_articulo);

        btn_agregar_contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogoSINOArticulos dialog = new DialogoSINOArticulos();
                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                dialog.show(fragmentManager, "");
            }
        });


        return root;
    }
}