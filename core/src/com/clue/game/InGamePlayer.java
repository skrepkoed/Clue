package com.clue.game;

import java.util.ArrayList;

public class InGamePlayer implements CardHolder {


    private boolean isAi;

    public ArrayList<inGameCard> getHand() {
        return hand;
    }

    public ArrayList<inGameCard> hand=new ArrayList<inGameCard>();

    public Person character;

    InGamePlayer(Person character,boolean isAi){
        this.character=character;
        this.isAi=isAi;
    }

    public void addCardToHand(inGameCard card){
        hand.add(card);
    }

    public Accusation accuse(Person person,Place place,Weapon weapon){
        return new Accusation(person,place,weapon);
    }

    public boolean isAi() {
        return isAi;
    }

}
