package com.clue.game.gamelogic;

import java.util.ArrayList;

public class InGamePlayer implements CardHolder {


    private boolean isAi;

    public ArrayList<InGameCard> getHand() {
        return hand;
    }

    public ArrayList<InGameCard> hand=new ArrayList<InGameCard>();

    public Person character;

    InGamePlayer(Person character,boolean isAi){
        this.character=character;
        this.isAi=isAi;
    }

    public void addCardToHand(InGameCard card){
        hand.add(card);
    }

    public void accuse(Person person, Place place, Weapon weapon){

    }

    public boolean isAi() {
        return isAi;
    }

}
