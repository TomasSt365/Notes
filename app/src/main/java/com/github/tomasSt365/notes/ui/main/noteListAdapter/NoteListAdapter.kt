package com.github.tomasSt365.notes.ui.main.noteListAdapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ToggleButton
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.github.tomasSt365.notes.R
import com.github.tomasSt365.notes.data.notesSources.NotesSource

class NoteListAdapter(
    private val fragment: Fragment) : RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {
    private var dataSource: NotesSource? = null
    private var listener: OnRecyclerViewClickListener? = null
    var clickContextPosition = 0

    fun setOnClickListener(listener: OnRecyclerViewClickListener?) {
        this.listener = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataSource(dataSource: NotesSource?) {
        this.dataSource = dataSource
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.noteName.text = dataSource!!.getNoteData(position)!!.title
        holder.favoriteButton.isChecked = dataSource!!.getNoteData(position)!!.isFavorite
    }

    override fun getItemCount(): Int {
        return dataSource!!.size()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        OnRecyclerViewClickListener, View.OnClickListener, OnLongClickListener {

        private val noteCardView: CardView = itemView.findViewById(R.id.item_card_view)
        val noteName: TextView = itemView.findViewById(R.id.noteName)
        val favoriteButton: ToggleButton = itemView.findViewById(R.id.favoriteButton)

        override fun onRecyclerViewClick(view: View, position: Int) {
            listener!!.onRecyclerViewClick(view, position)
        }

        override fun onClick(view: View) {
            onRecyclerViewClick(view, adapterPosition)
        }

        @SuppressLint("NonConstantResourceId")
        override fun onLongClick(view: View): Boolean {
            when (view.id) {
                R.id.item_card_view -> return onLongItemCardViewClick()
            }
            return false
        }

        private fun onLongItemCardViewClick(): Boolean {
            val v = fragment
                .requireActivity()
                .getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(25, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                //deprecated in API 26
                v.vibrate(25)
            }
            clickContextPosition = adapterPosition
            noteCardView.showContextMenu()
            return true
        }

        init {
            fragment.registerForContextMenu(noteCardView)
            noteCardView.setOnClickListener(this)
            noteCardView.setOnLongClickListener(this)
            favoriteButton.setOnClickListener(this)
        }
    }
}