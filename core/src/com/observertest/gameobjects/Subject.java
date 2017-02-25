package com.observertest.gameobjects;

import com.observertest.Observer;
import com.observertest.enums.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carl on 21/02/2017.
 */
public abstract class Subject extends GameObject
{
    private List<Observer> observers;

    public Subject()
    {
        observers = new ArrayList<Observer>();
    }

    protected boolean sendEvent(GameObject gameObject, Event event)
    {
        for(Observer observer : observers)
        {
            return observer.respond(gameObject, event);
        }

        return false;
    }

    public void addObserver(Observer observer)
    {
        observers.add(observer);
    }

    public void removeObserver(Observer observer)
    {
        observers.remove(observer);
    }
}
