package com.clue.game.gamelogic;

import com.clue.game.Card;
import com.clue.game.Player;

public class Tile {

    public int x_cord;
    public  int y_cord;
    public boolean passable;

    public Card getCurrentCard() {
        return currentCard;
    }

    public void setCurrentCard(Card currentCard) {
        this.currentCard = currentCard;
    }

    public Card currentCard;

    public int getX_cord() {
        return x_cord;
    }

    public void setX_cord(int x_cord) {
        this.x_cord = x_cord;
    }

    public int getY_cord() {
        return y_cord;
    }

    public void setY_cord(int y_cord) {
        this.y_cord = y_cord;
    }

    public boolean isPassable() {
        return passable;
    }

    public void setPassable(boolean passable) {
        this.passable = passable;
    }
    @Override
    public boolean equals(Object obj){
        Tile tile=(Tile) obj;
        if (obj==null){return false;
        }else if (this.getX_cord()==tile.getX_cord()&&this.getY_cord()==tile.getY_cord()){
            return true;
        }
        return false;
    }

    public int hashCode(){
        return 1;
    }

    public Tile(int x_cord, int y_cord, boolean passable) {
        this.x_cord = x_cord;
        this.y_cord = y_cord;
        this.passable = passable;
    }

    public String toString(){
        return "("+getX_cord()+";"+getY_cord()+")";
    }

}
