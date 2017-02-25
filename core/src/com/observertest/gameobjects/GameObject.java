package com.observertest.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Carl on 16/02/2017.
 */
public abstract class GameObject
{
    protected Vector2 position, dimension, direction;
    protected double speed, angle, rotationSpeed;
    protected Texture image;
    protected Color color;

    protected GameObject()
    {
        position = new Vector2(0, 0);
        dimension = new Vector2(0, 0);
        direction = new Vector2(0, 0);
        speed = 0.0;
        angle = 0.0;
        rotationSpeed = 0.0;
    }

    public abstract void update();
    public abstract void render(SpriteBatch batch);
}
