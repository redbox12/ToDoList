package com.example.todolist2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton addNoteButton;
    private RecyclerView recyclerViewNotes;
    private NotesAdapter notesAdapter;

    private MainViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        initeViews();
        notesAdapter = new NotesAdapter();

        viewModel.getNotes().observe(MainActivity.this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                notesAdapter.setNotes(notes);
            }
        });
        recyclerViewNotes.setAdapter(notesAdapter);

        //создать заметку
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchNextScreen();
            }
        });

        //удалить свайпом заметку
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT
        ) {
            @Override
            public boolean onMove(
                    @NonNull RecyclerView recyclerView,
                    @NonNull RecyclerView.ViewHolder viewHolder,
                    @NonNull RecyclerView.ViewHolder target
            ) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Note note = notesAdapter.getNotes().get(position);
                viewModel.remove(note);
            }

        });

        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);


    }

    private void initeViews() {
        addNoteButton = findViewById(R.id.addNoteButton);
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
    }


    private void launchNextScreen() {
        Intent intent = CreateNote.newIntent(MainActivity.this);
        startActivity(intent);
    }

}