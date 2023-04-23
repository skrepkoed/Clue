package com.clue.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;


public class ClueGameUtils {
    public static Vector2 adjust_coordinates(Vector3 worldCoord){
        if (worldCoord.x/50==0){
            return new Vector2((int) Math.ceil (worldCoord.x/50) *50,(int) (worldCoord.y/50)*50);
        } else if (worldCoord.y/50==0) {
            return new Vector2((int)  (worldCoord.x/50) *50,(int) Math.ceil (worldCoord.y/50)*50);
        } else
            return new Vector2((int)  (worldCoord.x/50) *50,(int) (worldCoord.y/50)*50);
    }
}
