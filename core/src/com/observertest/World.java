package com.observertest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.observertest.gameobjects.Bullet;
import com.observertest.gameobjects.GameObject;
import com.observertest.gameobjects.Ship;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carl on 27/02/2017.
 */
public class World
{
    private List<GameObject> activeGameObjects, inactiveGameObjects, activeGameObjectsToAdd, activeGameObjectsToRemove;

    public World()
    {
        activeGameObjects = new ArrayList<GameObject>();
        inactiveGameObjects = new ArrayList<GameObject>();
        activeGameObjectsToAdd = new ArrayList<GameObject>();
        activeGameObjectsToRemove = new ArrayList<GameObject>();

        activeGameObjects.add(new Ship(this, new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2),
                        new Vector2(50, 50), 5.0, 0.0, 5.0, Color.RED));
    }

    void update()
    {
        for(GameObject gameObject : activeGameObjects)
        {
            gameObject.update();
        }

        if(!activeGameObjectsToRemove.isEmpty())
        {
            activeGameObjects.removeAll(activeGameObjectsToRemove);
            activeGameObjectsToRemove.clear();
        }

        if(!activeGameObjectsToAdd.isEmpty())
        {
            activeGameObjects.addAll(activeGameObjectsToAdd);
            activeGameObjectsToAdd.clear();
        }
    }

    void render(SpriteBatch batch)
    {
        for(GameObject gameObject : activeGameObjects)
        {
            gameObject.render(batch);
        }
    }

    public void add(GameObject gameObject)
    {
        activeGameObjectsToAdd.add(gameObject);
    }

    public void addBullet(Vector2 position, Vector2 dimension, double speed, double angle, Color colour)
    {
        for(GameObject gameObject : inactiveGameObjects)
        {
            if(gameObject instanceof Bullet)
            {
                ((Bullet)gameObject).initialise(position, dimension, speed, angle, colour);
                activeGameObjectsToAdd.add(gameObject);
                inactiveGameObjects.remove(gameObject);
                return;
            }
        }

        activeGameObjectsToAdd.add(new Bullet(this, position, dimension, speed, angle, colour));
    }

    public void remove(GameObject gameObject)
    {
        if(activeGameObjects.contains(gameObject)) // Is this check necessary?
        {
            inactiveGameObjects.add(gameObject);
            activeGameObjectsToRemove.add(gameObject);
        }
    }

    public boolean contains(GameObject gameObject)
    {
        return activeGameObjects.contains(gameObject) || inactiveGameObjects.contains(gameObject);
    }
}
