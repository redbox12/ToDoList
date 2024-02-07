package com.example.todolist2;

import java.util.ArrayList;
import java.util.Random;

public class Database {

    private ArrayList<Note> notes = new ArrayList<>();

    public ArrayList<Note> getNotes() {
        return new ArrayList<>(notes);
    }

    private static Database instance = null;

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private Database() {

    }
    public void add(Note note){
        notes.add(note);
    }

    public void remove(int id){
        for(int i = 0; i < notes.size(); i++){
            Note note = notes.get(i);
            if(note.getId() == id){
                notes.remove(note);
            }
        }
    }
}
