package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class NoteContentFragment extends Fragment {

    private static final String DATA_KEY = "KEY";

    private NoteData noteData;

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
            this.noteData = getArguments().getParcelable(DATA_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_content, container, false);
        TextView name = view.findViewById(R.id.name);
        TextView noteText = view.findViewById(R.id.noteText);

        name.setText(this.noteData.getName());
        noteText.setText(this.noteData.getNoteContent());

        return view;
    }

}
