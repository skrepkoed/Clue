package com.clue.game.gamelogic;

import java.util.ArrayList;

public class SharedCards implements CardHolder{
  public Person person;

  public boolean isComplete(){
    return true;
  };

  ArrayList<InGameCard> hand=new ArrayList<>();
  public ArrayList<InGameCard> getHand(){
    return hand;
  };
  public void addCards(InGameCard inGameCard){
    getHand().add(inGameCard);
  }

}
