package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.MainActivity;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.R;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.NoteData;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.NoteSource;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.NoteSourceImpl;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.observer.Observer;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.observer.Publisher;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.ui.NoteListAdapter.NoteListAdapter;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.ui.NoteListAdapter.OnRecyclerViewClickListener;

public class NotesListFragment extends Fragment {
    private static NoteData currentNote;
    private static boolean isFavoriteList;
    private static NoteSource data;
    private NoteListAdapter noteListAdapter;
    private RecyclerView recyclerView;
    private Publisher publisher;

    public static NoteData getCurrentNote() {
        if (currentNote != null) {
            return currentNote;
        } else {
            return currentNote = new NoteData.Builder().build();
        }
    }

    public static NotesListFragment newInstance(boolean isFavoriteList) {
        NotesListFragment.isFavoriteList = isFavoriteList;
        return new NotesListFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        publisher = ((MainActivity) context).getPublisher();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        publisher = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = new NoteSourceImpl(getResources()).init();
        if (isFavoriteList) {
            data = data.getFavoriteData();
            //TODO:Костыль. Данные будут добавляться, и удаляться только во вкладке "избранные" из-за этой подмены данных
        }
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
                        String currentNoteTitle = data.getNoteData(position).getTitle();
                        String currentNoteContent = data.getNoteData(position).getNoteContent();
                        byte currentNoteIsFavorite = data.getNoteData(position).isFavorite();
                        NoteData.Builder noteBuilder = new NoteData
                                .Builder()
                                .setTitle(currentNoteTitle)
                                .setNoteContent(currentNoteContent)
                                .setIsFavorite(currentNoteIsFavorite);

                        currentNote = noteBuilder.build();
                        showContent();
                        break;
                    case R.id.favoriteButton:
                        if (data.getNoteData(position).isFavorite() == NoteData.TRUE) {
                            data.getNoteData(position).setFavorite(NoteData.FALSE);
                        } else
                            data.getNoteData(position).setFavorite(NoteData.TRUE);
                        if (isFavoriteList) {
                            data.deleteNote(position);
                            //TODO:костыль, нужно удалять только из листа с закладками, а не навсегда
                            // (Должен быть наблюдатель, который будет сортировать данные и убирать обычные заметки, если включен FavoriteList)
                            noteListAdapter.notifyDataSetChanged();
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

    private void showEditFragment() {
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("")
                .replace(R.id.main_container, EditFragment.newInstance())
                .commit();
    }

    private void showEditFragment(NoteData note) {
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("")
                .replace(R.id.main_container, EditFragment.newInstance(note))
                .commit();
    }

    //========================MainMenuWork==================================

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.notes_list_fragment, menu);
    }

    @SuppressLint({"NonConstantResourceId", "NotifyDataSetChanged"})
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionAdd:
                onActionAddClick();
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

    @SuppressLint("NotifyDataSetChanged")
    private void onActionAddClick() {
        showEditFragment();
        publisher.subscribe(new Observer() {
            @Override
            public void updateState(NoteData note) {
                if (isFavoriteList) {
                    note.setFavorite(NoteData.TRUE);
                }
                data.addNote(note);
                noteListAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(0);
            }
        });

    }

    //========================ContextMenuWork==================================


    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        requireActivity().getMenuInflater().inflate(R.menu.context_recycler_view, menu);
    }

    @SuppressLint({"NonConstantResourceId", "NotifyDataSetChanged"})
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = noteListAdapter.getClickContextPosition();
        switch (item.getItemId()) {
            case R.id.action_edit:
                onActionEditClick(position);
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

    @SuppressLint("NotifyDataSetChanged")
    private void onActionEditClick(int position) {
        showEditFragment(data.getNoteData(position));
        publisher.subscribe(new Observer() {
            @Override
            public void updateState(NoteData note) {
                data.deleteNote(position);
                data.addNote(note);
                //решил сделать такую подмену, чтобы отредактированные заметки отображались вверху
                //TODO: костыль
                noteListAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(0);
            }
        });
    }

}