package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.NoteListAdapter.NoteListAdapter;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.NoteListAdapter.OnRecyclerViewClickListener;

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

        initNotesList(view);

        return view;
    }

    private void initNotesList(View view) {
        //String[] noteNames = getResources().getStringArray(R.array.noteNames);
        NoteSource data = new NoteSourceImpl(getResources()).init();
        RecyclerView recyclerView = view.findViewById(R.id.listRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        NoteListAdapter noteListAdapter = new NoteListAdapter(data);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(noteListAdapter);
        noteListAdapter.setOnClickListener(new OnRecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                String currentNoteName = data.getNoteData(position).getName();
                String currentNoteContent = data.getNoteData(position).getNoteContent();
                currentNote = new NoteData(currentNoteName, currentNoteContent);
                showContent();
            }
        });
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