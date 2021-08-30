package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.observer

import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.NoteData

class Publisher() {
    private var observers: MutableList<Observer> = ArrayList()

    private constructor(observers: MutableList<Observer>) : this() {
        this.observers = observers
    }

    fun subscribe(observer: Observer) {
        observers.add(observer)
    }

    fun unsubscribe(observer: Observer) {
        observers.remove(observer)
    }

    fun notifyTask(note: NoteData?) {
        for (observer in observers) {
            observer.updateState(note)
            unsubscribe(observer)
        }
    }

    fun copy():Publisher{
        return Publisher(this.observers)
    }

    init {
        observers = ArrayList()
    }
}