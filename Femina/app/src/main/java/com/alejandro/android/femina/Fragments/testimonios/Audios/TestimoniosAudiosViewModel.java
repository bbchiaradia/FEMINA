package com.alejandro.android.femina.Fragments.testimonios.Audios;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TestimoniosAudiosViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TestimoniosAudiosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is testimonios audios fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}