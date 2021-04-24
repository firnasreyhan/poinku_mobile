package com.android.poinku.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.android.poinku.repository.OnlineRepository;

public class MainViewModel extends AndroidViewModel {
    private OnlineRepository onlineRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        onlineRepository = new OnlineRepository();
    }
}
