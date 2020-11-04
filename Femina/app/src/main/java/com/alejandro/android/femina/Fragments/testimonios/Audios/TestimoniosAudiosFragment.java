package com.alejandro.android.femina.Fragments.testimonios.Audios;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alejandro.android.femina.Adaptadores.AdaptadorAudios;
import com.alejandro.android.femina.BD.Audios.AudiosBD;
import com.alejandro.android.femina.Entidades.Audios;
import com.alejandro.android.femina.Fragments.testimonios.Admin.AMAudios.TestimoniosAMAudiosFragment;
import com.alejandro.android.femina.R;
import com.alejandro.android.femina.Session.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TestimoniosAudiosFragment extends Fragment implements MediaPlayerUtils.Listener {
    public Context context;
    private ArrayList<Audios> contactList = new ArrayList<Audios>();
    public List<AudioEstado> audioStatusList = new ArrayList<>();
    public Parcelable state;
    RecyclerView recyclerView;
    private TestimoniosAudiosViewModel testimoniosAudiosViewModel;
    private TextView no_hay;
    AdaptadorAudios adapter;
    private String search;
    private Spinner spinner;
    private boolean spinner_arranco = false;
    SearchView sv;
    private SearchView buscar;
    private ArrayList<Audios> audiosAux = new ArrayList<Audios>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        testimoniosAudiosViewModel =
                ViewModelProviders.of(this).get(TestimoniosAudiosViewModel.class);
        setHasOptionsMenu(true);// ADD THIS
        View root = inflater.inflate(R.layout.fragment_testimonios_audios, container, false);
        no_hay = root.findViewById(R.id.no_hay_audios);
        recyclerView = root.findViewById(R.id.recyclerView_audio);
        //final TextView textView = root.findViewById(R.id.txt_testimonios);
        testimoniosAudiosViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        context = getActivity();
        spinner = (Spinner) root.findViewById(R.id.spn_categoria_aud);
        // llamando a Async Task
        final AudiosBD audiosBD_sp = new AudiosBD(context, "CargarSpinner", spinner, this);
        audiosBD_sp.execute();


        // llamando a Async Task
        AudiosBD audiosBD = new AudiosBD(getContext(), contactList, no_hay, "Listar", this, recyclerView);
        audiosBD.execute();

        search = "";

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (spinner_arranco) {
                    LaodSelectedSpinner(spinner.getSelectedItem().toString());
                }
                spinner_arranco = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }
        });

        return root;
    }

    public void LaodSelectedSpinner(String Cat) {
        // llamando a Async Task
        AudiosBD audiosBdSelSpn = new AudiosBD(getContext(), contactList, "SelCategoria", Cat, no_hay, this, recyclerView);
        audiosBdSelSpn.execute();

    }

    public void getAudioList() {

        SharedPreferences prefs = getContext().getSharedPreferences("lista_audios", Context.MODE_PRIVATE);
        try {
            contactList = (ArrayList<Audios>) ObjectSerializer.deserialize(prefs.getString("AudioList", ObjectSerializer.serialize(new ArrayList<Audios>())));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        setRecyclerViewAdapter(contactList);

        for (int i = -1; i < contactList.size(); i++) {
            audioStatusList.add(new AudioEstado(AudioEstado.AUDIO_STATE.IDLE.ordinal(), -1));
        }
    }

    private void setRecyclerViewAdapter(List<Audios> contactList) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new AdaptadorAudios(context, contactList, this, this);
        adapter.getFilter().filter(search);
        recyclerView.setAdapter(adapter);


      //  adapter.notifyDataSetChanged();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onPause() {
        super.onPause();
        // Store its state
        state = Objects.requireNonNull(recyclerView.getLayoutManager()).onSaveInstanceState();
        MediaPlayerUtils.releaseMediaPlayer();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResume() {
        super.onResume();
        //Posición principal de RecyclerView cuando se carga de nuevo
        if (state != null) {
            Objects.requireNonNull(recyclerView.getLayoutManager()).onRestoreInstanceState(state);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MediaPlayerUtils.releaseMediaPlayer();
    }

    @Override
    public void onAudioUpdate(int currentPosition) {

        Log.d("onUpdate","Estoy_onUpdate");
        int playingAudioPosition = -1;
        for (int i = 0; i < audioStatusList.size(); i++) {
            AudioEstado audioStatus = audioStatusList.get(i);
            if (audioStatus.getAudioState() == AudioEstado.AUDIO_STATE.PLAYING.ordinal()) {
                playingAudioPosition = i;
                break;
            }
        }

        if (playingAudioPosition != -1) {
            AdaptadorAudios.AudioViewHolder holder
                    = (AdaptadorAudios.AudioViewHolder) recyclerView.findViewHolderForAdapterPosition(playingAudioPosition);
            if (holder != null) {
                holder.seekBarAudio.setProgress(currentPosition);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onAudioComplete() {

         SharedPreferences prefs = getContext().getSharedPreferences("lista_audios", Context.MODE_PRIVATE);
        try {
            audiosAux = (ArrayList<Audios>) ObjectSerializer.deserialize(prefs.getString("AudioList", ObjectSerializer.serialize(new ArrayList<Audios>())));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        //Almacenar su estado
        state = Objects.requireNonNull(recyclerView.getLayoutManager()).onSaveInstanceState();

        audioStatusList.clear();
       /* for (int i = 0; i < contactList.size(); i++) {
            audioStatusList.add(new AudioEstado(AudioEstado.AUDIO_STATE.IDLE.ordinal(), 0));
        }
        Log.d("onComplete","Estoy_onComplete");
        setRecyclerViewAdapter(contactList);
*/

        for (int i = 0; i < audiosAux.size(); i++) {
            audioStatusList.add(new AudioEstado(AudioEstado.AUDIO_STATE.IDLE.ordinal(), 0));
        }
        Log.d("onComplete","Estoy_onComplete");
        Log.d("AuxSize","LisAux: " + audiosAux.size());
        setRecyclerViewAdapter(audiosAux);



        //Posición principal de RecyclerView cuando se carga de nuevo
        if (state != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(state);
        }
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
        if (ses.getEs_admin()) {
            MenuItem menuItem = menu.findItem(R.id.add_video);
            menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
            menuItem.setVisible(true);
        }

        sv = (SearchView) item.getActionView();
        sv.setImeOptions(EditorInfo.IME_ACTION_DONE);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                    adapter.getFilter().filter(newText);
                    search = newText;
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // Toast.makeText(getContext(),"Accion buscar Video",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.add_video:
                Toast.makeText(getContext(), "Accion ingresar Video", Toast.LENGTH_SHORT).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fm.beginTransaction().replace(R.id.content_main, new TestimoniosAMAudiosFragment()).commit();

                SharedPreferences preferencias = getContext().getSharedPreferences("accion_videos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString("accion", "Insertar");
                editor.apply();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
