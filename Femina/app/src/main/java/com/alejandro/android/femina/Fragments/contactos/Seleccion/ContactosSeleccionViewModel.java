package com.alejandro.android.femina.Fragments.contactos.Seleccion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ContactosSeleccionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ContactosSeleccionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is contactos seleccion fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}