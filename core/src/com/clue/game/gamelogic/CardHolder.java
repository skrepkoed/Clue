package com.clue.game.gamelogic;

import com.clue.game.gamelogic.InGameCard;

import java.util.ArrayList;

public interface CardHolder {
    ArrayList<InGameCard> hand=new ArrayList<>();
    public ArrayList<InGameCard> getHand();
    public interface AtomicAccusation {
    public Person getPerson();
    public Place getPlace();
    public Weapon getWeapon();
        public boolean isComplete();

        public void setPerson(Person card);

        public void setPlace(Place card);

        public void setWeapon(Weapon card);

        public default boolean addToSolution(InGameCard card) {
            try {
                if (getPerson() == null && card.getClass().getSimpleName().equals("Person")) {
                    setPerson((Person) card);
                    return true;
                }
                if (getPlace() == null && card.getClass().getSimpleName().equals("Place")) {
                    setPlace((Place) card);

                    return true;
                }
                if (getWeapon() == null && card.getClass().getSimpleName().equals("Weapon")) {
                    setWeapon((Weapon) card);
                    return true;
                }
            } finally {
                isComplete();
            }

            return false;
        }
    }
}
