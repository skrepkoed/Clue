package com.clue.game.gamelogic;

public enum Place implements  InGameCard {
  BedRoom,LivingRoom, DiningRoom, Kitchen, BathRoom,Garden,BilliardRoom, Cabinet;

  @Override
  public String getTexture() {
    return texture;
  }

  String texture;
}
