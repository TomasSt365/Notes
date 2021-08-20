package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.MainActivity;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.R;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.NoteData;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.observer.Publisher;
import com.google.android.material.textfield.TextInputEditText;

public class EditFragment extends Fragment {
    private static final String ARG_NOTE = "ARG_NOTE";

    private NoteData note;
    private Publisher publisher;
    private EditText titleEdit;
    private EditText noteContentEdit;

    public static EditFragment newInstance(NoteData note) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    public static EditFragment newInstance() {
        EditFragment fragment = new EditFragment();
        return fragment;
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
        if(getArguments()!=null){
            note = getArguments().getParcelable(ARG_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);
        initView(view);
        if(note!=null){
            fillNoteInEditMode();
        }
        return view;
    }

    private void fillNoteInEditMode() {
        titleEdit.setText(note.getTitle());
        noteContentEdit.setText(note.getNoteContent());
        //TODO:не забыть считать значение кнопки закладок(после добавления)
    }

    private void initView(View view) {
        titleEdit = view.findViewById(R.id.titleEdit);
        noteContentEdit = view.findViewById(R.id.noteContentEdit);
    }

    @Override
    public void onStop() {
        super.onStop();
        note = collectNote();
    }

    private NoteData collectNote() {
        String title = titleEdit.getText().toString();
        String noteContent = noteContentEdit.getText().toString();
        byte isFavorite;
        if(note!=null){
            isFavorite = note.isFavorite();
        }else {
            isFavorite = NoteData.FALSE;
        }//TODO:добавить кноку в мекет(В закладках/не закладках) и считывать с неё

        return new NoteData(title, noteContent, isFavorite);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notifyTask(note);
        publisher = null;
    }
}
