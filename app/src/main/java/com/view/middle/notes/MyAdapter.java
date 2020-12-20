package com.view.middle.notes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

class MyAdapter extends BaseAdapter {
    Context context;
    ArrayList<Note> notes;
    private static LayoutInflater inflater = null;

    MyAdapter(Context context, ArrayList<Note> notes) {
        this.notes = notes;
        this.context = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Note getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.list_item, null);
        TextView title = vi.findViewById(R.id.title);
        TextView text = vi.findViewById(R.id.text);
        if(notes.get(position).text.length()<40){
            text.setText(notes.get(position).text);

        }else {
            text.setText(notes.get(position).text.substring(0,40)+"...");
        }

        if(notes.get(position).title.length()<15){
            title.setText(notes.get(position).title);
        }else{
            title.setText(notes.get(position).title.substring(0,15)+"...");

        }
        Button button = vi.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notes.remove(position);
                MainActivity.db.putNotes(notes);
                MainActivity.notes = MainActivity.db.getNotes();
                notifyDataSetChanged();


            }
        });

        vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNote(position);
            }
        });


        return vi;
    }

    private void openNote(int id) {
        Intent myIntent = new Intent(context, OpenNoteActivity.class);
        myIntent.putExtra("NoteID", id);
        context.startActivity(myIntent);
    }

}