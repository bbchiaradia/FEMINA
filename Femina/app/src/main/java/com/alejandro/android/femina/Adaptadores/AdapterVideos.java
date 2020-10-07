package com.alejandro.android.femina.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.recyclerview.widget.RecyclerView;

import com.alejandro.android.femina.Entidades.Videos;
import com.alejandro.android.femina.R;

import java.util.List;

//
// Created by Juan Manuel on 7/10/2020.
//
public class AdapterVideos extends RecyclerView.Adapter<AdapterVideos.VideoViewHolder> {

    List<Videos> youtubeVideoList;

    public AdapterVideos() {
    }

    public AdapterVideos(List<Videos> youtubeVideoList) {
        this.youtubeVideoList = youtubeVideoList;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from( parent.getContext()).inflate(R.layout.item_testimonio_videos, parent, false);

        return new VideoViewHolder(view);

    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {

        holder.videoWeb.loadData( youtubeVideoList.get(position).getUrl_video(), "text/html" , "utf-8" );

    }


    @Override
    public int getItemCount() {
        return youtubeVideoList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{

        WebView videoWeb;

        public VideoViewHolder(View itemView) {
            super(itemView);

            videoWeb = (WebView) itemView.findViewById(R.id.videoWebView);

            videoWeb.getSettings().setJavaScriptEnabled(true);
            videoWeb.setWebChromeClient(new WebChromeClient() {


            } );
        }
    }

}
