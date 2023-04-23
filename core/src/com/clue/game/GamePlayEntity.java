package com.clue.game;


interface inGameCard{

}

enum Person implements inGameCard{
    MrPlum, MrsIndigo, MrGreen, MsScarlet, MsPink,MrMustard
}
enum Place implements inGameCard{
    BedRoom,LivingRoom, DiningRoom, Kitchen, BathRoom,Garden,BilliardRoom, Cabinet
}
enum Weapon implements inGameCard{
    Knife,Candelabrum,Revoult,Rope,Poison,Wrench
}

public class GamePlayEntity {

    InGamePlayer player;

    public GamePlayEntity(InGamePlayer player) {
        this.player = player;
    }
}
