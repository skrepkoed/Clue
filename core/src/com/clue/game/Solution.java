package com.clue.game;

import java.util.ArrayList;

public class Solution implements CardHolder {

    @Override
    public ArrayList<inGameCard> getHand() {
        return hand;
    }

    private ArrayList<inGameCard> hand=new ArrayList<inGameCard>();
    public Solution(Person person,Place place, Weapon weapon) {
       hand.add(person);
       hand.add(place);
       hand.add(weapon);
    }
}
