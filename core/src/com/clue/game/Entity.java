package com.clue.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.clue.game.gamelogic.InGameCard;
import com.clue.game.gamelogic.Tile;

public class Entity extends Actor {
    public Vector2 getDestination() {
        return destination;
    }
    int moveCounter;

    float factor;
    Vector2 pathVector;
    Vector2 velocity;

    Tile currentTile;
    Tile destinationTile;

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

    Entity(Vector2 position){
        super();
        textureRegion = new TextureRegion();
        rectangle = new Rectangle();
        setCurrentPositionVector(position);
        setDestination(position);

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

    public void setDestination(Vector2 destination) {
        this.destination = destination;
        System.out.println(destination.x);
        System.out.println(destination.y);
        pathVector=new Vector2(destination.x-currentPositionVector.x,destination.y-currentPositionVector.y);

        int score=(Math.abs((int)pathVector.x))/50+(Math.abs((int)pathVector.y))/50;
        moveCounter=0;
        if (pathVector.len()!=0) {
            scaleFactor(pathVector);
            velocity = new Vector2(factor*pathVector.x,factor*pathVector.y);
        }
    }

    Vector2 destination;

    public Vector2 getCurrentPositionVector() {
        return currentPositionVector;
    }

    public void setCurrentPositionVector(Vector2 currentPositionVector) {
        //currentTile=findTile(currentPositionVector);
        this.currentPositionVector = currentPositionVector;
    }

    Vector2 currentPositionVector;

    public Vector2 getPathVector() {
        return pathVector;
    }

    public void setPathVector(Vector2 pathVector) {
        this.pathVector = pathVector;
    }

    public void setPosition(float x, float y){
        super.setPosition(x,y);
        setCurrentPositionVector(ClueGameUtils.adjust_coordinates(new Vector3(x,y,0)));

    }

    private float scaleFactor(Vector2 vector){
        factor=(float) 1/vector.len();
        return factor;
    }

    public void moveToDestination(){
        if (moveCounter<=(int)pathVector.len()){
            setPosition(getX()+velocity.x,getY()+velocity.y);
            moveCounter+= Math.round(velocity.len());
        }else {
            setPosition(destination.x,destination.y);
        }
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
