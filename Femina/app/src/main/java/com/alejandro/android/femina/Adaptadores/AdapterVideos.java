package com.alejandro.android.femina.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.alejandro.android.femina.Dialogos.DialogoAMVideos;
import com.alejandro.android.femina.Entidades.Videos;
import com.alejandro.android.femina.Fragments.contactos.Agregar_editar.ContactosAEFragment;
import com.alejandro.android.femina.Fragments.testimonios.VideosFullScreen.TestimoniosVideosFullScreenFragment;
import com.alejandro.android.femina.R;
import com.alejandro.android.femina.Session.Session;

import java.util.ArrayList;
import java.util.List;

// Created by Juan Manuel on 7/10/2020.
//
public class AdapterVideos extends RecyclerView.Adapter<AdapterVideos.VideoViewHolder> implements Filterable {

    protected List<Videos> youtubeVideoList;
    protected List<Videos> VideoListFull;
    private Context mCtx;

    public AdapterVideos(Context mCtx,List<Videos> youtubeVideoList) {
        this.mCtx = mCtx;
        this.youtubeVideoList = youtubeVideoList;
        VideoListFull = new ArrayList<>(youtubeVideoList);
    }


    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from( parent.getContext()).inflate(R.layout.item_testimonios_videos, parent, false);

        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final VideoViewHolder holder, int position) {
        // cargo la sesion de usuario
        final Session ses = new Session();
        ses.setCt(mCtx.getApplicationContext());
        ses.cargar_session();

        final Videos currentItem = youtubeVideoList.get(position);
        holder.titulo.setText(currentItem.getTitulo());
        holder.categoria.setText(currentItem.getIdCategoria().getDescripcion());
        holder.idcategoria.setText(currentItem.getIdCategoria().toString());
        holder.url.setText(currentItem.getUrl_video());
        holder.videoWeb.loadUrl(currentItem.getUrl_video());
        holder.idvideo.setInputType(currentItem.getId_video());

        // Solo es visible para el admin
        if (ses.getEs_admin()){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(mCtx, "Click On: " + currentItem.getTitulo(), Toast.LENGTH_SHORT).show();
                    Videos video = new Videos();

                    TextView titulovi = (TextView) view.findViewById(R.id.txtTituloVideo);
                    TextView urlvi = (TextView) view.findViewById(R.id.txt_url);
                    TextView inicategory = (TextView) view.findViewById(R.id.txt_idCategoria);
                    TextView categoria = (TextView) view.findViewById(R.id.txtCatVideo);

                    video.setTitulo(titulovi.getText().toString());
                    video.setUrl_video(urlvi.getText().toString());
                    video.setIdCategoria(currentItem.getIdCategoria());
                    video.setId_video(currentItem.getId_video());


                    DialogoAMVideos dialogoAMVideos = new DialogoAMVideos(video);
                    FragmentManager fragmentManager = ((AppCompatActivity) view.getContext()).getSupportFragmentManager();
                    dialogoAMVideos.show(fragmentManager, "");


                }
            });
        }
        // boton fullScreen
        holder.btnfs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Crear bundle, que son los datos que pasaremos
                Bundle datosAEnviar = new Bundle();
                // Aquí pon todos los datos que quieras en formato clave, valor
                datosAEnviar.putString("urlfs",currentItem.getUrl_video());
                // Preparar el fragmento
                Fragment fragment = new TestimoniosVideosFullScreenFragment();
                // ¡Importante! darle argumentos
                fragment.setArguments(datosAEnviar);
                FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_main, fragment);
                fragmentTransaction.addToBackStack(null);
                // Terminar transición y nos vemos en el fragmento de destino
                fragmentTransaction.commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return youtubeVideoList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{

        WebView videoWeb;
        TextView titulo, idvideo;
        TextView categoria, url,idcategoria;
        Button btnfs;

        public VideoViewHolder(View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.txtTituloVideo);
            categoria = itemView.findViewById(R.id.txtCatVideo);
            url = itemView.findViewById(R.id.txt_url);
            videoWeb = (WebView) itemView.findViewById(R.id.videoWebView);
            idcategoria = (TextView) itemView.findViewById(R.id.txt_idCategoria);
            idvideo = (TextView) itemView.findViewById(R.id.txt_idvideo);
            btnfs = (Button) itemView.findViewById(R.id.fullscreen);

            videoWeb.getSettings().setJavaScriptEnabled(true);
            videoWeb.setWebChromeClient(new WebChromeClient() {


            } );


        }
    }

    @Override
    public Filter getFilter() {
        return videoFilter;
    }

    private final Filter videoFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Videos> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(VideoListFull);
            } else {
                        String filterPattern = constraint.toString().toLowerCase().trim();
                        for (Videos item : VideoListFull) {
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
            youtubeVideoList.clear();
            youtubeVideoList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };



    }

