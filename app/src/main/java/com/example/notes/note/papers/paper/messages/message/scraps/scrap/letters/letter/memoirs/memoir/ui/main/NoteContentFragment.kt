package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.R
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.NoteData

class NoteContentFragment : Fragment() {
    private var currentNote: NoteData? = null

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentNote = if (arguments != null) {
            arguments!!.getParcelable(DATA_KEY)
        } else {
            NoteData.Builder().build()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_note_content, container, false)
        val name = view.findViewById<TextView>(R.id.noteTitle)
        val noteText = view.findViewById<TextView>(R.id.noteContent)
        name.text = currentNote!!.title
        noteText.text = currentNote!!.noteContent
        return view
    }

    companion object {
        private const val DATA_KEY = "KEY"
        fun newInstance(noteData: NoteData?): NoteContentFragment {
            val fragment = NoteContentFragment()
            val bundle = Bundle()
            bundle.putParcelable(DATA_KEY, noteData)
            fragment.arguments = bundle
            return fragment
        }
    }
}