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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class CardUI extends Card{

    public Card getCard() {
        return card;
    }

    private Card card;
    public CardUI(Card card){
        super();
        this.card=card;
        setTouchable(Touchable.enabled);
        setBounds(0,0, getTextureRegion().getRegionWidth() ,getTextureRegion().getRegionHeight());
        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Clicked");
                getCard().setX(300);
                getCard().setY(300);
                getCard().setVisible(true);
                getCard().addAction(sequence(fadeOut(0),fadeIn(0.25f)));
            }
        });
    }
}
