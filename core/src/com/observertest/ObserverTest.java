package com.observertest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ObserverTest extends ApplicationAdapter
{
	private World world;
	private SpriteBatch batch;
	private FPSLogger fpsLogger;
	
	@Override
	public void create()
	{
		world = new World();

		batch = new SpriteBatch();
		fpsLogger = new FPSLogger();
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
