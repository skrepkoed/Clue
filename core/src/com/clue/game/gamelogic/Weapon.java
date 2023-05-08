package com.clue.game.gamelogic;

public enum Weapon implements InGameCard{
  Knife("assets/knife_card.png"),
  Candelabrum("assets/chandeler_card.png"),
  Revoult("assets/revoult_card.png"),
  Rope("assets/rope_card.png"),
  Poison("assets/poison_card.png"),
  Wrench("assets/wrench_card.png");


   Weapon(String texture){
    this.texture=texture;
  }
  public String getTexture() {
    return texture;
  }

  String texture;
}
