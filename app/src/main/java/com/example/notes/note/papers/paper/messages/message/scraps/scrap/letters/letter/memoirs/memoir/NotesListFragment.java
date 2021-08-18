package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.NoteListAdapter.NoteListAdapter;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.NoteListAdapter.OnRecyclerViewClickListener;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.NoteData;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.NoteSource;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.NoteSourceImpl;

public class NotesListFragment extends Fragment {
    private static NoteData currentNote;

    public static NoteData getCurrentNote() {
        if (currentNote != null) {
            return currentNote;
        } else {
            return currentNote = new NoteData();
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
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onRecyclerViewClick(View view, int position) {
                switch (view.getId()){
                    case R.id.item_card_view:
                        String currentNoteName = data.getNoteData(position).getName();
                        String currentNoteContent = data.getNoteData(position).getNoteContent();
                        boolean currentNoteIsFavorite = data.getNoteData(position).isFavorite();
                        currentNote = new NoteData(currentNoteName, currentNoteContent, currentNoteIsFavorite);
                        showContent();
                        break;
                    case R.id.favoriteButton:
                        data.getNoteData(position).setFavorite(!data.getNoteData(position).isFavorite());
                        break;
                    default:
                        break;
                }
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