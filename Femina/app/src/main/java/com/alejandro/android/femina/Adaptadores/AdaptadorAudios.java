package com.alejandro.android.femina.Adaptadores;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.alejandro.android.femina.Entidades.Audios;
import com.alejandro.android.femina.Entidades.Videos;
import com.alejandro.android.femina.Fragments.testimonios.Audios.AudioEstado;
import com.alejandro.android.femina.Fragments.testimonios.Audios.MediaPlayerUtils;
import com.alejandro.android.femina.Fragments.testimonios.Audios.TestimoniosAudiosFragment;
import com.alejandro.android.femina.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//
// Created by Juan Manuel on 21/10/2020.
//
public class AdaptadorAudios extends RecyclerView.Adapter<AdaptadorAudios.AudioViewHolder> implements Filterable {

    private final Context context;
    private final TestimoniosAudiosFragment testimoniosAudiosFragment;
    private List<Audios> contactList = new ArrayList<>();
    protected List<Audios> AudioListFull;
    private MediaPlayerUtils.Listener mListener;


    public AdaptadorAudios(Context context, List<Audios> contactList, TestimoniosAudiosFragment testimoniosAudiosFragment, MediaPlayerUtils.Listener listener) {
        this.context = context;
        this.contactList = contactList;
        this.testimoniosAudiosFragment = testimoniosAudiosFragment;
        this.mListener = listener;
        AudioListFull = new ArrayList<>(contactList);

    }


    @NonNull
    @Override
    public AdaptadorAudios.AudioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_testimonios_audios, parent, false);
        return new AudioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  AdaptadorAudios.AudioViewHolder holder,  int position) {
        Log.d("SizeAdap", "SizeAdapIs: "+ contactList.size());
        Audios currentItem = contactList.get(position);
        holder.txtSongName.setText(currentItem.getTitulo());

        if(testimoniosAudiosFragment.audioStatusList.get(position).getAudioState() != AudioEstado.AUDIO_STATE.IDLE.ordinal()) {
            holder.seekBarAudio.setMax(testimoniosAudiosFragment.audioStatusList.get(position).getTotalDuration());
            holder.seekBarAudio.setProgress(testimoniosAudiosFragment.audioStatusList.get(position).getCurrentValue());
            holder.seekBarAudio.setEnabled(true);
        } else {
            holder.seekBarAudio.setProgress(0);
            holder.seekBarAudio.setEnabled(false);
        }

        if(testimoniosAudiosFragment.audioStatusList.get(position).getAudioState() == AudioEstado.AUDIO_STATE.IDLE.ordinal()
                || testimoniosAudiosFragment.audioStatusList.get(position).getAudioState() == AudioEstado.AUDIO_STATE.PAUSED.ordinal()) {
            holder.imagePlayPause.setImageResource(R.drawable.ic_play);
        } else {
            holder.imagePlayPause.setImageResource(R.drawable.ic_pause);
        }


    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class AudioViewHolder extends RecyclerView.ViewHolder {
        public SeekBar seekBarAudio;
        TextView txtSongName;
        ImageView imagePlayPause;


        public AudioViewHolder(@NonNull View itemView) {
            super(itemView);
            seekBarAudio = (SeekBar) itemView.findViewById(R.id.seekBarAudio);
            txtSongName = (TextView) itemView.findViewById(R.id.txtSongName);
            imagePlayPause = itemView.findViewById(R.id.imgPlayPause);

            seekBarAudio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if(fromUser) MediaPlayerUtils.applySeekBarValue(progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });


  imagePlayPause.setOnClickListener(new View.OnClickListener() {



                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View view) {

          int position = getAdapterPosition();

          // Comprueba si se está reproduciendo algún otro audio
          if(testimoniosAudiosFragment.audioStatusList.get(position).getAudioState()
                  == AudioEstado.AUDIO_STATE.IDLE.ordinal()) {

              // Resetea media player, casteando el escuchador con el contexto del MainActivity
              mListener.onAudioComplete();
          }

          String audioPath = contactList.get(position).getUrl_audio();
          AudioEstado audioEstado = testimoniosAudiosFragment.audioStatusList.get(position);
          int currentAudioState = audioEstado.getAudioState();

          if(currentAudioState == AudioEstado.AUDIO_STATE.PLAYING.ordinal()) {
              // Si mediaPlayer esta reproduciendo, pausa mediaPlayer
              imagePlayPause.setImageResource(R.drawable.ic_play);
              MediaPlayerUtils.pauseMediaPlayer();

              audioEstado.setAudioState(AudioEstado.AUDIO_STATE.PAUSED.ordinal());
              testimoniosAudiosFragment.audioStatusList.set(position, audioEstado);
          } else if(currentAudioState == AudioEstado.AUDIO_STATE.PAUSED.ordinal()) {
              // Si mediaPlayer esta pausado, reproduce mediaPlayer
              imagePlayPause.setImageResource(R.drawable.ic_pause);
              MediaPlayerUtils.playMediaPlayer();

              audioEstado.setAudioState(AudioEstado.AUDIO_STATE.PLAYING.ordinal());
              testimoniosAudiosFragment.audioStatusList.set(position, audioEstado);
          } else {
              // si mediaPlayer esta stopeado, inicia y reproduce mediaPlayer
              imagePlayPause.setImageResource(R.drawable.ic_pause);

              audioEstado.setAudioState(AudioEstado.AUDIO_STATE.PLAYING.ordinal());
              testimoniosAudiosFragment.audioStatusList.set(position, audioEstado);

              try {
                  Toast.makeText(context,"Por favor espera que cargue el audio",Toast.LENGTH_SHORT).show();
                  MediaPlayerUtils.startAndPlayMediaPlayer(audioPath, mListener);


                  audioEstado.setTotalDuration(MediaPlayerUtils.getTotalDuration());
                  testimoniosAudiosFragment.audioStatusList.set(position, audioEstado);
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
                }
            });
        }

    }

    @Override
    public Filter getFilter() {
        return audioFilter;
    }

    private final Filter audioFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Audios> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(AudioListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Audios item : AudioListFull) {
                    //Aca se indica cual sera el campo a filtar
                    if (item.getTitulo().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            contactList.clear();
            contactList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}

