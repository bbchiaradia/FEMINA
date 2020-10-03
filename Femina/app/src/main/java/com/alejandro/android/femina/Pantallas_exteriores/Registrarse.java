package com.alejandro.android.femina.Pantallas_exteriores;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.alejandro.android.femina.R;

public class Registrarse extends AppCompatActivity {

    private Spinner sexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_registrarse);

        sexo = (Spinner) findViewById(R.id.spn_sexo_registrarse);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sexo_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sexo.setAdapter(adapter);

    }
}