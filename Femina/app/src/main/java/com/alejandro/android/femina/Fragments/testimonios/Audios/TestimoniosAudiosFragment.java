package com.alejandro.android.femina.Fragments.testimonios.Audios;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alejandro.android.femina.Adaptadores.AdaptadorAudios;
import com.alejandro.android.femina.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

 public class TestimoniosAudiosFragment extends Fragment implements MediaPlayerUtils.Listener {
     public static  MediaPlayerUtils mediaPlayerUtils;
     public Context context;
    private final List<String> contactList = new ArrayList<>();
    public  List<AudioEstado> audioStatusList = new ArrayList<>();
    public Parcelable state;
    RecyclerView recyclerView;
    private TestimoniosAudiosViewModel testimoniosAudiosViewModel;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        testimoniosAudiosViewModel =
                ViewModelProviders.of(this).get(TestimoniosAudiosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_testimonios_audios, container, false);
        //final TextView textView = root.findViewById(R.id.txt_testimonios);
        testimoniosAudiosViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        context = getActivity();
        recyclerView = root.findViewById(R.id.recyclerView_audio);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        contactList.add("http://infinityandroid.com/music/good_times.mp3"); // URL of audio file
        contactList.add("https://www.comecuco.org/sites/default/files/1.mp3"); // URL of audio file
        contactList.add("https://www.shalombait.org.ar/wp-content/uploads/2019/07/Audio-Guia-Orientativa-para-realizar-una-Denuncia-por-Violencia-de-Ge%CC%81nero-en-la-Ciudad-de-Buenos-Aires.mp3"); // URL of audio file

        for(int i = 0; i < contactList.size(); i++) {
            audioStatusList.add(new AudioEstado(AudioEstado.AUDIO_STATE.IDLE.ordinal(), 0));
        }
        setRecyclerViewAdapter(contactList);

        return root;
  }





    private void setRecyclerViewAdapter(List<String> contactList) {
        AdaptadorAudios adapter = new AdaptadorAudios(context, contactList, this, this);
        recyclerView.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onPause() {
        super.onPause();
        // Store its state
        state = Objects.requireNonNull(recyclerView.getLayoutManager()).onSaveInstanceState();
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
        int playingAudioPosition = -1;
        for(int i = 0; i < audioStatusList.size(); i++) {
            AudioEstado audioStatus = audioStatusList.get(i);
            if(audioStatus.getAudioState() == AudioEstado.AUDIO_STATE.PLAYING.ordinal()) {
                playingAudioPosition = i;
                break;
            }
        }

        if(playingAudioPosition != -1) {
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
        //Almacenar su estado
        state = Objects.requireNonNull(recyclerView.getLayoutManager()).onSaveInstanceState();

        audioStatusList.clear();
        for(int i = 0; i < contactList.size(); i++) {
            audioStatusList.add(new AudioEstado(AudioEstado.AUDIO_STATE.IDLE.ordinal(), 0));
        }
        setRecyclerViewAdapter(contactList);

        //Posición principal de RecyclerView cuando se carga de nuevo
        if (state != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(state);
        }
    }



}
