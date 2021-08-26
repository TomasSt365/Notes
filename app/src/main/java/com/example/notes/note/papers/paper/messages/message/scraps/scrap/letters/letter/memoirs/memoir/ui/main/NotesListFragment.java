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
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.Const;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.MainActivity;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.R;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.NoteData;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.notesSources.NotesSource;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.notesSources.NotesSourceRemoteImpl;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.notesSources.NotesSourceResponse;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.observer.Observer;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.observer.Publisher;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.ui.Navigation;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.ui.main.removeDialog.OnRemoveDialogClickListener;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.ui.main.removeDialog.RemoveDialogFragment;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.ui.main.NoteListAdapter.NoteListAdapter;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.ui.main.NoteListAdapter.OnRecyclerViewClickListener;

public class NotesListFragment extends Fragment implements OnRemoveDialogClickListener {
    private static NoteData currentNote;
    private static boolean isFavoriteList;
    private static NotesSource data;
    private NoteListAdapter noteListAdapter;
    private RecyclerView recyclerView;
    private Publisher publisher;
    private Navigation navigation;

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
        navigation = new Navigation(requireActivity().getSupportFragmentManager());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        publisher = null;
        navigation = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*data = new NotesSourceLocalImpl(getResources()).init(new NotesSourceResponse() {
            @Override
            public void initialized(NotesSource notesSource) {

            }
        });
        if (isFavoriteList) {
            data = data.getFavoriteData();
            //TODO:Костыль. Данные будут добавляться, и удаляться только во вкладке "избранные" из-за этой подмены данных
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        data = new NotesSourceRemoteImpl().init(new NotesSourceResponse() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void initialized(NotesSource notesSource) {
                noteListAdapter.notifyDataSetChanged();
            }
        });
        if (isFavoriteList) {
            data = data.getFavoriteData();
            //TODO:Костыль. Данные будут добавляться и удаляться только во вкладке "избранные" из-за этой подмены данных
        }
        initNotesList(view);
        noteListAdapter.setDataSource(data);
        return view;
    }

    //=======================ContentWork================================//

    private void initNotesList(View view) {
        recyclerView = view.findViewById(R.id.listRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        noteListAdapter = new NoteListAdapter(this);

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
                        boolean currentNoteIsFavorite = data.getNoteData(position).isFavorite();
                        NoteData.Builder noteBuilder = new NoteData
                                .Builder()
                                .setTitle(currentNoteTitle)
                                .setNoteContent(currentNoteContent)
                                .setIsFavorite(currentNoteIsFavorite);

                        currentNote = noteBuilder.build();
                        showContent();
                        break;
                    case R.id.favoriteButton:
                        data.getNoteData(position).setFavorite(!data.getNoteData(position).isFavorite());
                        data.editNote(position, data.getNoteData(position));
                        // TODO: Должен быть наблюдатель,
                        //  который будет сортировать данные и убирать обычные заметки, если включен FavoriteList
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void showContent() {
        if (MainActivity.isLandScape()) {
            navigation.addFragment(R.id.content_container,
                    NoteContentFragment.newInstance(currentNote), false);
        } else {
            navigation.addFragment(R.id.main_container,
                    NoteContentFragment.newInstance(currentNote), true);
        }
    }

    //========================MainMenuWork==================================//

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.notes_list_fragment_menu, menu);
    }

    @SuppressLint({"NonConstantResourceId"})
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionAdd:
                onActionAddClick();
                break;
            case R.id.actionClearAll:
                showRemoveDialogFragment(Const.ALL_NOTES_POSITION);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void onActionAddClick() {
        navigation.addFragment(R.id.main_container, EditFragment.newInstance(),true);
        publisher.subscribe(new Observer() {
            @Override
            public void updateState(NoteData note) {
                if (isFavoriteList) {
                    note.setFavorite(true);
                }
                data.addNote(note);
                noteListAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(0);
            }
        });

    }

    //========================ContextMenuWork==================================//

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
                showRemoveDialogFragment(position);
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void onActionEditClick(int position) {
        navigation.addFragment(R.id.main_container,
                EditFragment.newInstance(data.getNoteData(position)),true);
        publisher.subscribe(new Observer() {
            @Override
            public void updateState(NoteData note) {
                data.editNote(position,note);
                noteListAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(0);
            }
        });
    }

    //==========================RemoveDialogWork===============================//

    void showRemoveDialogFragment(int position) {
        NoteData note;
        if (position == Const.ALL_NOTES_POSITION) {
            note = Const.ALL_NOTES;
        } else {
            note = data.getNoteData(position);
        }
        RemoveDialogFragment fragment = RemoveDialogFragment.newInstance(position, note);
        fragment.setOnDialogClickListener(this);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragment.show(fragmentManager, "");
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onPositiveButtonClick(int position) {
        if (position == Const.ALL_NOTES_POSITION) {
            data.clearAllNote();
        } else {
            data.deleteNote(position);
        }

        noteListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNegativeButtonClick() {}

}