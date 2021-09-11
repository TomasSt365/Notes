package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.ui.main.removeDialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.Const
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.R
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.NoteData
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.ui.main.NotesListFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RemoveDialogFragment : BottomSheetDialogFragment(), View.OnClickListener {
    private var position: Int = -1
    private var note: NoteData? = null
    private var dialogClickListener: OnRemoveDialogClickListener? = null

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreate(savedInstanceState: Bundle?) {
        if (arguments != null) {
            note = arguments!!.getParcelable(NOTE_DIALOG_KEY)
            position = arguments!!.getInt(POSITION_DIALOG_KEY)
        } else {
            note = NotesListFragment.getNoteToDelete()
            position = NotesListFragment.getPositionToRemote()
        }
        setStyle(STYLE_NO_TITLE, R.style.RemoveDialogStyle)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_remove_note_dialog, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        @SuppressLint("CutPasteId") val positiveBtn = view.findViewById<Button>(R.id.positiveBtn)
        @SuppressLint("CutPasteId") val negativeBtn = view.findViewById<Button>(R.id.negativeBtn)
        val noteToDelete = view.findViewById<TextView>(R.id.noteToDelete)
        val message = view.findViewById<TextView>(R.id.message)
        if (note == Const.ALL_NOTES) {
            message.setText(R.string.clearAllDialogMessage)
        } else {
            message.setText(R.string.removeNoteDialogMessage)
            noteToDelete.text = note!!.title
        }
        positiveBtn.setOnClickListener(this)
        negativeBtn.setOnClickListener(this)
    }

    @SuppressLint("NonConstantResourceId")
    override fun onClick(view: View) {
        dismiss()
        if (dialogClickListener != null) {
            when (view.id) {
                R.id.positiveBtn -> dialogClickListener!!.onPositiveButtonClick(position)
                R.id.negativeBtn -> dialogClickListener!!.onNegativeButtonClick()
            }
        }
    }

    fun setOnDialogClickListener(dialogClickListener: OnRemoveDialogClickListener?) {
        this.dialogClickListener = dialogClickListener
    }

    companion object {
        private const val NOTE_DIALOG_KEY = "NOTE_DIALOG_KEY"
        private const val POSITION_DIALOG_KEY = "POSITION_DIALOG_KEY"
        fun newInstance(): RemoveDialogFragment {
            val fragment = RemoveDialogFragment()
            val bundle = Bundle()
            bundle.putParcelable(NOTE_DIALOG_KEY, NotesListFragment.getNoteToDelete())
            bundle.putInt(POSITION_DIALOG_KEY, NotesListFragment.getPositionToRemote())
            fragment.arguments = bundle
            return fragment
        }
    }
}