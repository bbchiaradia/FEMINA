package com.alejandro.android.femina.Fragments.secuencia;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SecuenciaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SecuenciaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is secuencia fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
