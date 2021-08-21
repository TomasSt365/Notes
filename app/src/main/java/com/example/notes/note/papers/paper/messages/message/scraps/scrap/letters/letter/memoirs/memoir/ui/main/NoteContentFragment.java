package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.R;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.NoteData;

public class NoteContentFragment  extends Fragment {

    private static final String DATA_KEY = "KEY";

    private static NoteData currentNote;

    public static NoteContentFragment newInstance(NoteData noteData) {
        NoteContentFragment fragment = new NoteContentFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(DATA_KEY, noteData);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentNote = getArguments().getParcelable(DATA_KEY);
        }else{
            currentNote = new NoteData.Builder().build();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_content, container, false);
        TextView name = view.findViewById(R.id.noteTitle);
        TextView noteText = view.findViewById(R.id.noteContent);

        name.setText(currentNote.getTitle());
        noteText.setText(currentNote.getNoteContent());

        return view;
    }


}
