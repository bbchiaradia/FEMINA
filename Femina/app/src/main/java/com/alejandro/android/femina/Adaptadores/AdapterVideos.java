package com.alejandro.android.femina.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.alejandro.android.femina.Entidades.Videos;
import com.alejandro.android.femina.R;
import java.util.ArrayList;
import java.util.List;

// Created by Juan Manuel on 7/10/2020.
//
public class AdapterVideos extends RecyclerView.Adapter<AdapterVideos.VideoViewHolder> implements Filterable {

    protected List<Videos> youtubeVideoList;
    protected List<Videos> VideoListFull;
    private Context mCtx;

/*    public AdapterVideos(List<Videos> youtubeVideoList) {
        this.youtubeVideoList = youtubeVideoList;
        VideoListFull = new ArrayList<>(youtubeVideoList);
    }*/
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
        Videos currentItem = youtubeVideoList.get(position);
        holder.titulo.setText(currentItem.getTitulo());
        holder.categoria.setText(currentItem.getIdCategoria().getDescripcion());
        holder.videoWeb.loadData(currentItem.getUrl_video(), "text/html" , "utf-8");
    }

    @Override
    public int getItemCount() {
        return youtubeVideoList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{

        WebView videoWeb;
        TextView titulo;
        TextView categoria;

        public VideoViewHolder(View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.txtTituloVideo);
            categoria = itemView.findViewById(R.id.txtCatVideo);
            videoWeb = (WebView) itemView.findViewById(R.id.videoWebView);

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

