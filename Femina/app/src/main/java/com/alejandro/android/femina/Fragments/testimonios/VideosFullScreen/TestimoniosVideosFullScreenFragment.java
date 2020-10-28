package com.alejandro.android.femina.Fragments.testimonios.VideosFullScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alejandro.android.femina.R;

public class TestimoniosVideosFullScreenFragment extends Fragment {
    private WebView webView_fs;

    private com.alejandro.android.femina.Fragments.testimonios.VdeosFullScreen.TestimoniosVideosFullScreenViewModel mViewModel;

    public static TestimoniosVideosFullScreenFragment newInstance() {
        return new TestimoniosVideosFullScreenFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.testimonios_videos_full_screen_fragment, container, false);
        webView_fs = root.findViewById(R.id.full_video);
        webView_fs.setWebViewClient(new WebViewClient());
        webView_fs.getSettings().setJavaScriptEnabled(true);
        webView_fs.setWebChromeClient(new WebChromeClient() {} );

        Bundle datosRecuperados = getArguments();
        if (datosRecuperados != null) {
            // Y ahora puedes recuperar usando get en lugar de put
            webView_fs.loadUrl(datosRecuperados.getString("urlfs"));
            // Imprimimos, pero en tu caso haz lo necesario
            Log.d("FragmentFullScreen", "La url: " + datosRecuperados.getString("urlfs"));
        }


        return root;
    }


 /*   @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TestimoniosVideosFullScreenViewModel.class);
        // TODO: Use the ViewModel
    }*/

    @Override
    public void onPause() {
        super.onPause();
        ((AppCompatActivity) getContext()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getContext()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setRetainInstance(true);
    }
}