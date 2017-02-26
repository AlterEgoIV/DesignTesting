package com.observertest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.observertest.enums.Data;
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
	private List<GameObject> gameObjects, gameObjectsToAdd, gameObjectsToRemove;
	private List<Bullet> inactiveBullets;
	public static long frameCount = 0;
	
	@Override
	public void create()
	{
		fpsLogger = new FPSLogger();
		batch = new SpriteBatch();
		gameObjects = new ArrayList<GameObject>();
		gameObjectsToAdd = new ArrayList<GameObject>();
		gameObjectsToRemove = new ArrayList<GameObject>();
		inactiveBullets = new ArrayList<Bullet>();

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
		gameObjectsToRemove.clear();
		gameObjectsToAdd.clear();

		frameCount++;
		fpsLogger.log();
	}

	@Override
	public void dispose()
	{
		batch.dispose();
	}

	@Override
	public void receiveEvent(Event event, GameObject gameObject)
	{
		switch(event)
		{
			case SHIP_FIRED_BULLET:
			{
				Ship ship = (Ship)gameObject;
				Bullet bullet;

				Vector2 position = new Vector2((Vector2)ship.readData.get(Data.POSITION));
				Vector2 dimension = new Vector2(10, 1);
				double speed = (Double)ship.readData.get(Data.SPEED) * 2.0;
				double angle = (Double)ship.readData.get(Data.ANGLE);
				Color colour = (Color)ship.readData.get(Data.COLOUR);

				if(inactiveBullets.size() == 0)
				{
					bullet = new Bullet(position, dimension, speed, angle, colour);
					bullet.addObserver(this);
				}
				else
				{
					inactiveBullets.get(inactiveBullets.size() - 1).initialise(position, dimension, speed, angle, colour);
					bullet = inactiveBullets.get(inactiveBullets.size() - 1);
					inactiveBullets.remove(bullet);
				}

				gameObjectsToAdd.add(bullet);

				break;
			}

			case REMOVE_BULLET:
			{
				inactiveBullets.add((Bullet)gameObject);
				gameObjectsToRemove.add(gameObject);

				break;
			}
		}
	}
}
