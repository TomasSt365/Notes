package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

public class NoteData implements Parcelable {
    public static final int TRUE = 1;
    public static final int FALSE = 0;
    private String name;
    private String noteContent;
    private String dateOfCreation;
    private String dateOfChange;
    private int isFavorite;

    public NoteData() {
        this.name = "";
        this.noteContent = "";
        this.isFavorite = FALSE;
    }

    public NoteData(String name) {
        this.name = name;
        noteContent = "";
        isFavorite = FALSE;
    }

    public NoteData(String name, String noteContent) {
        this.name = name;
        this.noteContent = noteContent;
        isFavorite = FALSE;
    }

    public NoteData(String name, String noteContent, int isFavorite) {
        this.name = name;
        this.noteContent = noteContent;
        this.isFavorite = isFavorite;
    }

    protected NoteData(Parcel in) {
        name = in.readString();
        noteContent = in.readString();
        dateOfCreation = in.readString();
        dateOfChange = in.readString();
        isFavorite = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(noteContent);
        dest.writeString(dateOfCreation);
        dest.writeString(dateOfChange);
        dest.writeInt(isFavorite);
    }

    public static final Creator<NoteData> CREATOR = new Creator<NoteData>() {
        @Override
        public NoteData createFromParcel(Parcel in) {
            return new NoteData(in);
        }

        @Override
        public NoteData[] newArray(int size) {
            return new NoteData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getDateOfChange() {
        return dateOfChange;
    }

    public void setDateOfChange(String dateOfChange) {
        this.dateOfChange = dateOfChange;
    }

    public int isFavorite() {
        return isFavorite;
    }

    public void setFavorite(int favorite) {
        isFavorite = favorite;
    }
}
