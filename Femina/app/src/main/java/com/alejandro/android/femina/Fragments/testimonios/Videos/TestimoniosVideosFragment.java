package com.alejandro.android.femina.Fragments.testimonios.Videos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import java.util.Vector;

public class TestimoniosVideosFragment extends Fragment {

    RecyclerView recyclerView;
    Vector<Videos> youtubeVideos = new Vector<Videos>();

    private TestimoniosVideosViewModel testimoniosVideosViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        testimoniosVideosViewModel =
                ViewModelProviders.of(this).get(TestimoniosVideosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_testimonios_videos, container, false);
        //final TextView textView = root.findViewById(R.id.txt_testimonios);
        testimoniosVideosViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));


        youtubeVideos.add( new Videos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/roN-Kbvn-OI\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new Videos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/dqGKhWN9zwU\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new Videos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/fg0iEgpSbdA\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new Videos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/r6f2ZoHuwho\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new Videos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/5UiQ77kTwLQ\" frameborder=\"0\" allowfullscreen></iframe>") );

        AdapterVideos videoAdapter = new AdapterVideos(youtubeVideos);

        recyclerView.setAdapter(videoAdapter);



        return root;
    }
}