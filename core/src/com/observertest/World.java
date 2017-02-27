package com.observertest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.observertest.gameobjects.GameObject;
import com.observertest.gameobjects.Ship;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carl on 27/02/2017.
 */
public class World
{
    private List<GameObject> gameObjects, gameObjectsToAdd, gameObjectsToRemove;

    public World()
    {
        gameObjects = new ArrayList<GameObject>();
        gameObjectsToAdd = new ArrayList<GameObject>();
        gameObjectsToRemove = new ArrayList<GameObject>();

        gameObjects.add(new Ship(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2),
                        new Vector2(50, 50), 5.0, 0.0, 5.0, Color.RED));
    }

    public void update()
    {
        for(GameObject gameObject : gameObjects)
        {
            gameObject.update();
        }

        gameObjects.removeAll(gameObjectsToRemove);
		gameObjects.addAll(gameObjectsToAdd);
		gameObjectsToRemove.clear();
		gameObjectsToAdd.clear();
    }

    public void render(SpriteBatch batch)
    {
        for(GameObject gameObject : gameObjects)
        {
            gameObject.render(batch);
        }
    }
}
