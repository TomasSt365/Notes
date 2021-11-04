package com.github.tomasSt365.notes.observer

import com.github.tomasSt365.notes.data.NoteData

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

    fun copy(): Publisher {
        return Publisher(this.observers)
    }

    init {
        observers = ArrayList()
    }
}