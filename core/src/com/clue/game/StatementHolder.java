package com.clue.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.clue.game.gamelogic.Statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class StatementHolder extends Actor {
  public TextureRegion getTextureRegion() {
    return textureRegion;
  }

  private TextureRegion textureRegion;
  Statement statement;

  public void setTextureArrayList() {
            textureArrayList.add(new Texture(Gdx.files.internal("./assets/statement_holder.png")));
            textureArrayList.add(new Texture(Gdx.files.internal("./assets/check_mark.png")));
            textureArrayList.add(new Texture(Gdx.files.internal("./assets/cross.png")));
            textureArrayList.add(new Texture(Gdx.files.internal("./assets/question_mark.png")));
  }

  private final ArrayList<Texture> textureArrayList=new ArrayList<Texture>();
  private Rectangle rectangle;

  private Iterator<Texture> state;

  StatementHolder(Statement statement){
    super();
    setTextureArrayList();
    state=textureArrayList.iterator();
    state.next();
    this.statement=statement;
    textureRegion = new TextureRegion();
    rectangle = new Rectangle();
    setTexture(new Texture(Gdx.files.internal("./assets/statement_holder.png")));

    addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        if (!statement.isTruthfulness()){
          nextState();
        }
      }
    });
  }

  public void setTexture(Texture t)
  {
    textureRegion.setRegion(t);
    setSize( 100,40 );
    rectangle.setSize( 100,40 );
  }

  public void nextState(){
    if (state.hasNext()){
        setState(state.next());
    }else {
      state=textureArrayList.iterator();
      setState(state.next());
    }
  }

  public void setState(Texture texture){
    setTexture(texture);
  }

  public StatementHolder setTrue(){
    setTexture(new Texture(Gdx.files.internal("./assets/check_mark.png")));
    statement.setTruthfulness(true);
    return this;
  }
  public StatementHolder setFalse(){
    setTexture(new Texture(Gdx.files.internal("./assets/cross.png")));
    statement.setTruthfulness(false);
    return this;
  }

  public void draw(Batch batch, float parentAlpha)
  {
    super.draw( batch, parentAlpha );
    Color c = getColor(); // used to apply tint color effect
    batch.setColor(c.r, c.g, c.b, c.a);
    if ( isVisible() )
      batch.draw( textureRegion,
              getX(), getY(), getOriginX(), getOriginY(),
              getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation() );
  }
}
