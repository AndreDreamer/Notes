package com.view.middle.notes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static ArrayList<Note> notes;
    static MyDB db;
    ListView listView;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new MyDB(this);
        notes = db.getNotes();
        listView = findViewById(R.id.listView);
        listView.setAdapter(new MyAdapter(this, notes));
        context = this;
        findViewById(R.id.addNote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, OpenNoteActivity.class);
                myIntent.putExtra("NoteID",-1);
                startActivity(myIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        notes = db.getNotes();
        listView.setAdapter(new MyAdapter(this, notes));
    }

}
