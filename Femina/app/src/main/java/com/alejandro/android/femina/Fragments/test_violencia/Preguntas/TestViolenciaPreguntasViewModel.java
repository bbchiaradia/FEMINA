package com.alejandro.android.femina.Fragments.test_violencia.Preguntas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TestViolenciaPreguntasViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TestViolenciaPreguntasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is que test violencia preguntas fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}