package com.clue.game;

import com.badlogic.gdx.math.Vector2;
import com.clue.game.gamelogic.Tile;

import java.util.ArrayList;
import java.util.Iterator;

public class GameState {
    public Iterator<Player> nextPlayer;
    public ArrayList<Player>players=new ArrayList<>();

    public ArrayList<Player> getCharacters() {
        return characters;
    }

    public ArrayList<Player> characters=new ArrayList<>();
    public Player currentPlayer;

    public Player getCurrentPlayer() {
        if (!nextPlayer.hasNext()){
            nextPlayer=players.iterator();
        }
        currentPlayer=nextPlayer.next();
        return currentPlayer;
    }

    public Tile getCurrentPlayerPosition(){
        return currentPlayer.currentTile;
    }


    public GameState(){

    }
    public void setIterator(){
        nextPlayer=players.iterator();
        currentPlayer=players.get(0);
    }
}
