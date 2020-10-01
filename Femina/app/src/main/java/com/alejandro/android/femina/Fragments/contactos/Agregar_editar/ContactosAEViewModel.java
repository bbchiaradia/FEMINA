package com.alejandro.android.femina.Fragments.contactos.Agregar_editar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ContactosAEViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ContactosAEViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is contactos AE fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}