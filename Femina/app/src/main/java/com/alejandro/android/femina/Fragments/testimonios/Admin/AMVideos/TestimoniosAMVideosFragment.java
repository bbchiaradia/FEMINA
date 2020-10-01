package com.alejandro.android.femina.Fragments.testimonios.Admin.AMVideos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alejandro.android.femina.R;

public class TestimoniosAMVideosFragment extends Fragment {

    private TestimoniosAMVideosViewModel testimoniosAMVideosViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        testimoniosAMVideosViewModel =
                ViewModelProviders.of(this).get(TestimoniosAMVideosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_testimonios_videos_am, container, false);
        //final TextView textView = root.findViewById(R.id.txt_testimonios);
        testimoniosAMVideosViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }
}