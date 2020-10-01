package com.alejandro.android.femina.Fragments.testimonios.Admin.AMAudios;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TestimoniosAMAudiosViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TestimoniosAMAudiosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is que testimonios AM audios fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}