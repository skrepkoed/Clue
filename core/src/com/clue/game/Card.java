package com.clue.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Card extends Entity{
    public Card(){
        super();
        Texture texture= new Texture(Gdx.files.internal("assets/poison_card.png"));
        setTexture(texture);
        setSize(50,50);
        setDebug(true);

    }
}
