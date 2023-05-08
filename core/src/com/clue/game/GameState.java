package com.clue.game;

import com.badlogic.gdx.math.Vector2;
import com.clue.game.gamelogic.Tile;

import java.util.ArrayList;
import java.util.Iterator;

public class GameState {
    public Iterator<Player> nextPlayer;
    public ArrayList<Player>players;
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


    public GameState(ArrayList<Player>players){
        this.players=players;
        nextPlayer=players.iterator();
        currentPlayer=players.get(0);
    }
}
