package com.alejandro.android.femina.Adaptadores;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filterable;

import com.alejandro.android.femina.Entidades.ContactosEmergencia;
import com.alejandro.android.femina.R;

import java.util.List;

public class AdaptadorPreguntasTest extends ArrayAdapter<ContactosEmergencia> implements Filterable {
    public AdaptadorPreguntasTest(Context context, List<ContactosEmergencia> objetos) {
        super(context, R.layout.fragment_contactos, objetos);
    }
}