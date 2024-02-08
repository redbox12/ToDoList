package com.example.todolist2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class CreateNote extends AppCompatActivity {
    private EditText editNoteText;
    private RadioButton lowNote;
    private RadioButton mediumNote;

    private Button buttonSaveNote;
    private CreateViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        viewModel = new ViewModelProvider(this).get(CreateViewModel.class);
        initViews();
        viewModel.getShouldCloseScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldClose) {
                if(shouldClose){
                    finish();
                }
            }
        });

        buttonSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });


    }

    private void initViews() {
        editNoteText = findViewById(R.id.editNoteText);
        lowNote = findViewById(R.id.lowNote);
        mediumNote = findViewById(R.id.mediumNote);
        buttonSaveNote = findViewById(R.id.buttonSaveNote);
    }

    private void saveNote() {
        String text = editNoteText.getText().toString().trim();
        int priority = getPriority();
        Note newNote = new Note(0, text, priority);
        viewModel.saveNote(newNote);
    }


    private int getPriority() {
        int priority;
        if (lowNote.isChecked()) {
            priority = 0;
        } else if (mediumNote.isChecked()) {
            priority = 1;
        } else {
            priority = 2;
        }
        return priority;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, CreateNote.class);
    }
}