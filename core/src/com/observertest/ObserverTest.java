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

public class ObserverTest extends ApplicationAdapter
{
	private World world;
	private FPSLogger fpsLogger;
	private SpriteBatch batch;
	
	@Override
	public void create()
	{
		world = new World();

		fpsLogger = new FPSLogger();
		batch = new SpriteBatch();
	}

	@Override
	public void render()
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		world.update();

		batch.begin();
		world.render(batch);
		batch.end();

		fpsLogger.log();
	}

	@Override
	public void dispose()
	{
		batch.dispose();
	}
}
