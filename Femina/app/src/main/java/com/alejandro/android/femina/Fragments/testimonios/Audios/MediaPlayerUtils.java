package com.alejandro.android.femina.Fragments.testimonios.Audios;

import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;

public class MediaPlayerUtils {

    private static MediaPlayer mediaPlayer;
    private static Listener listener;
    private static Handler mHandler;

    /**
     * Obtener instancia
     *retorna instancia de controlador de base de datos
     */
    public static void getInstance() {
        if(mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }

        if(mHandler == null) {
            mHandler = new Handler();
        }
    }

    /**
     * lanza mediaPlayer
     */
    public static void releaseMediaPlayer() {
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public static void pauseMediaPlayer() {
        mediaPlayer.pause();
    }

    public static void playMediaPlayer() {
        mediaPlayer.start();
        mHandler.postDelayed(mRunnable, 100);
    }

    public static void applySeekBarValue(int selectedValue) {
        mediaPlayer.seekTo(selectedValue);
        mHandler.postDelayed(mRunnable, 100);
    }

    /**
     * Inicia mediaPlayer
     * @param audioUrl
     * @param listener
     * @throws IOException exception
     */
    public static void startAndPlayMediaPlayer(String audioUrl, final Listener listener) throws IOException {
        MediaPlayerUtils.listener = listener;
        getInstance();
        if(isPlaying()) {
            pauseMediaPlayer();
        }
        releaseMediaPlayer();
        getInstance();
        Log.d("Url","URLID" + audioUrl);
        mediaPlayer.setDataSource(audioUrl);
        mediaPlayer.prepare();
        //mediaPlayer.prepareAsync();
        mediaPlayer.setOnCompletionListener(onCompletionListener);
        playMediaPlayer();


        mHandler.postDelayed(mRunnable, 100);

    }

    public static boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    public static int getTotalDuration() {
        return mediaPlayer.getDuration();
    }

    private static MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            MediaPlayerUtils.releaseMediaPlayer();
            listener.onAudioComplete();
        }
    };

    private static Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                if (isPlaying()) {
                    mHandler.postDelayed(mRunnable, 100);
                    listener.onAudioUpdate(mediaPlayer.getCurrentPosition());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public interface Listener {
        void onAudioComplete();
        void onAudioUpdate(int currentPosition);
    }

}
