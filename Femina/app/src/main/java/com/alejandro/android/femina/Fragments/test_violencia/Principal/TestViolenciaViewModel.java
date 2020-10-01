package com.alejandro.android.femina.Fragments.test_violencia.Principal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TestViolenciaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TestViolenciaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is que test violencia fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}