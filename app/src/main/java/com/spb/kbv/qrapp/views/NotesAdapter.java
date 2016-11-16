package com.spb.kbv.qrapp.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.spb.kbv.qrapp.R;
import com.spb.kbv.qrapp.model.Note;

import java.util.List;

public class NotesAdapter extends BaseAdapter {
    private List<Note> mNotes;
    private LayoutInflater mInflater;

    public NotesAdapter(List<Note> notes, Context context) {
        mNotes = notes;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mNotes.size();
    }

    @Override
    public Note getItem(int i) {
        return mNotes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        final Note note = mNotes.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, viewGroup, false);

            viewHolder = new ViewHolder();
            viewHolder.noteName = (TextView) convertView.findViewById(R.id.item_text);
            viewHolder.noteCode = (TextView) convertView.findViewById(R.id.item_code);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.noteName.setText(note.getNoteName());
        viewHolder.noteCode.setText(note.getStatusCode());
        return convertView;
    }

    private static class ViewHolder {
        TextView noteName, noteCode;
    }
}
