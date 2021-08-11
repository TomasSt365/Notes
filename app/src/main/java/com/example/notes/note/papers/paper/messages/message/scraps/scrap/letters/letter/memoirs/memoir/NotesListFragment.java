package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotesListFragment extends Fragment {

    public static NotesListFragment newInstance() {
        return new NotesListFragment();
    }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        LinearLayout linearLayout = (LinearLayout) view;
        String[] noteNames = getResources().getStringArray(R.array.noteNames);

        for (String noteName : noteNames) {
            TextView textView = new TextView(getContext());
            textView.setText(noteName);
            textView.setTextColor(R.color.purple_700);
            textView.setTextSize(getResources().getInteger(R.integer.nameInListSize));
            linearLayout.addView(textView);
        }

        return view;
    }

}