package com.clue.game.gamelogic;

import com.clue.game.gamelogic.Person;
import com.clue.game.gamelogic.Place;
import com.clue.game.gamelogic.Weapon;

public class Accusation implements CardHolder.AtomicAccusation{
    private Person person;
    private Place place;
    private Weapon weapon;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon( Weapon weapon) {
        this.weapon = weapon;
    }


    private boolean isComplete=false;
    public boolean isComplete() {
        if (person!=null&&place!=null&&weapon!=null){
            this.isComplete=true;
        }
        return this.isComplete;
    }



    public Accusation(Person person, Place place, Weapon weapon) {
        this.person = person;
        this.place = place;
        this.weapon = weapon;
    }

    public String toString(){
        return "Mansion owner was murdered by +\n"+ getPerson()+" in"+ getPlace()+" with"+ getWeapon();
    }

    public Accusation(){

    }
}
