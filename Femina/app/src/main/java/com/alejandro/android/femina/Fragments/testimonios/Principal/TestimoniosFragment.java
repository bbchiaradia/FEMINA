package com.alejandro.android.femina.Fragments.testimonios.Principal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alejandro.android.femina.Fragments.testimonios.Videos.TestimoniosVideosFragment;
import com.alejandro.android.femina.R;

public class TestimoniosFragment extends Fragment {

    CardView cardVideo;

    private TestimoniosViewModel testimoniosViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        testimoniosViewModel =
                ViewModelProviders.of(this).get(TestimoniosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_testimonios, container, false);
        //final TextView textView = root.findViewById(R.id.txt_testimonios);
        testimoniosViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        cardVideo=root.findViewById(R.id.cardVideo);
        cardVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear fragmento de tu clase
                Fragment fragment = new TestimoniosVideosFragment();
                // Obtener el administrador de fragmentos a través de la actividad
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                // Definir una transacción
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // Remplazar el contenido principal por el fragmento
                fragmentTransaction.replace(R.id.content_main, fragment);
                fragmentTransaction.addToBackStack(null);
                // Cambiar
                fragmentTransaction.commit();
            }
        });


        return root;
    }
}