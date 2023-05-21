package com.clue.game;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.clue.game.gamelogic.GameBroker;
import com.clue.game.gamelogic.Person;
import com.clue.game.gamelogic.Place;
import com.clue.game.gamelogic.Tile;
import com.clue.game.gamelogic.Weapon;

import java.util.ArrayList;
import java.util.Map;

public class CardUI extends Card {
    static Map<Tile,Tile>personsLocations=Map.of(
            new Tile(400,300,true),new Tile(350,250,true),
            new Tile(550,300,true),new Tile(550,250,true),
            new Tile(700,300,true),new Tile(750,300,true),
            new Tile(650,450,true),new Tile(700,450,true),
            new Tile(300,450,true),new Tile(300,400,true),
            new Tile(700,600,true),new Tile(700,650,true),
            new Tile(500,650,true),new Tile(550,650,true),
            new Tile(300,650,true),new Tile(300,600,true));
    static Map<Tile,Tile>weaponsLocations=Map.of(
            new Tile(400,300,true),new Tile(400,250,true),
            new Tile(550,300,true),new Tile(600,250,true),
            new Tile(700,300,true),new Tile(750,350,true),
            new Tile(650,450,true),new Tile(700,500,true),
            new Tile(300,450,true),new Tile(300,350,true),
            new Tile(700,600,true),new Tile(750,650,true),
            new Tile(500,650,true),new Tile(600,650,true),
            new Tile(300,650,true),new Tile(250,600,true));
    static Map<Tile,Place>placesTile=Map.of(
            new Tile(400,300,true),Place.Kitchen,
            new Tile(550,300,true),Place.DiningRoom,
            new Tile(700,300,true),Place.Garden,
            new Tile(650,450,true),Place.BilliardRoom,
            new Tile(300,450,true),Place.LivingRoom,
            new Tile(700,600,true),Place.BedRoom,
            new Tile(500,650,true),Place.Cabinet,
            new Tile(300,650,true),Place.BathRoom);
    GameState gameState;

    public Card getCard() {
        return card;
    }
    private Card card;
    public CardUI(Card card,GameState gameState){
        super(card.getInGameCard());
        this.card=card;
        this.gameState=gameState;
        setTouchable(Touchable.enabled);
        setSize(100,100);
        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                uiInteraction();
            }
        });
    }

    public void uiInteraction(){
        if(((CardUI.placesTile.get(gameState.getCurrentPlayerPosition()))!=null)){
            GameBroker.getGame().currentPlayer.accusation.setPlace((CardUI.placesTile.get(gameState.getCurrentPlayerPosition())));
            getParent().getParent().getChild(1).setVisible(false);
            getParent().getParent().getChild(0).setVisible(true);
            if (card.inGameCard.getClass().getSimpleName().equals("Weapon")){
                Tile tile=CardUI.weaponsLocations.get(gameState.getCurrentPlayerPosition());
                GameBroker.getGame().currentPlayer.accusation.setWeapon((Weapon)card.getInGameCard());
                placeCard(tile);

            }else if (card.inGameCard.getClass().getSimpleName().equals("Person")){
                Tile tile=CardUI.personsLocations.get(gameState.getCurrentPlayerPosition());
                GameBroker.getGame().currentPlayer.accusation.setPerson((Person)card.getInGameCard());

                for (Player p:gameState.characters) {
                    if ((p.person==(Person)card.inGameCard)&&(Person)card.inGameCard!=gameState.currentPlayer.person){
                        p.setDestinationTile(tile.composeVector(),false);
                    }
                }
            }
        }
    }

    public boolean aIUiInteraction(){
        if(((CardUI.placesTile.get(gameState.getCurrentPlayerPosition()))!=null)){
            //GameBroker.getGame().currentPlayer.accusation.setPlace((CardUI.placesTile.get(gameState.getCurrentPlayerPosition())));
            getParent().getParent().getChild(1).setVisible(false);
            getParent().getParent().getChild(0).setVisible(true);
            if (card.inGameCard.getClass().getSimpleName().equals("Weapon")){
                Tile tile=CardUI.weaponsLocations.get(gameState.getCurrentPlayerPosition());
                //GameBroker.getGame().currentPlayer.accusation.setWeapon((Weapon)card.getInGameCard());
                placeCard(tile);

            }else if (card.inGameCard.getClass().getSimpleName().equals("Person")){
                Tile tile=CardUI.personsLocations.get(gameState.getCurrentPlayerPosition());
                //GameBroker.getGame().currentPlayer.accusation.setPerson((Person)card.getInGameCard());

                for (Player p:gameState.characters) {
                    if ((p.person==(Person)card.inGameCard)&&(Person)card.inGameCard!=gameState.currentPlayer.person){
                        p.setDestinationTile(tile.composeVector(),false);
                        //p.moveToDestination();
                    }
                }
            }

            return true;
        }else {
            return false;
        }
    }
    private void placeCard(Tile tile){
        if (tile.getCurrentCard()!=null){
            tile.getCurrentCard().setVisible(false);
        }
        tile.setCurrentCard(getCard());
        getCard().setX(tile.getX_cord());
        getCard().setY(tile.getY_cord());
        getCard().setVisible(true);
        getCard().addAction(sequence(fadeOut(0), fadeIn(0.25f)));
    }
}
