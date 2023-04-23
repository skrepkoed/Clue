package com.clue.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Entity extends Actor {
    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    private TextureRegion textureRegion;
    private Rectangle rectangle;

    Entity(){
        super();
        textureRegion = new TextureRegion();
        rectangle = new Rectangle();
    }

    public void setTexture(Texture t)
    {
        textureRegion.setRegion(t);
        setSize( t.getWidth(), t.getHeight() );
        rectangle.setSize( t.getWidth(), t.getHeight() );
    }
    public Rectangle getRectangle()
    {
        rectangle.setPosition( getX(), getY() );
        return rectangle;
    }

    public void draw(Batch batch, float parentAlpha)
    {
        super.draw( batch, parentAlpha );
        Color c = getColor(); // used to apply tint color effect
        batch.setColor(c.r, c.g, c.b, c.a);
        if ( isVisible() )
            batch.draw( textureRegion,
                    getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation() );
    }
}
