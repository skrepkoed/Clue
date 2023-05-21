package com.clue.game.gamelogic;

import com.clue.game.gamelogic.CardHolder;
import com.clue.game.gamelogic.Person;
import com.clue.game.gamelogic.Place;
import com.clue.game.gamelogic.Weapon;
import com.clue.game.gamelogic.InGameCard;

import java.util.ArrayList;

public class Solution implements CardHolder, CardHolder.AtomicAccusation {

    @Override
    public ArrayList<InGameCard> getHand() {
        return hand;
    }

  public boolean isComplete() {
     if ((!(person==null))&&(!(place==null))&&(!(weapon==null))){
       this.isComplete=true;
       hand.add(person);
       hand.add(place);
       hand.add(weapon);
       return true;
     }else {
       return false;
     }
  }

  private boolean isComplete=false;
  private Person  person;
  private Place place;
  private Weapon weapon;
    private ArrayList<InGameCard> hand=new ArrayList<InGameCard>();

    public Solution() {

    }

    public boolean revelation(Accusation accusation){
        if (getPerson()==accusation.getPerson()
                &&getPlace()==accusation.getPlace()
                &&getWeapon()==accusation.getWeapon()){
          return true;
        }
        return false;
    }

  public String toString(){
    return "Mansion owner was murdered by \n"+ getPerson()+ " in"+ getPlace()+" with"+ getWeapon();
  }

  @Override
  public Person getPerson() {
    return person;
  }

  @Override
  public void setPerson(Person person) {
    this.person = person;
  }

  @Override
  public Place getPlace() {
    return place;
  }

  @Override
  public void setPlace(Place place) {
    this.place = place;
  }

  @Override
  public Weapon getWeapon() {
    return weapon;
  }

  @Override
  public void setWeapon(Weapon weapon) {
    this.weapon = weapon;
  }
}
