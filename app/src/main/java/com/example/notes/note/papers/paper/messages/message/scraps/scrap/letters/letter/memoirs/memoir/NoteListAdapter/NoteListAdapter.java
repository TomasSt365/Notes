package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.NoteListAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.NoteSource;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.R;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder>{

    private NoteSource dataSource;
    private OnRecyclerViewClickListener listener;

    public NoteListAdapter(NoteSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public void setOnClickListener(OnRecyclerViewClickListener listener){
        this.listener = listener;
    }

    public void setDataSource(NoteSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.noteName.setText(dataSource.getNoteData(position).getName());
        holder.favoriteButton.setChecked(dataSource.getNoteData(position).isFavorite());

    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements OnRecyclerViewClickListener, View.OnClickListener{
        private final CardView noteCardView;
        private final TextView noteName;
        private final ToggleButton favoriteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            noteName = itemView.findViewById(R.id.noteName);
            noteCardView = itemView.findViewById(R.id.item_card_view);
            favoriteButton = itemView.findViewById(R.id.favoriteButton);

            noteCardView.setOnClickListener(this);
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
    }
}
