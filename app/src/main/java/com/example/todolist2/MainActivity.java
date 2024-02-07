package com.example.todolist2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

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
    private RecyclerView recyclerViewNotes;
    private NotesAdapter notesAdapter;
    private Database database = Database.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initeViews();

        notesAdapter = new NotesAdapter();
        notesAdapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {
                database.remove(note.getId());
                showNotes();
            }
        });
        recyclerViewNotes.setAdapter(notesAdapter);

        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchNextScreen();
            }
        });
    }

    private void initeViews() {
        addNoteButton = findViewById(R.id.addNoteButton);
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showNotes();
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
    }

    private void showNotes() {
        notesAdapter.setNotes(database.getNotes());
    }

    private void launchNextScreen() {
        Intent intent = CreateNote.newIntent(MainActivity.this);
        startActivity(intent);
    }

}