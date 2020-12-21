package com.view.middle.notes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static ArrayList<Note> notes;
    static MyDB db;
    ListView listView;
    Context context;
    TextView latestNotesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        db = new MyDB(this);
        listView = findViewById(R.id.listView);
        latestNotesTextView = findViewById(R.id.latestNoteTextView);
        refreshNotes();
        findViewById(R.id.addNote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, OpenNoteActivity.class);
                myIntent.putExtra("NoteID", -1);
                startActivity(myIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshNotes();
    }

    private void refreshNotes() {
        notes = db.getNotes();
        if (notes.size() == 0)
            latestNotesTextView.setText(getResources().getString(R.string.addNewNote));
        if (notes.size() == 1)
            latestNotesTextView.setText(getResources().getString(R.string.latestNote));
        if (notes.size() > 1)
            latestNotesTextView.setText(getResources().getString(R.string.latestNotes));
        listView.setAdapter(new MyAdapter(this, notes));

    }

}
