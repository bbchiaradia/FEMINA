package com.alejandro.android.femina.Fragments.testimonios.Videos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alejandro.android.femina.Adaptadores.AdapterVideos;
import com.alejandro.android.femina.Entidades.Videos;
import com.alejandro.android.femina.R;
import java.util.ArrayList;

public class TestimoniosVideosFragment extends Fragment  {

    private AdapterVideos adapter_video;
    RecyclerView recyclerView;
    ArrayList<Videos> youtubeVideos = new ArrayList<>();

    private TestimoniosVideosViewModel testimoniosVideosViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        testimoniosVideosViewModel =
                ViewModelProviders.of(this).get(TestimoniosVideosViewModel.class);
        setHasOptionsMenu(true);// ADD THIS
        View root = inflater.inflate(R.layout.fragment_testimonios_videos, container, false);
        testimoniosVideosViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        fillVideoList();
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));
        adapter_video = new AdapterVideos(youtubeVideos);
        recyclerView.setAdapter(adapter_video);
        return root;
    }

           private void fillVideoList(){
               youtubeVideos.add(new Videos("TOP 10 JUMP ROPE",1,"<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/roN-Kbvn-OI\" frameborder=\"0\" allowfullscreen></iframe>"));
               youtubeVideos.add( new Videos("SIDE-SWING",2,"<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/dqGKhWN9zwU\" frameborder=\"0\" allowfullscreen></iframe>") );
               youtubeVideos.add( new Videos("BEST 360",3,"<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/fg0iEgpSbdA\" frameborder=\"0\" allowfullscreen></iframe>") );
               youtubeVideos.add( new Videos("TECHNICAL JUMP ROPE",4,"<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/r6f2ZoHuwho\" frameborder=\"0\" allowfullscreen></iframe>") );
               youtubeVideos.add( new Videos("JUMP ROPE LIKE A MASTER",5,"<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/5UiQ77kTwLQ\" frameborder=\"0\" allowfullscreen></iframe>") );
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

        inflater.inflate(R.menu.menusearch, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter_video.getFilter().filter(newText);
                return true;
            }
        });
    }
}