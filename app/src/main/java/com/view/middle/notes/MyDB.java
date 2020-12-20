package com.view.middle.notes;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.ArrayList;

public class MyDB {
    private SharedPreferences preferences;

    public MyDB(Context appContext) {
        preferences = PreferenceManager.getDefaultSharedPreferences(appContext);
    }

    public void putNotes(ArrayList<Note> notes) {
        String[] titles = new String[notes.size()];
        String[] texts = new String[notes.size()];
        for (int i = 0; i < notes.size(); i++) {
            titles[i] = notes.get(i).title;
            texts[i] = notes.get(i).text;
        }
        preferences.edit().putString("notes1", TextUtils.join("‚‗‚", titles)).apply();
        preferences.edit().putString("notes2", TextUtils.join("‚‗‚", texts)).apply();
    }

    public ArrayList<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        String[] titles = TextUtils.split(preferences.getString("notes1", ""), "‚‗‚");
        String[] texts = TextUtils.split(preferences.getString("notes2", ""), "‚‗‚");
        for (int i = 0; i < titles.length; i++) {
            notes.add(new Note(titles[i], texts[i]));
        }
        return notes;

    }

}
