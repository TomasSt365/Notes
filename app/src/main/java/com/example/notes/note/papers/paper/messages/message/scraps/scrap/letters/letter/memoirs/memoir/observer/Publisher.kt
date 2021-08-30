package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.observer

import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.NoteData
import java.util.*

class Publisher {
    private val observers: MutableList<Observer>

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

    init {
        observers = ArrayList()
    }
}