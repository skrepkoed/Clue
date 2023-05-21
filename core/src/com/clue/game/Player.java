package com.clue.game;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.clue.game.gamelogic.Field;
import com.clue.game.gamelogic.GameBroker;
import com.clue.game.gamelogic.InGamePlayer;
import com.clue.game.gamelogic.Person;
import com.clue.game.gamelogic.Tile;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Player extends  Entity{
    public Vector2 getDestination() {
        return destination;
    }
    int moveCounter;

    float factor;
    Vector2 velocity;

    Tile currentTile;
    Tile destinationTile;



    List<DefaultEdge> pathList;
    ArrayDeque<Tile> path=new ArrayDeque<>();

    public void setDestination(Vector2 destination) {
        this.destination = destination;
        System.out.println(destination.x);
        System.out.println(destination.y);
        setCurrentPositionVector(currentTile.composeVector());
        pathVector=new Vector2(destination.x-currentPositionVector.x,destination.y-currentPositionVector.y);

        int score=(Math.abs((int)pathVector.x))/50+(Math.abs((int)pathVector.y))/50;
        System.out.println(score);
        moveCounter=0;
        if (pathVector.len()!=0) {
            scaleFactor(pathVector);
            velocity = new Vector2(factor*pathVector.x,factor*pathVector.y);
        }
        moveTo(destination.x,destination.y,3);
        currentTile=findTile(destination);
    }

    public void setDestinationTile(Vector2 destination,boolean isScored){
        //setDestination(destination);
        System.out.println(destination.x);
        System.out.println(destination.y);
        destinationTile=findTile(destination);
        createPath();
        if (isScored){
        if (path.size()<= GameBroker.getGame().currentPlayer.getMoves()) {
            System.out.println("Path: " + path);
            System.out.println("Moves: "+GameBroker.getGame().currentPlayer.getMoves());
            passPath();
            GameBroker.getGame().currentPlayer.setMoves(GameBroker.getGame().currentPlayer.getMoves()-path.size());
            System.out.println("Current pos:" + currentTile);
            System.out.println("Destination pos:" + destinationTile);
        }else {
            if (GameBroker.getGame().currentPlayer.isAi()){
                for (int i =0;i<path.size()-GameBroker.getGame().currentPlayer.getMoves();i++){
                    path.removeLast();
                }
                destinationTile=path.getLast();
                passPath();
            }
            path=new ArrayDeque<>();
        }
        }else {
            passPath();
        }

    }

    Vector2 destination;

    public Vector2 getCurrentPositionVector() {
        return currentPositionVector;
    }

    public void setCurrentPositionVector(Vector2 currentPositionVector) {
        currentTile=findTile(currentPositionVector);
        this.currentPositionVector = currentPositionVector;
    }

    public Tile findTile(Vector2 currentPositionVector){
        for (Tile[] row:Field.fieldMap) {
            for (Tile tile:row) {

                if (tile.equals(new Tile((int)currentPositionVector.x,(int)currentPositionVector.y,true))&&tile.passable){
                    return tile;
                }
            }
        }
        return  currentTile;
    }

    Vector2 currentPositionVector;

    public Vector2 getPathVector() {
        return pathVector;
    }

    public void setPathVector(Vector2 pathVector) {
        this.pathVector = pathVector;
    }

    static ArrayList<Vector2>  initialPositions=new ArrayList<>(Arrays.asList(
            new Vector2(400,350),
            new Vector2(500,350),
            new Vector2(600,350),
            new Vector2(600,450),
            new Vector2(600,550),
            new Vector2(500,550),
            new Vector2(400,550),
            new Vector2(400,450)));
//List<Vector2>initialPositions=List.of(new Vector2(450,250));

    Vector2 pathVector;
    Random random =new Random();
    Person person;
    public  Player(Person person){
        super();
        setTexture(new Texture(Gdx.files.internal(person.getTexture())));
        Collections.shuffle(initialPositions);
        Vector2 position=initialPositions.remove(0);
        setPosition(position.x, position.y);
        //setCurrentPositionVector(position);
        setDestinationTile(position,false);
        setSize(50,50);
        //setDestination(new Vector2(initial_posX,initial_posY));
        velocity=new Vector2(0,0);
        this.person=person;

    }

    public void setPosition(float x, float y){
        super.setPosition(x,y);
        //setCurrentPositionVector(ClueGameUtils.adjust_coordinates(new Vector3(x,y,0)));

    }
    public void  act(float dt){
        super.act(dt);
        //moveToDestination();
        //passPath();
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

    public void createPath(){
        if (!(currentTile.equals(destinationTile))){
            DijkstraShortestPath<Tile, DefaultEdge> shortestPath=new DijkstraShortestPath<>(Field.fieldGraph,currentTile,destinationTile);
            pathList =shortestPath.getPathEdgeList();
            System.out.println(pathList);
            //path.add(Field.fieldGraph.getEdgeSource(pathList.get(0)));
            for (DefaultEdge edge: pathList) {
                path.add(Field.fieldGraph.getEdgeTarget(edge));
            }
            //path.add(destinationTile);
        }
    }

    public void passPath(){
        int duration=3;
        currentTile=destinationTile;
        SequenceAction sequence = new SequenceAction();
        /*if (!path.isEmpty()){
        if (currentTile.equals(path.getFirst())){
            while (!path.isEmpty()) {
                MoveToAction action = new MoveToAction();
                Tile tile = path.pollFirst();
                action.setPosition((float) tile.getX_cord(), (float) tile.getY_cord());
                action.setDuration(1);
                sequence.addAction(action);
            }


        }else {
            while (!path.isEmpty()) {
                MoveToAction action = new MoveToAction();
                Tile tile = path.pollLast();
                action.setPosition((float) tile.getX_cord(), (float) tile.getY_cord());
                action.setDuration(1);
                sequence.addAction(action);
            }
        }
        }*/
        for (Tile tile:path) {
            MoveToAction action = new MoveToAction();
            action.setPosition((float) tile.getX_cord(),(float) tile.getY_cord());
            action.setDuration(1);
            sequence.addAction(action);

        }
        sequence.addAction(moveTo((float) destinationTile.getX_cord(),(float) destinationTile.getY_cord(),1));
        sequence.addAction(run(new Runnable() {
            @Override
            public void run() {
                //currentTile=destinationTile;
                path=new ArrayDeque<>();
            }
        }));
        addAction(sequence);
    }

}
