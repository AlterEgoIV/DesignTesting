package com.observertest.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.observertest.World;

import java.awt.*;

/**
 * Created by Carl on 02/03/2017.
 */
public abstract class CollidableObject extends GameObject
{
    public Rectangle rectangle;

    protected CollidableObject(World world)
    {
        super(world);
        rectangle = new Rectangle();
    }

    @Override
    public void render(SpriteBatch batch)
    {
        batch.draw(image,
          position.x - dimension.x / 2, position.y - dimension.y / 2,
          dimension.x / 2, dimension.y / 2, // Origin is center point for rotation
          dimension.x, dimension.y,
          1f, 1f,
          (float)angle,
          0, 0, (int)dimension.x, (int)dimension.y,
          false, false);
    }

    public boolean collidesWith(CollidableObject collidableObject)
    {
        if(!collidableObject.equals(this))
        {
            return rectangle.intersects(collidableObject.rectangle);
        }

        return false;
    }

    public abstract void resolveCollision(CollidableObject collidableObject);
}
