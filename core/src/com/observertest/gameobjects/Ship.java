package com.observertest.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.observertest.InputHandler;
import com.observertest.World;

/**
 * Created by Carl on 16/02/2017.
 */
public class Ship extends GameObject implements InputHandler
{
    private double rotationSpeed;
    private int coolDownTime, timeToCool;

    public Ship(World world, Vector2 position, Vector2 dimension, double speed, double angle, double rotationSpeed, Color colour)
    {
        super(world);
        this.position = position;
        this.dimension = dimension;
        this.speed = speed;
        this.angle = angle;
        this.rotationSpeed = rotationSpeed;
        this.colour = colour;
        coolDownTime = 60 / 4;
        timeToCool = 0;

        Pixmap pixmap = new Pixmap((int)dimension.x, (int)dimension.y, Pixmap.Format.RGBA8888);
        pixmap.setColor(colour);
        pixmap.drawLine(0, 0, (int)dimension.x, (int)dimension.y / 2); // Outside left
        pixmap.drawLine(0, (int)dimension.y, (int)dimension.x, (int)dimension.y / 2); // Outside right
        pixmap.drawLine(0, 0, (int)dimension.x / 2, (int)dimension.y / 2); // Inside left
        pixmap.drawLine(0, (int)dimension.y, (int)dimension.x / 2, (int)dimension.y / 2); // Inside right

        image = new Texture(pixmap);
        image.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear); // Reduces jagged lines

        pixmap.dispose();
    }

    @Override
    public void update()
    {
        handleInput();

        direction.x = (float)Math.cos(Math.toRadians(angle));
        direction.y = (float)Math.sin(Math.toRadians(angle));

        if(timeToCool > 0) timeToCool--;
    }

    @Override
    public void handleInput()
    {
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
        {
            direction.scl((float)speed);
            position.add(direction);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            angle += rotationSpeed;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            angle -= rotationSpeed;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.M) && timeToCool == 0)
        {
            timeToCool = coolDownTime;
            fireBullet();
        }
    }

    private void fireBullet()
    {
        for(GameObject gameObject : world.getGameObjects())
        {
            if(gameObject instanceof Bullet && !gameObject.isActive)
            {
                Bullet bullet = (Bullet)gameObject;
                bullet.initialise(new Vector2(position.x, position.y), new Vector2(10, 1), speed * 2, angle, colour);
                return;
            }
        }

        Bullet bullet = new Bullet(world, new Vector2(position.x, position.y), new Vector2(10, 1), speed * 2, angle, colour);
        world.getGameObjectsToAdd().add(bullet);
    }
}
