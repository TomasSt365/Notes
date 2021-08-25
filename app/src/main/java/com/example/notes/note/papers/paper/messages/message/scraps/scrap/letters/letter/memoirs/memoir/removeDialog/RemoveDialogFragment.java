package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.removeDialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class RemoveDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private OnRemoveDialogClickListener dialogClickListener;

    public static RemoveDialogFragment newInstance() {
        return new RemoveDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_remove_note_dialog, container, false);
        setCancelable(false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        @SuppressLint("CutPasteId") Button positiveBtn = view.findViewById(R.id.positiveBtn);
        @SuppressLint("CutPasteId") Button negativeBtn = view.findViewById(R.id.positiveBtn);

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
                    dialogClickListener.onPositiveButtonClick();
                    break;
                case R.id.negativeBtn:
                    dialogClickListener.onNegativeButtonClick();
                    break;
                default:
                    break;
            }
        }
    }

    void setOnClickListener(OnRemoveDialogClickListener dialogClickListener) {
        this.dialogClickListener = dialogClickListener;
    }

}
