package com.view.middle.notes;

import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class OpenNoteActivity extends Activity {
    EditText title;
    EditText text;
    int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_note);
        title = findViewById(R.id.editTitle);
        text = findViewById(R.id.editText);
        index = getIntent().getIntExtra("NoteID", 0);
        if (index == -1) {
            title.setText("Edit title");
            text.setText("Put notes");
        } else {
            Note note = MainActivity.notes.get(index);
            title.setText(note.title);
            text.setText(note.text);

        }
        Button ok = findViewById(R.id.buttonOK);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void finish() {
        if (!text.getText().toString().isEmpty()) {
            String header = title.getText().toString();
            String plot = text.getText().toString();
            if (title.getText().toString().isEmpty()) {
                header = plot.length() > 15 ? plot.substring(0, 15)+"..." : plot;
            }
            if (index == -1) {
                Note note = new Note(header, plot);
                MainActivity.notes.add(note);
            } else {
                Note note = new Note(header, plot);
                MainActivity.notes.set(index, note);
            }
            MainActivity.db.putNotes(MainActivity.notes);
        }
        super.finish();

    }


}
