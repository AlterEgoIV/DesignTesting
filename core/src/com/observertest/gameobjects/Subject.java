package com.observertest.gameobjects;

import com.observertest.Observer;
import com.observertest.enums.Data;
import com.observertest.enums.Event;

import java.util.*;

/**
 * Created by Carl on 21/02/2017.
 */
public abstract class Subject extends GameObject
{
    private List<Observer> observers;
    public Map<Data, Object> readData;

    public Subject()
    {
        observers = new ArrayList<Observer>();
        readData = new HashMap<Data, Object>();

        readData.put(Data.POSITION, position);
        readData.put(Data.DIMENSION, dimension);
        readData.put(Data.SPEED, speed);
        readData.put(Data.ANGLE, angle);
        readData.put(Data.COLOUR, colour);

        readData = Collections.unmodifiableMap(readData);
        double d = (double)(Double)readData.get(Data.SPEED);
    }

    protected void sendEvent(Event event, GameObject gameObject)
    {
        for(Observer observer : observers)
        {
            observer.receiveEvent(event, gameObject);
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
