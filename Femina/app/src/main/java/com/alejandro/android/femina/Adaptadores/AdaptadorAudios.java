package com.alejandro.android.femina.Adaptadores;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alejandro.android.femina.Entidades.Audios;
import com.alejandro.android.femina.R;

import java.util.List;

//
// Created by Juan Manuel on 21/10/2020.
//
public class AdaptadorAudios extends RecyclerView.Adapter<AdaptadorAudios.AudioViewHolder> {
    Context context;
    List<Audios> listaAudios;

    public AdaptadorAudios(Context context, List<Audios> listaAudios) {
        this.context = context;
        this.listaAudios = listaAudios;
    }



    @NonNull
    @Override
    public AdaptadorAudios.AudioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_testimonios_audios,parent,false);
        return new AudioViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdaptadorAudios.AudioViewHolder holder, int position) {
        holder.txtTitleAudio.setText(listaAudios.get(position).getTitulo());
        holder.txtCategoryAudio.setText(listaAudios.get(position).getId_categoria().getDescripcion());

        holder.imagePlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.mediaPlayer.isPlaying()){
                    holder.handler.removeCallbacks(holder.updater);
                    holder.mediaPlayer.pause();
                    holder.imagePlayPause.setImageResource(R.drawable.ic_play);
                } else {
                    holder.mediaPlayer.start();
                    holder.imagePlayPause.setImageResource(R.drawable.ic_pause);
                    holder.updateSeekBar();
                }
            }
        });

        holder.playerSeekBar.setMax(100);

    }

    @Override
    public int getItemCount() {
        return listaAudios.size();
    }

    public class AudioViewHolder extends RecyclerView.ViewHolder {
        ImageView imagePlayPause;
        TextView txtCurrentTime, txtTotalDuration, txtCategoryAudio ,txtTitleAudio;
        SeekBar playerSeekBar;
        MediaPlayer mediaPlayer;
        Handler handler = new Handler();


        public AudioViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePlayPause = itemView.findViewById(R.id.imgPlayPause);
            txtCurrentTime = itemView.findViewById(R.id.txtCurrentTime);
            txtTotalDuration = itemView.findViewById(R.id.txtTotalDuration);
            txtCategoryAudio = itemView.findViewById(R.id.txtCatAudio);
            txtTitleAudio = itemView.findViewById(R.id.txtTituloAudio);
            playerSeekBar = itemView.findViewById(R.id.playerSeekBar);
            mediaPlayer = new MediaPlayer();

            //playerSeekBar.setMax(100);

 /*           imagePlayPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mediaPlayer.isPlaying()){
                        handler.removeCallbacks(updater);
                        mediaPlayer.pause();
                        imagePlayPause.setImageResource(R.drawable.ic_play);
                    } else {
                        mediaPlayer.start();
                        imagePlayPause.setImageResource(R.drawable.ic_pause);
                        updateSeekBar();
                    }
                }
            });*/

            prepareMediaPlayer();

        }

        private void prepareMediaPlayer(){
            try {
                mediaPlayer.setDataSource("http://infinityandroid.com/music/good_times.mp3"); // URL of audio file
                mediaPlayer.prepare();
                txtTotalDuration.setText(milliSecondsToTimer(mediaPlayer.getDuration()));

            }catch (Exception exception){
                Toast.makeText(context,exception.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }

        private Runnable updater = new Runnable() {
            @Override
            public void run() {
                updateSeekBar();
                long currentDuration = mediaPlayer.getCurrentPosition();
                txtCurrentTime.setText(milliSecondsToTimer(currentDuration));
            }
        };

        private void updateSeekBar(){
            if(mediaPlayer.isPlaying()){
                playerSeekBar.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration()) * 100));
                handler.postDelayed(updater,1000);
            }
        }

        private String milliSecondsToTimer(long milliSeconds){
            String timerString = "";
            String secondsString;

            int hours = (int)(milliSeconds / (1000 * 60 * 60));
            int minutes = (int)(milliSeconds % (1000 * 60 * 60)) / (1000 * 60);
            int seconds = (int)((milliSeconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);

            if (hours > 0){
                timerString = hours + ":";
            }
            if(seconds < 10){
                secondsString = "0" + seconds;
            } else {
                secondsString = "" + seconds;
            }

            timerString = timerString + minutes + ":" + secondsString;
            return  timerString;

        }
    }
}
