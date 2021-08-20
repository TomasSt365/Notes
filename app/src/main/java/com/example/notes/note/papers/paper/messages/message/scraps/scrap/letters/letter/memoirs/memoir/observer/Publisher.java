package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.observer;

import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.NoteData;

import java.util.ArrayList;
import java.util.List;

public class Publisher {
    private List<Observer> observers;

    public Publisher() {
        this.observers = new ArrayList<Observer>();
    }

    public void subscribe(Observer observer){
        observers.add(observer);
        unsubscribe(observer);
    }

    public void unsubscribe(Observer observer){
        observers.remove(observer);
    }

    public void notifyTask(NoteData note){
        for(Observer observer:observers){
            observer.updateState(note);
            unsubscribe(observer);
        }
    }
}
