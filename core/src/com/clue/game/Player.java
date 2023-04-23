package com.clue.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Player extends  Entity{
    public Vector2 getDestination() {
        return destination;
    }
    int moveCounter;

    float factor;
    Vector2 velocity;

    public void setDestination(Vector2 destination) {
        this.destination = destination;
        System.out.println(destination.x);
        System.out.println(destination.y);
        pathVector=new Vector2(destination.x-currentPositionVector.x,destination.y-currentPositionVector.y);
        int score=(Math.abs((int)pathVector.x))/50+(Math.abs((int)pathVector.y))/50;
        System.out.println(score);
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
        this.currentPositionVector = currentPositionVector;
    }

    Vector2 currentPositionVector;

    public Vector2 getPathVector() {
        return pathVector;
    }

    public void setPathVector(Vector2 pathVector) {
        this.pathVector = pathVector;
    }

    Vector2 pathVector;
    public  Player(float initial_posX, float initial_posY){
        super();
        setPosition(initial_posX, initial_posY);
        setCurrentPositionVector(new Vector2(initial_posX,initial_posY));
        setDestination(new Vector2(initial_posX,initial_posY));
        velocity=new Vector2(0,0);
    }

    public void setPosition(float x, float y){
        super.setPosition(x,y);
        setCurrentPositionVector(ClueGameUtils.adjust_coordinates(new Vector3(x,y,0)));

    }
    public void  act(float dt){
        super.act(dt);
        moveToDestination();
        //
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

}
