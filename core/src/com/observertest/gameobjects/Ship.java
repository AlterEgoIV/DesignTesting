package com.observertest.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.observertest.enums.Event;

/**
 * Created by Carl on 16/02/2017.
 */
public class Ship extends Subject
{
    private int coolDownTime, lastFired;
    private boolean canFire;

    public Ship(Vector2 position, Vector2 dimension, double speed, double angle, double rotationSpeed, Color colour)
    {
        this.position = position;
        this.dimension = dimension;
        this.speed = speed;
        this.angle = angle;
        this.rotationSpeed = rotationSpeed;
        this.colour = colour;
        coolDownTime = 60 / 4;
        lastFired = 0;
        canFire = true;

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
        direction.x = (float)Math.cos(Math.toRadians(angle));
        direction.y = (float)Math.sin(Math.toRadians(angle));
        direction.scl((float)speed);

        if(Gdx.input.isKeyPressed(Input.Keys.UP))
        {
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

        if(Gdx.input.isKeyPressed(Input.Keys.M) && canFire)
        {
            System.out.println("Ship fired");

            canFire = false;
            sendEvent(Event.SHIP_FIRED_BULLET, this);
        }

        if(!canFire) lastFired++;
        if(lastFired % coolDownTime == 0) canFire = true;
    }
}
