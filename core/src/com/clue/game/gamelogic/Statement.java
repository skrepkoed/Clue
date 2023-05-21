package com.clue.game.gamelogic;

import java.util.Objects;

public class Statement {
  public InGamePlayer inGamePlayer;
  public InGameCard card;

  public boolean isTruthfulness() {
    return truthfulness;
  }

  public void setTruthfulness(boolean truthfulness) {
    this.truthfulness = truthfulness;
  }

  public boolean truthfulness=false;

  public boolean isEmpty() {
    return isEmpty;
  }

  private boolean isEmpty;

  public Statement(InGamePlayer inGamePlayer, InGameCard card) {
    this.inGamePlayer = inGamePlayer;
    this.card = card;
    isEmpty=false;
    truthfulness=false;
  }

  public Statement(){
    isEmpty=true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Statement statement = (Statement) o;
    return inGamePlayer.equals(statement.inGamePlayer) && card.equals(statement.card);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inGamePlayer, card);
  }
}
