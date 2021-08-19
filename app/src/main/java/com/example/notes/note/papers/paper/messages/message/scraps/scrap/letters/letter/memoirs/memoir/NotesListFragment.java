package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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
    private static boolean isFavoriteList;
    private NoteSource data;
    private NoteSource favoriteData;
    private NoteListAdapter noteListAdapter;
    private RecyclerView recyclerView;

    public static NoteData getCurrentNote() {
        if (currentNote != null) {
            return currentNote;
        } else {
            return currentNote = new NoteData();
        }
    }

    public static NotesListFragment newInstance(boolean isFavoriteList) {
        NotesListFragment.isFavoriteList = isFavoriteList;
        return new NotesListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);

        initNotesList(view, isFavoriteList);

        return view;
    }

    //=======================ContentWork================================

    private void initNotesList(View view, boolean isFavoriteList) {
        data = new NoteSourceImpl(getResources());
        if (isFavoriteList) {
            favoriteData = data.getFavoriteData();
            data = favoriteData;
        }
        recyclerView = view.findViewById(R.id.listRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        noteListAdapter = new NoteListAdapter(data, this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(noteListAdapter);
        noteListAdapter.setOnClickListener(new OnRecyclerViewClickListener() {
            @SuppressLint({"NonConstantResourceId", "NotifyDataSetChanged"})
            @Override
            public void onRecyclerViewClick(View view, int position) {
                switch (view.getId()) {
                    case R.id.item_card_view:
                        String currentNoteName = data.getNoteData(position).getName();
                        String currentNoteContent = data.getNoteData(position).getNoteContent();
                        int currentNoteIsFavorite = data.getNoteData(position).isFavorite();
                        currentNote = new NoteData(currentNoteName, currentNoteContent, currentNoteIsFavorite);
                        showContent();
                        break;
                    case R.id.favoriteButton:
                        if (data.getNoteData(position).isFavorite() == NoteData.TRUE) {
                            data.getNoteData(position).setFavorite(NoteData.FALSE);
                            if(isFavoriteList){
                                data.deleteNote(position);
                                noteListAdapter.notifyDataSetChanged();
                            }
                        } else {
                            data.getNoteData(position).setFavorite(NoteData.TRUE);
                        }
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

    //========================MainMenuWork==================================

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.notes_list_fragment, menu);
    }

    @SuppressLint({"NonConstantResourceId", "NotifyDataSetChanged"})
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionAdd:
                if (isFavoriteList) {
                    data.addNote(new NoteData(NoteData.TRUE));
                } else {
                    data.addNote(new NoteData());
                }
                noteListAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(0);
                break;
            case R.id.actionClearAll:
                data.clearAllNote();
                noteListAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //========================ContextMenuWork==================================


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        requireActivity().getMenuInflater().inflate(R.menu.context_recycler_view, menu);
    }

    @SuppressLint({"NonConstantResourceId", "NotifyDataSetChanged"})
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = noteListAdapter.getClickContextPosition();
        switch (item.getItemId()) {
            case R.id.action_edit:
                //TODO: no realisation
                noteListAdapter.notifyDataSetChanged();
                break;
            case R.id.action_delete:
                data.deleteNote(position);
                noteListAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }
}