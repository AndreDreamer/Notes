package com.view.middle.notes;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.net.Uri;

import android.widget.Toast;



import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static boolean active = false;
    static ArrayList<Note> notes;
    static MyDB db;
    ListView listView;
    Context context;


    private static final int DRAW_OVER_OTHER_APP_PERMISSION = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        active = true;
        setContentView(R.layout.activity_main);
        context = this;
        db = new MyDB(this);
        listView = findViewById(R.id.listView);
        askForSystemOverlayPermission();
        refreshNotes();
        findViewById(R.id.addNote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, OpenNoteActivity.class);
                myIntent.putExtra("NoteID", -1);
                startActivity(myIntent);
            }
        });

        removeService();
    }

    private void removeService(){
        {
            stopService(new Intent(MainActivity.this, FloatingWidgetService.class));
            }
        }


    @Override
    protected void onResume() {
        super.onResume();
        active = true;
        removeService();
        refreshNotes();
    }

    private void refreshNotes() {
        notes = db.getNotes();
        listView.setAdapter(new MyAdapter(this, notes));

    }
    private void askForSystemOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {

            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, DRAW_OVER_OTHER_APP_PERMISSION);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        active = false;
        if(!MainActivity.active && !OpenNoteActivity.active) {
            // To prevent starting the service if the required permission is NOT granted.
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(this)) {
                startService(new Intent(MainActivity.this, FloatingWidgetService.class).putExtra("activity_background", true));

            } else {
                errorToast();
            }
        }
        super.onStop();

    }

    @Override
    protected void onDestroy(){

        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DRAW_OVER_OTHER_APP_PERMISSION) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    //Permission is not available. Display error text.
                    errorToast();
                    finish();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void errorToast() {
        Toast.makeText(this, "Draw over other app permission not available. Can't start the application without the permission.", Toast.LENGTH_LONG).show();
    }

}
