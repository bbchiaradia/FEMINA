package com.alejandro.android.femina.Fragments.testimonios.Videos;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alejandro.android.femina.Adaptadores.AdapterVideos;
import com.alejandro.android.femina.BD.Videos.VideosBD;
import com.alejandro.android.femina.Entidades.Videos;
import com.alejandro.android.femina.Fragments.contactos.Agregar_editar.ContactosAEFragment;
import com.alejandro.android.femina.Fragments.testimonios.Admin.AMVideos.TestimoniosAMVideosFragment;
import com.alejandro.android.femina.R;
import com.alejandro.android.femina.Session.Session;

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
    private Button btnfs ;

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
        btnfs = (Button) root.findViewById(R.id.fullscreen);
        spinner = (Spinner) root.findViewById(R.id.spn_categoria);
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

   @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        // cargo la sesion de usuario
       final Session ses = new Session();
       ses.setCt(getContext());
       ses.cargar_session();

        inflater.inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
       item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
       item.setVisible(true);
       // Solo es visible para el Admin
       if(ses.getEs_admin()) {
           MenuItem menuItem = menu.findItem(R.id.add_video);
           menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
           menuItem.setVisible(true);
       }
        sv = (SearchView) item.getActionView();
        sv.setImeOptions(EditorInfo.IME_ACTION_DONE);
        Log.d("EntrandoDB", "EnDB");
        VideosBD v = new VideosBD(getContext(), adapter_video, youtubeVideos,no_hay,"Listar",sv,"");
        v.execute();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_search:
               // Toast.makeText(getContext(),"Accion buscar Video",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.add_video:
                Toast.makeText(getContext(),"Accion ingresar Video",Toast.LENGTH_SHORT).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fm.beginTransaction().replace(R.id.content_main, new TestimoniosAMVideosFragment()).commit();

                SharedPreferences preferencias = getContext().getSharedPreferences("accion_videos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString("accion","Insertar");
                editor.apply();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}