package com.observertest;

import com.observertest.gameobjects.CollidableObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carl on 02/03/2017.
 */
public class CollisionHandler
{
    private List<CollidableObject> collidableObjects;

    public CollisionHandler()
    {
        collidableObjects = new ArrayList<CollidableObject>();
    }

    public void handleCollisions()
    {
        for(CollidableObject collidableObject : collidableObjects)
        {
            for(CollidableObject otherCollidableObject : collidableObjects)
            {
                if(collidableObject.collidesWith(otherCollidableObject))
                {
                    System.out.println("Collision!");

                    collidableObject.resolveCollision(otherCollidableObject);
                }
            }
        }
    }

    public void add(CollidableObject collidableObject)
    {
        collidableObjects.add(collidableObject);
    }

    public void remove(CollidableObject collidableObject)
    {
        if(collidableObjects.contains(collidableObject))
        {
            collidableObjects.remove(collidableObject);
        }
    }
}
