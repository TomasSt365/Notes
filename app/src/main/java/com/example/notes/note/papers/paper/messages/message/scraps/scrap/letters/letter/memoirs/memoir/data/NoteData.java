package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data;

import android.os.Parcel;
import android.os.Parcelable;

public class NoteData implements Parcelable {
    public static final byte TRUE = 1;
    public static final byte FALSE = 0;

    private String title;
    private String noteContent;
    private String dateOfCreation;
    private String dateOfChange;
    private byte isFavorite;

    public NoteData() {
        this.title = "";
        this.noteContent = "";
        this.isFavorite = FALSE;
    }

    public NoteData(String title) {
        this.title = title;
        noteContent = "";
        isFavorite = FALSE;
    }

    public NoteData(byte isFavorite) {
        this.title = "";
        this.noteContent = "";
        this.isFavorite = isFavorite;
    }

    public NoteData(String title, String noteContent) {
        this.title = title;
        this.noteContent = noteContent;
        isFavorite = FALSE;
    }

    public NoteData(String title, String noteContent, byte isFavorite
            /*хотел сделать так чтобы при вызове конструктора можно было присылать TRUE(1),FALSE(0),
            это было в предыдущем курсе, но я забыл как это делаеться*/) {
        this.title = title;
        this.noteContent = noteContent;
        this.isFavorite = isFavorite;
    }

    protected NoteData(Parcel in) {
        title = in.readString();
        noteContent = in.readString();
        dateOfCreation = in.readString();
        dateOfChange = in.readString();
        isFavorite = in.readByte();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(noteContent);
        dest.writeString(dateOfCreation);
        dest.writeString(dateOfChange);
        dest.writeByte(isFavorite);
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

    public String getTitle() {
        return title;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public byte isFavorite() {
        return isFavorite;
    }

    public void setFavorite(byte favorite) {
        isFavorite = favorite;
    }
}
