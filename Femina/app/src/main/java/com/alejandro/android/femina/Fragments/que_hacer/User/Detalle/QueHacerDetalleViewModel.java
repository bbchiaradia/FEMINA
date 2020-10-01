package com.alejandro.android.femina.Fragments.que_hacer.User.Detalle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QueHacerDetalleViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public QueHacerDetalleViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is que hacer detalle fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}