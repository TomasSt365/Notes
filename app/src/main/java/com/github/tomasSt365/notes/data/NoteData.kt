package com.github.tomasSt365.notes.data

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import java.util.*

class NoteData : Parcelable {
    var id: String?

    var title: String?
    var noteContent: String?
    var isFavorite: Boolean

    //TODO дата в приложении не реализована
    var dateOfCreation: Date? = null
    var dateOfEdit: Date? = null

    constructor(builder: Builder) {
        id = builder.id
        title = builder.title
        noteContent = builder.noteContent
        dateOfCreation = builder.dateOfCreation
        dateOfEdit = builder.dateOfChange
        isFavorite = builder.isFavorite
    }

    fun copy(): NoteData {
        val builder = Builder()
            .setTitle(title)
            .setNoteContent(noteContent)
            .setDateOfCreation(dateOfCreation)
            .setDateOfEdit(dateOfEdit)
            .setIsFavorite(isFavorite)

        return builder.build()
    }

    /**Builder*/
    class Builder {
        var id: String? = null
        var title: String? = ""
        var noteContent: String? = ""
        var dateOfCreation: Date? = null
        var dateOfChange: Date? = null
        var isFavorite = false

        fun setId(id: String?): Builder {
            this.id = id
            return this
        }

        fun setTitle(title: String?): Builder {
            this.title = title
            return this
        }

        fun setNoteContent(noteContent: String?): Builder {
            this.noteContent = noteContent
            return this
        }

        fun setDateOfCreation(dateOfCreation: Date?): Builder {
            this.dateOfCreation = dateOfCreation
            return this
        }

        fun setDateOfEdit(dateOfEdit: Date?): Builder {
            dateOfChange = dateOfEdit
            return this
        }

        fun setIsFavorite(isFavorite: Boolean): Builder {
            this.isFavorite = isFavorite
            return this
        }

        fun build(): NoteData {
            return NoteData(this)
        }
    }

    /**Parcelable implementation*/
    private constructor(`in`: Parcel) {
        id = `in`.readString()
        title = `in`.readString()
        noteContent = `in`.readString()
        isFavorite = `in`.readByte().toInt() != 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(id)
        dest.writeString(title)
        dest.writeString(noteContent)
        dest.writeByte((if (isFavorite) 1 else 0).toByte())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @SuppressLint("ParcelCreator")
        val CREATOR: Creator<NoteData?> = object : Creator<NoteData?> {
            override fun createFromParcel(`in`: Parcel): NoteData {
                return NoteData(`in`)
            }

            override fun newArray(size: Int): Array<NoteData?> {
                return arrayOfNulls(size)
            }
        }
    }
}