package com.alejandro.android.femina.ui.que_hacer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QueHacerViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public QueHacerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is que hacer fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
