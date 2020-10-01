package com.alejandro.android.femina.Fragments.que_hacer.Admin.Alta_Modificacion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QueHacerAMViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public QueHacerAMViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is que hacer alta/modificacion fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}