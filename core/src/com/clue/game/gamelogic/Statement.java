package com.clue.game.gamelogic;

public class Statement {
  public InGamePlayer inGamePlayer;
  public InGameCard card;

  public boolean isEmpty() {
    return isEmpty;
  }

  private boolean isEmpty;

  public Statement(InGamePlayer inGamePlayer, InGameCard card) {
    this.inGamePlayer = inGamePlayer;
    this.card = card;
    isEmpty=false;
  }

  public Statement(){
    isEmpty=true;
  }
}
