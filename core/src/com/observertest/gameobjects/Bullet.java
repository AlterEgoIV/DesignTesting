package com.observertest.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.observertest.World;

/**
 * Created by Carl on 16/02/2017.
 */
public class Bullet extends GameObject
{
    public Bullet(World world)
    {
        super(world);
    }

    public Bullet(World world, Vector2 position, Vector2 dimension, double speed, double angle, Color colour)
    {
        super(world);
        initialise(position, dimension, speed, angle, colour);
    }

    public void initialise(Vector2 position, Vector2 dimension, double speed, double angle, Color colour)
    {
        this.position = position;
        this.dimension = dimension;
        this.speed = speed;
        this.angle = angle;
        this.colour = colour;

        direction.x = (float)Math.cos(Math.toRadians(angle));
        direction.y = (float)Math.sin(Math.toRadians(angle));
        direction.scl((float)speed);

        Pixmap pixmap = new Pixmap((int)dimension.x, (int)dimension.y, Pixmap.Format.RGBA8888);
        pixmap.setColor(colour);
        pixmap.drawLine(0, 0, (int)dimension.x, 0);

        image = new Texture(pixmap);
        image.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear); // Reduces jagged lines

        pixmap.dispose();
    }

    @Override
    public void update()
    {
        position.add(direction);

        if(position.x > Gdx.graphics.getWidth() || position.x < 0 ||
           position.y > Gdx.graphics.getHeight() || position.y < 0)
        {
            world.remove(this);
        }
    }
}
