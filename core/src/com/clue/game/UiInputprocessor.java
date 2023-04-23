package com.clue.game;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;

public class UiInputprocessor extends InputAdapter {
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }
}
