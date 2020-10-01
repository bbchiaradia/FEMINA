package com.alejandro.android.femina.Fragments.test_violencia.Resultados;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TestViolenciaResultadoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TestViolenciaResultadoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is que test violencia resultado fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}