package com.clue.game.gamelogic;

import java.util.ArrayList;
import java.util.Objects;

public class InGamePlayer implements CardHolder {


    private boolean isAi;

    public ArrayList<InGameCard> getHand() {
        return hand;
    }

    public ArrayList<InGameCard> hand=new ArrayList<InGameCard>();

    public Person character;

    public Accusation accusation;

    InGamePlayer(Person character,boolean isAi){
        this.character=character;
        this.isAi=isAi;
        accusation=new Accusation();
    }

    public void addCardToHand(InGameCard card){
        hand.add(card);
    }

    public void accuse(Person person, Place place, Weapon weapon){

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InGamePlayer player = (InGamePlayer) o;
        return character == player.character;
    }

    @Override
    public int hashCode() {
        return Objects.hash(character);
    }

    public boolean isAi() {
        return isAi;
    }

}
