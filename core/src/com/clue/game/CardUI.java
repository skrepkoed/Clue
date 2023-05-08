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
import com.clue.game.gamelogic.Tile;

import java.util.Map;

public class CardUI extends Card {
    /*static Map<Tile,Tile>locations=Map.of(
            new Tile(400,300,true),new Tile(350,250,true),
            new Tile(550,300,true),new Tile(550,250,true),
            new Tile(750,300,true),new Tile(750,300,true),
            new Tile(650,450,true),new Tile(700,450,true),
            new Tile(400,450,true),new Tile(350,450,true),
            new Tile(350,450,true),new Tile(300,400,true),
            new Tile(700,600,true),new Tile(700,650,true),
            new Tile(500,650,true),new Tile(550,650,true),
            new Tile(300,650,true),new Tile(300,600,true));
            */
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

    public Card getCard() {
        return card;
    }
    private Card card;
    public CardUI(Card card,GameState gameState){
        super(card.getInGameCard());
        this.card=card;
        setTouchable(Touchable.enabled);
        setSize(100,100);
        //setBounds(0,0, getTextureRegion().getRegionWidth() ,getTextureRegion().getRegionHeight());
        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getParent().getParent().getChild(1).setVisible(false);
                getParent().getParent().getChild(0).setVisible(true);
                if (card.inGameCard.getClass().getSimpleName().equals("Weapon")){
                    if ((CardUI.weaponsLocations.get(gameState.getCurrentPlayerPosition()))!=null) {
                        Tile tile=CardUI.weaponsLocations.get(gameState.getCurrentPlayerPosition());
                        placeCard(tile);
                    }
                }else if (card.inGameCard.getClass().getSimpleName().equals("Person")){
                    if ((CardUI.personsLocations.get(gameState.getCurrentPlayerPosition()))!=null) {
                        Tile tile=CardUI.personsLocations.get(gameState.getCurrentPlayerPosition());
                        placeCard(tile);
                    }
                }
            }
        });
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
