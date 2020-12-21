package com.view.middle.notes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

class MyAdapter extends BaseAdapter {
    private Context context;
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

        String textText = "";
        int spaceCounter = 0;
        for (int i = 0; i < notes.get(position).text.length(); i++) {
            if (notes.get(position).text.charAt(i) == '\n') spaceCounter++;
            if (spaceCounter > 3) {
                textText += "...";
                break;
            }
            if (i > 40) {
                textText += "...";
                break;
            }
            textText += notes.get(position).text.charAt(i);
        }
        text.setText(textText);

        if (notes.get(position).title.length() < 15) {
            title.setText(notes.get(position).title);
        } else {
            title.setText(notes.get(position).title.substring(0, 15) + "...");

        }
        final ImageButton button = vi.findViewById(R.id.button);
        //delete btn Event
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu dropDownMenu = new PopupMenu(context, button);
                dropDownMenu.getMenuInflater().inflate(R.menu.drop_down_menu, dropDownMenu.getMenu());
                dropDownMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getTitle().equals("delete")) {
                            notes.remove(position);
                            MainActivity.db.putNotes(notes);
                            MainActivity.notes = notes;
                            notifyDataSetChanged();
                        }
                        if (menuItem.getTitle().equals("info")) {
                        //todo info menu
                        }
                        return true;
                    }
                });
                dropDownMenu.show();


            }
        });
        //note click event
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