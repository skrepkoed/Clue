package com.clue.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.clue.game.gamelogic.InGameCard;

public class Card extends Entity{
    public InGameCard getInGameCard() {
        return inGameCard;
    }
    final  static Vector2 deckPosition=new Vector2(150,500);

    InGameCard inGameCard;
    public Card(InGameCard inGameCard){
        super(deckPosition);
        this.inGameCard=inGameCard;
        Texture texture= new Texture(Gdx.files.internal(inGameCard.getTexture()));
        setTexture(texture);
        setSize(50,50);
    }


}
