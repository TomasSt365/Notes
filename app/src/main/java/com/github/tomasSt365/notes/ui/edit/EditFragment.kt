package com.github.tomasSt365.notes.ui.edit

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.tomasSt365.notes.MainActivity
import com.github.tomasSt365.notes.R
import com.github.tomasSt365.notes.data.NoteData
import com.github.tomasSt365.notes.observer.Publisher
import java.util.*

class EditFragment : Fragment() {
    private var note: NoteData? = null
    private var publisher: Publisher? = null
    private var titleEdit: EditText? = null
    private var noteContentEdit: EditText? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        publisher = (context as MainActivity).publisher
    }

    override fun onDetach() {
        super.onDetach()
        publisher = null
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            note = arguments!!.getParcelable(ARG_NOTE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit, container, false)
        initView(view)
        if (note != null) {
            fillNoteInEditMode()
        }
        return view
    }

    private fun fillNoteInEditMode() {
        titleEdit!!.setText(note!!.title)
        noteContentEdit!!.setText(note!!.noteContent)
    }

    private fun initView(view: View) {
        titleEdit = view.findViewById(R.id.titleEdit)
        noteContentEdit = view.findViewById(R.id.noteContentEdit)
    }

    override fun onStop() {
        super.onStop()
        note = collectNote()
        publisher!!.notifyTask(note)
        Toast.makeText(requireActivity(), R.string.changesSavedText, Toast.LENGTH_SHORT)
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()

        publisher = null
    }

    private fun collectNote(): NoteData? {
        val title = titleEdit!!.text.toString()
        val noteContent = noteContentEdit!!.text.toString()
        val dateOfEdit: Date? = null //todo:???????????????? ???????????????????? ?????????????? ????????

        if (note != null) {
            note!!.title = title
            note!!.noteContent = noteContent
        } else {
            val dateOfCreation: Date? = null //todo:???????????????? ???????????????????? ?????????????? ????????
            val noteBuilder = NoteData.Builder()
                .setTitle(title)
                .setNoteContent(noteContent)
                .setIsFavorite(false)
                .setDateOfEdit(dateOfEdit)
                .setDateOfCreation(dateOfCreation)
            note = noteBuilder.build()
        }
        return note
    }

    companion object {
        private const val ARG_NOTE = "ARG_NOTE"
        fun newInstance(note: NoteData?): EditFragment {
            val fragment = EditFragment()
            val args = Bundle()
            args.putParcelable(ARG_NOTE, note)
            fragment.arguments = args
            return fragment
        }

        fun newInstance(): EditFragment {
            return EditFragment()
        }
    }
}