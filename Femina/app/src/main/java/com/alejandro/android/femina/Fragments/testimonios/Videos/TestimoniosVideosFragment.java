package com.alejandro.android.femina.Fragments.testimonios.Videos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alejandro.android.femina.Adaptadores.AdapterVideos;
import com.alejandro.android.femina.BD.Videos.VideosBD;
import com.alejandro.android.femina.Entidades.Videos;
import com.alejandro.android.femina.R;
import java.util.ArrayList;

public class TestimoniosVideosFragment extends Fragment  {

    private AdapterVideos adapter_video;
    RecyclerView recyclerView;
    ArrayList<Videos> youtubeVideos = new ArrayList<>();
    private TextView no_hay;
    private SearchView buscar;
    private Spinner spinner,spinner_editar;
    private SearchView sv;
    private boolean spinner_arranco = false;

    private TestimoniosVideosViewModel testimoniosVideosViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        testimoniosVideosViewModel =
                ViewModelProviders.of(this).get(TestimoniosVideosViewModel.class);
        setHasOptionsMenu(true);// ADD THIS
        View root = inflater.inflate(R.layout.fragment_testimonios_videos, container, false);
        no_hay = root.findViewById(R.id.no_hay_videos);
        buscar = (SearchView) root.findViewById(R.id.action_search);
        testimoniosVideosViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
 /*       recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));*/

        spinner = (Spinner) root.findViewById(R.id.spn_categoria);
        //spinner_editar = (Spinner) root.findViewById(R.id.spn_categoria_am);
        // llamando a Async Task
        VideosBD vid = new VideosBD(getContext(),"CargarSpinner",spinner);
        vid.execute();


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(spinner_arranco) {
                    // llamando a Async Task
                    VideosBD v = new VideosBD(getContext(), adapter_video, youtubeVideos, no_hay, "SelCategoria", sv, spinner.getSelectedItem().toString());
                    v.execute();
                }
                spinner_arranco = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        return root;
    }


//    Se agrego un widget SearchView para filtrar un RecyclerView en tiempo real.
//    Agregando un SearchView como un elemento a nuestro menú menusearch y lo haremos expandible con el atributo collapseActionView.
//    De esta manera podemos mostrarlo como un icono en nuestra barra de aplicaciones, que se expande a un campo de entrada cuando hacemos clic en él.
//    Implementamos la interfaz filtrable en nuestro AdapterVideos RecyclerView y creamos nuestro propio filtro, donde manejamos la lógica del filtro en el método performFiltering asíncrono,
//    que publica los resultados de la búsqueda en el método publishResults en el hilo de la interfaz de usuario.
//    En nuestra actividad, luego conectamos nuestro SearchView con el filtro configurando un OnQueryTextListener
//    en nuestro SearchView y escuchando la entrada de texto en onQueryTextChange.
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();

        inflater.inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setVisible(true);

        sv = (SearchView) item.getActionView();
        sv.setImeOptions(EditorInfo.IME_ACTION_DONE);

        VideosBD v = new VideosBD(getContext(), adapter_video, youtubeVideos,no_hay,"Listar",sv,"");
        v.execute();

    }
}