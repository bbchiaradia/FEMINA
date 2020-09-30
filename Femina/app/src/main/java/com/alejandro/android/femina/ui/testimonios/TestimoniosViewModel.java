package com.alejandro.android.femina.ui.testimonios;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TestimoniosViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TestimoniosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is que testimonios fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}