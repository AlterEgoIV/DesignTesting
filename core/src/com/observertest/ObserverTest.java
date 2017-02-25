package com.observertest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.observertest.gameobjects.Bullet;
import com.observertest.gameobjects.GameObject;
import com.observertest.gameobjects.Ship;
import com.observertest.enums.Event;
import com.observertest.gameobjects.Subject;

import java.util.ArrayList;
import java.util.List;

public class ObserverTest extends ApplicationAdapter implements Observer
{
	private FPSLogger fpsLogger;
	private SpriteBatch batch;
	private List<GameObject> gameObjects, gameObjectsToAdd, gameObjectsToRemove, inactiveGameObjects;
	public static long frameCount = 0;
	
	@Override
	public void create()
	{
		fpsLogger = new FPSLogger();
		batch = new SpriteBatch();
		gameObjects = new ArrayList<GameObject>();
		gameObjectsToAdd = new ArrayList<GameObject>();
		gameObjectsToRemove = new ArrayList<GameObject>();
		inactiveGameObjects = new ArrayList<GameObject>();

		gameObjects.add(new Ship(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2),
				new Vector2(50, 50), 5.0, 0.0, 5.0, Color.RED));

		// After all objects have been added
		for(GameObject gameObject : gameObjects)
		{
			if(gameObject instanceof Subject)
			{
				((Subject)gameObject).addObserver(this);
			}
		}
	}

	@Override
	public void render()
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		for(GameObject gameObject : gameObjects)
		{
			gameObject.update();
		}

		batch.begin();
		for(GameObject gameObject : gameObjects)
		{
			gameObject.render(batch);
		}
		batch.end();

		gameObjects.removeAll(gameObjectsToRemove);
		gameObjects.addAll(gameObjectsToAdd);
		gameObjectsToAdd.clear();
		gameObjectsToRemove.clear();

		frameCount++;
		fpsLogger.log();
	}

	@Override
	public void dispose()
	{
		batch.dispose();
	}

	@Override
	public boolean respond(GameObject gameObject, Event event)
	{
		switch(event)
		{
			case SHIP_FIRED_BULLET:
			{
				for(GameObject inactiveGameObject : inactiveGameObjects)
				{
					if(inactiveGameObject instanceof Bullet)
					{
						((Ship)gameObject).constructBullet((Bullet)inactiveGameObject);
						((Subject)inactiveGameObject).addObserver(this);
						gameObjectsToAdd.add(inactiveGameObject);
						inactiveGameObjects.remove(inactiveGameObject);

						return true;
					}
				}

				break;
			}

			case SHIP_CREATED_BULLET:
			{
				((Subject)gameObject).addObserver(this);
				gameObjectsToAdd.add(gameObject);
				break;
			}

			case REMOVE_BULLET:
			{
				((Subject)gameObject).removeObserver(this);
				gameObjectsToRemove.add(gameObject);
				inactiveGameObjects.add(gameObject);
				break;
			}
		}

		return false;
	}
}
