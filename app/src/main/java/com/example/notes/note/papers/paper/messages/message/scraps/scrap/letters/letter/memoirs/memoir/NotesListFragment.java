package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class NotesListFragment extends Fragment {
    private static NoteData currentNote;

    public static NoteData getCurrentNote() {
        if (currentNote != null) {
            return currentNote;
        } else {
            return currentNote = new NoteData("");
        }
    }

    public static NotesListFragment newInstance() {
        return new NotesListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        LinearLayout linearLayout = (LinearLayout) view;
        initNoteListTextView(linearLayout);
        return view;
    }

    @SuppressLint("ResourceAsColor")
    private void initNoteListTextView(LinearLayout linearLayout) {

        String[] noteNames = getResources().getStringArray(R.array.noteNames);
        LayoutInflater layoutInflater = getLayoutInflater();

        for (int i = 0; i < noteNames.length; i++) {
            String noteName = noteNames[i];
            TextView textView = (TextView) layoutInflater.inflate(R.layout.item, linearLayout, false);
            textView.setText(noteName);
            linearLayout.addView(textView);
            int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String currentNoteName = (getResources().getStringArray(R.array.noteNames))[finalI];
                    String currentNoteContent = (getResources().getStringArray(R.array.noteContent))[finalI];
                    currentNote = new NoteData(currentNoteName, currentNoteContent);
                    showContent();
                }
            });
        }
    }

    private void showContent() {
        if (MainActivity.isLandScape()) {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_container, NoteContentFragment.newInstance(currentNote))
                    .commit();
        } else {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("")
                    .replace(R.id.main_container, NoteContentFragment.newInstance(currentNote))
                    .commit();
        }
    }

}