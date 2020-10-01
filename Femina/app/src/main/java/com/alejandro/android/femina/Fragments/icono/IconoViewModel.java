package com.alejandro.android.femina.Fragments.icono;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class IconoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public IconoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is icono fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
