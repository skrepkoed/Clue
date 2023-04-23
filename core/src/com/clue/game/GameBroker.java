package com.clue.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
public class GameBroker {

    private static GameBroker game;

    private static final Class[] CARDS_ENUM={Person.class,Place.class,Weapon.class};

    public static int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    private   static int numberOfPlayers;

    public static int getNumberOfHumans() {
        return numberOfHumans;
    }

    private static int numberOfHumans;


    public static ArrayList<Person> charactersDeck=new ArrayList<Person>();
    //public static ArrayList<Place> placesStack=new ArrayList<Place>();
    //public static ArrayList<Weapon> weaponsStack=new ArrayList<Weapon>();

    public static ArrayList<inGameCard> getCardsDeck() {
        return cardsDeck;
    }

    public static ArrayList<inGameCard> cardsDeck=new ArrayList<inGameCard>();
    public static LinkedList<InGamePlayer> players= new LinkedList<InGamePlayer>() {
    };
    public static LinkedHashMap<inGameCard,CardHolder> gameSolution=new LinkedHashMap<inGameCard,CardHolder>();

    private   GameBroker(){
        createCardsStack(Person.class,charactersDeck);
        createCardsStack(CARDS_ENUM);
        initialize();


    };

    public static GameBroker getGame(){
        if (game == null) {
            game = new GameBroker();
        }
        return game;
    }

    public void initialize(int numberOfPlayers,int numberOfHumans ){
        initializePlayers(numberOfPlayers, numberOfHumans);
        cardDistribution();

    }

    public void initialize(){
        this.numberOfPlayers=3;
        this.numberOfHumans=1;
        initialize(numberOfPlayers, numberOfHumans);
    }

    private void initializePlayers(int numberOfPlayers, int numberOfHumans){
        for(int i=0; i<numberOfHumans;i++){
            InGamePlayer player=new InGamePlayer(charactersDeck.get(i),false);
            players.add(player);
        }
        for (int i=numberOfHumans; i<numberOfPlayers;i++) {
            players.add(new InGamePlayer(charactersDeck.get(i),true));
        }
    }

    private void cardDistribution(){
        int cardsNumberInHand=cardsDeck.size()/getNumberOfPlayers();
        int outer_counter=0;
        int counter=0;
        for (InGamePlayer player:players) {
            outer_counter+=1;
            for (;counter<cardsNumberInHand*outer_counter;counter++){
                gameSolution.put(cardsDeck.get(counter),player);
            }
        }
    }

    private  <E extends Enum<E> & inGameCard>
    void createCardsStack(Class<E> enumClass, ArrayList<E>list) {
        for(E constant : enumClass.getEnumConstants()) {
            list.add(constant);
        }
        Collections.shuffle(list);
    }

    private  <E extends Enum<E> & inGameCard>
    void createCardsStack(Class<E>[] enumClasses) {
        for (Class<E> enumClass: enumClasses) {
            for(E constant : enumClass.getEnumConstants()) {
                getCardsDeck().add(constant);
            }
        }
        Collections.shuffle(getCardsDeck());
    }


}
