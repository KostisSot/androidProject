package com.example.project1.ui.phones;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PhonesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PhonesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is phones fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}