package com.alejandro.android.femina.Fragments.testimonios.Admin.AMVideos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TestimoniosAMVideosViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TestimoniosAMVideosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is que testimonios AM videos fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}