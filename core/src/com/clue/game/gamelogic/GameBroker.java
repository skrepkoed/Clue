package com.clue.game.gamelogic;

import com.clue.game.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class GameBroker {

    private static GameBroker game;

    private static final Class[] CARDS_ENUM={Person.class, Place.class, Weapon.class};

    public static int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    private   static int numberOfPlayers;

    public static int getNumberOfHumans() {
        return numberOfHumans;
    }

    private static int numberOfHumans;

    public static  Solution solution=new Solution();

    public static Solution getSolution() {
        return solution;
    }
    public SharedCards sharedCards=new SharedCards();

    public static ArrayList<Person> charactersDeck=new ArrayList<Person>();
    //public static ArrayList<Place> placesStack=new ArrayList<Place>();
    //public static ArrayList<Weapon> weaponsStack=new ArrayList<Weapon>();

    public static ArrayList<InGameCard> getCardsDeck() {
        return cardsDeck;
    }

    public static ArrayList<InGameCard> cardsDeck=new ArrayList<InGameCard>();
    public static ArrayList<InGamePlayer> players= new ArrayList<>();

    public static ArrayList<Weapon>weapons=new ArrayList<Weapon>();
    public static ArrayList<Person>persons=new ArrayList<Person>();
    public static  ArrayList<Place>places=new ArrayList<>();
    Random random=new Random();
    public static LinkedHashMap<InGameCard, InGamePlayer> gameSolution=new LinkedHashMap<InGameCard,InGamePlayer>();

    public static Accusation getAccusation() {
        return accusation;
    }

    public static void setAccusation(Accusation accusation) {
        GameBroker.accusation = accusation;
    }

    public static Accusation accusation;

    public static final Map<Place,Tile> locationsTile= Map.of(
            Place.Kitchen,new Tile(400,300,true),
            Place.DiningRoom,new Tile(550,300,true),
            Place.Garden,new Tile(700,300,true),
            Place.BilliardRoom,new Tile(650,450,true),
            Place.LivingRoom,new Tile(300,450,true),
            Place.BedRoom,new Tile(700,600,true),
            Place.Cabinet,new Tile(500,650,true),
            Place.BathRoom,new Tile(300,650,true));




    Iterator<InGamePlayer> nextPlayer;

    public InGamePlayer currentPlayer;

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


    public void aITurn(){
        if (!currentPlayer.accusation.isComplete()) {
            ArrayList<InGameCard> possibleCards = new ArrayList<>();
            for (InGameCard card : cardsDeck) {
                if (!currentPlayer.getHand().contains(card)) {
                    possibleCards.add(card);
                }
            }
            Collections.shuffle(possibleCards);
            for (InGameCard card : possibleCards) {
                if (card.getClass().getSimpleName().equals("Person")) {
                    currentPlayer.accusation.setPerson((Person) card);
                }
                if (card.getClass().getSimpleName().equals("Place")) {
                    currentPlayer.accusation.setPlace((Place) card);
                }
                if (card.getClass().getSimpleName().equals("Weapon")) {
                    currentPlayer.accusation.setWeapon((Weapon) card);
                }
            }
            if (!currentPlayer.accusation.isComplete()) {
                if (currentPlayer.accusation.getPerson() == null) {
                    currentPlayer.accusation.setPerson(persons.get(random.nextInt(6)));
                }
                if (currentPlayer.accusation.getPlace() == null) {
                    currentPlayer.accusation.setPlace(places.get(random.nextInt(8)));
                }
                if (currentPlayer.accusation.getWeapon() == null) {
                    currentPlayer.accusation.setWeapon(weapons.get(random.nextInt(6)));
                }
            }
        }

    }

    public void initialize(int numberOfPlayers,int numberOfHumans ){
        initializePlayers(numberOfPlayers, numberOfHumans);
        createCardsStack(Weapon.class, weapons);
        createCardsStack(Person.class,persons);
        createCardsStack(Place.class,places);
        cardDistribution();
        nextPlayer=players.iterator();
        currentPlayer=players.get(0);
    }

    public void initialize(){
        this.numberOfPlayers=3;
        this.numberOfHumans=1;
        initialize(numberOfPlayers, numberOfHumans);
    }

    public InGamePlayer getCurrentPlayer() {
        if (!nextPlayer.hasNext()){
            nextPlayer=players.iterator();
        }

        currentPlayer=nextPlayer.next();
        currentPlayer.setMoves(random.nextInt(12)+1);
        return currentPlayer;
    }

    public Statement defineStatement(){
        accusation=currentPlayer.accusation;
        InGamePlayer p1= gameSolution.get(accusation.getPerson());
        InGamePlayer p2=gameSolution.get(accusation.getPlace());
        InGamePlayer p3=gameSolution.get(accusation.getWeapon());
        int start=players.lastIndexOf(currentPlayer);
        for (int i=start+1;i<players.size();i++){
            if (players.get(i).equals(p1)){
                return new Statement(p1,accusation.getPerson());
            }
            if (players.get(i).equals(p2)){
                return new Statement(p2,accusation.getPlace());
            }
            if (players.get(i).equals(p3)){
                return new Statement(p3,accusation.getWeapon());
            }
        }
        for (int i=0;i<start;i++){
            if (players.get(i).equals(p1)){
                return new Statement(p1,accusation.getPerson());
            }
            if (players.get(i).equals(p2)){
                return new Statement(p2,accusation.getPlace());
            }
            if (players.get(i).equals(p3)){
                return new Statement(p3,accusation.getWeapon());
            }
        }
        if (sharedCards.getHand().contains(accusation.getPerson())||
                sharedCards.getHand().contains(accusation.getPlace())||
                sharedCards.getHand().contains(accusation.getWeapon())){

        }
        return new Statement();
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
        for (InGameCard card:cardsDeck) {
            solution.addToSolution(card);
        }
        for (InGameCard card:solution.getHand()) {
            cardsDeck.remove(card);
        }
        int cardsNumberInHand=cardsDeck.size()/getNumberOfPlayers();
        int sharedCardsNumber=cardsDeck.size()%getNumberOfPlayers();
        for (int i= 0;i<sharedCardsNumber;i++){
            sharedCards.addCards(cardsDeck.remove(i));
        }
        int outer_counter=0;
        int counter=0;
        for (InGamePlayer player:players) {
            outer_counter+=1;
            for (;counter<cardsNumberInHand*outer_counter;counter++){
                gameSolution.put(cardsDeck.get(counter),player);
                player.getHand().add(cardsDeck.get(counter));
            }
        }
    }

    private  <E extends Enum<E> & InGameCard>
    void createCardsStack(Class<E> enumClass, ArrayList<E>list) {
        for(E constant : enumClass.getEnumConstants()) {
            list.add(constant);
        }
        Collections.shuffle(list);
    }

    private  <E extends Enum<E> & InGameCard>
    void createCardsStack(Class<E>[] enumClasses) {
        for (Class<E> enumClass: enumClasses) {
            for(E constant : enumClass.getEnumConstants()) {
                getCardsDeck().add(constant);
            }
        }
        Collections.shuffle(getCardsDeck());
    }


}
