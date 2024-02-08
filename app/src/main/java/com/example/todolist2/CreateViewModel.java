package com.example.todolist2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class CreateViewModel extends AndroidViewModel {

    private NoteDatabase notesDataBase;
    private MutableLiveData<Boolean> shouldCloseScreen = new MutableLiveData<>();

    public LiveData<Boolean> getShouldCloseScreen() {
        return shouldCloseScreen;
    }

    public CreateViewModel(@NonNull Application application) {
        super(application);
        notesDataBase = NoteDatabase.getInstance(application);
    }

    public void saveNote(Note note){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                notesDataBase.notesDao().add(note);
                shouldCloseScreen.postValue(true);
            }
        });
        thread.start();
    }
}
