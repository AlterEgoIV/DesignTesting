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

    protected void sendEvent(GameObject gameObject, Event event)
    {
        for(Observer observer : observers)
        {
            observer.receiveEvent(gameObject, event);
        }
    }

    public boolean addObserver(Observer newObserver)
    {
        for(Observer observer : observers)
        {
            if(newObserver.equals(observer)) return false;
        }

        return observers.add(newObserver);
    }

    public boolean removeObserver(Observer currentObserver)
    {
        for(Observer observer : observers)
        {
            if(currentObserver.equals(observer)) return observers.remove(observer);
        }

        return false;
    }
}
