package com.clue.game.gamelogic;

public enum  Person implements InGameCard{
  MrPlum("assets/mr_plum_icon.png"),
  MrsIndigo("assets/mrs_indigo_icon.png"),
  MrGreen("assets/mr_green_icon.png"),
  LadyScarlet("assets/lady_scarlet_icon.png"),
  MsPink("assets/mrs_pink_icon.png"),
  MrMustard("assets/mr_mustard_icon.png");
  String texture;
  Person(String texture){
    this.texture=texture;
  }
  public  String getTexture(){
    return texture;
  }
}
