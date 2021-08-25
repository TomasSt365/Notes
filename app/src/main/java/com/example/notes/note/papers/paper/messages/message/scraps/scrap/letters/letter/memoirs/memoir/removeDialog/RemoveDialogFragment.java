package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.removeDialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.Const;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.R;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.NoteData;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class RemoveDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private static final String NOTE_DIALOG_KEY = "NOTE_DIALOG_KEY";
    private static final String POSITION_DIALOG_KEY = "POSITION_DIALOG_KEY";
    private int position;
    private NoteData note;
    private OnRemoveDialogClickListener dialogClickListener;

    public static RemoveDialogFragment newInstance(int position, NoteData note) {
        RemoveDialogFragment fragment = new RemoveDialogFragment(position, note);
        Bundle bundle = new Bundle();
        bundle.putParcelable(NOTE_DIALOG_KEY, note);
        bundle.putInt(POSITION_DIALOG_KEY, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    public RemoveDialogFragment(int position, NoteData note) {
        this.position = position;
        this.note = note;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            note = getArguments().getParcelable(NOTE_DIALOG_KEY);
            position = getArguments().getInt(POSITION_DIALOG_KEY);
        }
        setStyle(BottomSheetDialogFragment.STYLE_NO_TITLE, R.style.RemoveDialogStyle);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_remove_note_dialog, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        @SuppressLint("CutPasteId") Button positiveBtn = view.findViewById(R.id.positiveBtn);
        @SuppressLint("CutPasteId") Button negativeBtn = view.findViewById(R.id.negativeBtn);
        TextView noteToDelete = view.findViewById(R.id.noteToDelete);
        TextView message = view.findViewById(R.id.message);

        if (note == Const.ALL_NOTES) {
            message.setText(R.string.clearAllDialogMessage);
        } else {
            message.setText(R.string.removeNoteDialogMessage);
            noteToDelete.setText(note.getTitle());
        }

        positiveBtn.setOnClickListener(this);
        negativeBtn.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        dismiss();
        if (dialogClickListener != null) {
            switch (view.getId()) {
                case R.id.positiveBtn:
                    dialogClickListener.onPositiveButtonClick(position);
                    break;
                case R.id.negativeBtn:
                    dialogClickListener.onNegativeButtonClick();
                    break;
                default:
                    break;
            }
        }
    }

    public void setOnDialogClickListener(OnRemoveDialogClickListener dialogClickListener) {
        this.dialogClickListener = dialogClickListener;
    }

}
