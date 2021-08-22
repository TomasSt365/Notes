package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.ui.NoteListAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.R;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.NotesSource;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {
    private NotesSource dataSource;
    private OnRecyclerViewClickListener listener;
    private final Fragment fragment;
    private int clickContextPosition;

    public int getClickContextPosition() {
        return clickContextPosition;
    }

    public NoteListAdapter(Fragment fragment) {

        this.fragment = fragment;
    }

    public void setOnClickListener(OnRecyclerViewClickListener listener) {
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDataSource(NotesSource dataSource) {
        this.dataSource = dataSource;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.noteName.setText(dataSource.getNoteData(position).getTitle());
        holder.favoriteButton.setChecked(dataSource.getNoteData(position).isFavorite());
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements OnRecyclerViewClickListener,
            View.OnClickListener, View.OnLongClickListener {

        private final CardView noteCardView;
        private final TextView noteName;
        private final ToggleButton favoriteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            noteName = itemView.findViewById(R.id.noteName);
            noteCardView = itemView.findViewById(R.id.item_card_view);
            favoriteButton = itemView.findViewById(R.id.favoriteButton);
            fragment.registerForContextMenu(noteCardView);

            noteCardView.setOnClickListener(this);
            noteCardView.setOnLongClickListener(this);
            favoriteButton.setOnClickListener(this);
        }

        @Override
        public void onRecyclerViewClick(View view, int position) {
            listener.onRecyclerViewClick(view, position);
        }

        @Override
        public void onClick(View view) {
            onRecyclerViewClick(view, getAdapterPosition());
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onLongClick(View view) {
            switch (view.getId()) {
                case R.id.item_card_view:
                    return onLongItemCardViewClick();
                default:
                    break;
            }
            return false;
        }

        private boolean onLongItemCardViewClick() {
            Vibrator v = (Vibrator) fragment
                    .requireActivity()
                    .getSystemService(Context.VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(25, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(25);
            }
            clickContextPosition = getAdapterPosition();
            noteCardView.showContextMenu();
            return true;
        }
    }
}
