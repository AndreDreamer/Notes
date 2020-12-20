package com.view.middle.notes;

import android.app.LauncherActivity;

public class Note extends LauncherActivity.ListItem {
    long id;
    String title;
    String text;

    public Note(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public Note(String text) {
        this.text = text;
        title = text.substring(0, 6) + "...";
    }
}
