package com.clue.game.gamelogic;

public enum Weapon implements InGameCard{
  Knife,Candelabrum,Revoult,Rope,Poison,Wrench;

  @Override
  public String getTexture() {
    return texture;
  }

  String texture;
}
