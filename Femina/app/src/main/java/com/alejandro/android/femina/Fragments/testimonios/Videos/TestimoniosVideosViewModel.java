package com.alejandro.android.femina.Fragments.testimonios.Videos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TestimoniosVideosViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TestimoniosVideosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is testimonios videos fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}