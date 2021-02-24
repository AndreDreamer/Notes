package com.view.middle.notes;

import android.app.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class OpenNoteActivity extends Activity {
    EditText title;
    EditText text;
    int index;
    static boolean active = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        active = true;
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
    removeService();
    }

    private void removeService(){
        {
            stopService(new Intent(OpenNoteActivity.this, FloatingWidgetService.class));
        }
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

            Intent myIntent = new Intent(this, MainActivity.class);
            myIntent.putExtra("NoteID", -1);
            startActivity(myIntent);
        super.finish();

    }
    @Override
    protected void onResume(){
        super.onResume();
    active = true;
        removeService();
    }

    @Override protected void onStop(){
        super.onStop();
        active = false;
        if(!MainActivity.active && !OpenNoteActivity.active) {
            // To prevent starting the service if the required permission is NOT granted.
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(this)) {
                startService(new Intent(OpenNoteActivity.this, FloatingWidgetService.class).putExtra("activity_background", true));

            } else {
                errorToast();
            }
        }
    }
    private void errorToast() {
        Toast.makeText(this, "Draw over other app permission not available. Can't start the application without the permission.", Toast.LENGTH_LONG).show();
    }


}
