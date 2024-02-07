package com.example.todolist2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton addNoteButton;
    private LinearLayout linearNotes;
    private Database database = Database.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initeViews();

        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchNextScreen();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showNotes();
    }

    private void showNotes() {
        linearNotes.removeAllViews();
        for(Note note: database.getNotes()){
            View view = getLayoutInflater().inflate(
                    R.layout.note_item,
                    linearNotes,
                    false
            );
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    database.remove(note.getId());
                    showNotes();
                }
            });
            TextView textViewNote = view.findViewById(R.id.textViewNote);
            textViewNote.setText(note.getText());

            int colorResId;
            switch (note.getPriority()){
                case 0:
                    colorResId = android.R.color.holo_green_light;
                    break;
                case 1:
                    colorResId = android.R.color.holo_orange_light;
                    break;
                default:
                    colorResId = android.R.color.holo_red_light;
            }

            int color = ContextCompat.getColor(this, colorResId); //получение color по id
            textViewNote.setBackgroundColor(color);
            linearNotes.addView(view);
        }

    }

    private void launchNextScreen() {
        Intent intent = CreateNote.newIntent(MainActivity.this);
        startActivity(intent);
    }


    private void initeViews() {
        addNoteButton = findViewById(R.id.addNoteButton);
        linearNotes = findViewById(R.id.linearNotes);
    }
}