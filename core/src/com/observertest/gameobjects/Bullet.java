package com.observertest.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.observertest.enums.Event;

/**
 * Created by Carl on 16/02/2017.
 */
public class Bullet extends Subject
{
    public Bullet(Vector2 position, Vector2 dimension, double speed, double angle, Color color)
    {
        initialise(position, dimension, speed, angle, color);
    }

    public void initialise(Vector2 position, Vector2 dimension, double speed, double angle, Color color)
    {
        this.position = position;
        this.dimension = dimension;
        this.speed = speed;
        this.angle = angle;
        this.color = color;

        direction.x = (float)Math.cos(Math.toRadians(angle));
        direction.y = (float)Math.sin(Math.toRadians(angle));
        direction.scl((float)speed);

        Pixmap pixmap = new Pixmap((int)dimension.x, (int)dimension.y, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.drawLine(0, 0, (int)dimension.x, 0);

        image = new Texture(pixmap);
        image.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear); // Reduces jagged lines

        pixmap.dispose();
    }

    @Override
    public void update()
    {
        position.add(direction);

        if(position.x > Gdx.graphics.getWidth() ||
                position.x < 0 ||
                position.y > Gdx.graphics.getHeight() ||
                position.y < 0)
        {
            sendEvent(this, Event.REMOVE_BULLET);
        }
    }
}
