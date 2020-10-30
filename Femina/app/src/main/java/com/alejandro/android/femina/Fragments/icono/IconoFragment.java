package com.alejandro.android.femina.Fragments.icono;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alejandro.android.femina.Adaptadores.AdapterIconos;
import com.alejandro.android.femina.BuildConfig;
import com.alejandro.android.femina.Dialogos.DialogoArticulos;
import com.alejandro.android.femina.Dialogos.DialogoIconos;
import com.alejandro.android.femina.Entidades.Articulos;
import com.alejandro.android.femina.Entidades.EntidadIcono;
import com.alejandro.android.femina.Fragments.que_hacer.User.Detalle.QueHacerDetalleFragment;
import com.alejandro.android.femina.R;
import com.alejandro.android.femina.Session.Session;
import com.alejandro.android.femina.gestionicono.gestionicono;

import java.util.ArrayList;

public class IconoFragment extends Fragment {

    private IconoViewModel iconoViewModel;
    public ListView lvIconos;
    public AdapterIconos adaptador;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        iconoViewModel =
                ViewModelProviders.of(this).get(IconoViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_icono, container, false);
        //final TextView textView = root.findViewById(R.id.txt_icono);



        iconoViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                lvIconos = (ListView) root.findViewById(R.id.lvIconos);

                adaptador = new AdapterIconos(getContext(), GetArrayIconos());
                lvIconos.setAdapter(adaptador);

                final Session ses = new Session();
                ses.setCt(getContext());
                ses.cargar_session();



                lvIconos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        Bundle datosAEnviar = new Bundle();

                        TextView id_icono = (TextView) view.findViewById(R.id.txt_id_icono);

                           final TextView tituloIcono = (TextView) view.findViewById(R.id.iconoTitulo);


                        AlertDialog.Builder dialogoIcono = new AlertDialog.Builder(getContext());
                        dialogoIcono.setTitle("¿Desea confirmar la selección de este Icono?");
                        dialogoIcono.setCancelable(false);
                        dialogoIcono.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo1, int id) {

                                if (tituloIcono.getText().toString() == "MODA"){
                                    setIcon(gestionicono.ICON_COLOUR.MODA);

                                } else if (tituloIcono.getText().toString() == "HOROSCOPO"){
                                    setIcon(gestionicono.ICON_COLOUR.HOROSCOPO);

                                }else if (tituloIcono.getText().toString() == "PELUQUERIA"){
                                    setIcon(gestionicono.ICON_COLOUR.PELUQUERIA);

                                }else if (tituloIcono.getText().toString() == "RECETAS"){
                                    setIcon(gestionicono.ICON_COLOUR.RECETAS);

                                }else if (tituloIcono.getText().toString() == "FEMINA"){
                                    setIcon(gestionicono.ICON_COLOUR.FEMINA);

                                }



                            }
                        });
                        dialogoIcono.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo1, int id) {
                               // cancelar();
                            }
                        });
                        dialogoIcono.show();




                    }
                });



            }
        });
        return root;
    }


    public void aceptar() {
        Toast t= Toast.makeText(getContext(),"ACEPTA", Toast.LENGTH_SHORT);
        t.show();
    }

    public void cancelar() {
        Toast t= Toast.makeText(getContext(),"Accion Cancelada", Toast.LENGTH_SHORT);
        t.show();
    }

    private ArrayList<EntidadIcono> GetArrayIconos(){
        ArrayList<EntidadIcono> listIconos = new ArrayList<>();

        listIconos.add(new EntidadIcono(R.drawable.icono_horoscopo, "HOROSCOPO"));
        listIconos.add(new EntidadIcono(R.drawable.icono_moda, "MODA"));
        listIconos.add(new EntidadIcono(R.drawable.icono_recetas, "RECETAS"));
        listIconos.add(new EntidadIcono(R.drawable.icono_peluqueria, "PELUQUERIA"));
        listIconos.add(new EntidadIcono(R.drawable.icono_femina, "FEMINA"));

        return listIconos;
    }

    public void setIcon(gestionicono.ICON_COLOUR tgt) {

        gestionicono.ICON_COLOUR[] iconColour = gestionicono.ICON_COLOUR.values();

        for (int i = 0; i < iconColour.length ; ++i) {
            int setting;
            if (iconColour[i] == tgt) {
                setting = PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
            } else {
                setting = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
            }


            ComponentName componentName = new ComponentName(BuildConfig.APPLICATION_ID, BuildConfig.APPLICATION_ID + "." + iconColour[i].name());
            getContext().getPackageManager().setComponentEnabledSetting(componentName,
                    setting,
                    PackageManager.DONT_KILL_APP);
         //   Toast.makeText(getContext(), componentName.toString(), Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(), "Icono " +tgt +" actualizado con EXITO!!!", Toast.LENGTH_SHORT).show();
        }
    }


}
