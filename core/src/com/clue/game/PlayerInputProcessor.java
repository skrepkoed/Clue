package com.clue.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayerInputProcessor implements InputProcessor {

    //private Player player;
    public Array<KeyState> keyStates = new Array<KeyState>();
    public Array<TouchState> touchStates = new Array<TouchState>();

    Vector3 currentMousePosition;
    Vector3 previousMousePosition;



    public PlayerInputProcessor(){
        currentMousePosition=new Vector3(0,0,0);
        previousMousePosition=new Vector3(currentMousePosition);
        for (int i = 0; i < 256; i++) {
            keyStates.add(new KeyState(i));
        }
        touchStates.add(new TouchState(0, 0, 0, 0));
    }

    public class InputState {
        public boolean pressed = false;
        public boolean down = false;
        public boolean released = false;
    }

    public class KeyState extends InputState{
        //the keyboard key of this object represented as an integer.
        public int key;

        public KeyState(int key){
            this.key = key;
        }
    }

    public class TouchState extends InputState{
        //keep track of which finger this object belongs to
        public int pointer;
        //coordinates of this finger/mouse
        public Vector2 coordinates;
        //mouse button
        public int button;
        //track the displacement of this finger/mouse
        private Vector2 lastPosition;
        public Vector2 displacement;

        public TouchState(int coord_x, int coord_y, int pointer, int button){
            this.pointer = pointer;
            coordinates = new Vector2(coord_x, coord_y);
            this.button = button;

            lastPosition = new Vector2(0, 0);
            displacement = new Vector2(lastPosition.x, lastPosition.y);
        }
    }

    public boolean isKeyPressed(int key){
        return keyStates.get(key).pressed;
    }
    public boolean isKeyDown(int key){
        return keyStates.get(key).down;
    }
    public boolean isKeyReleased(int key){
        return keyStates.get(key).released;
    }

    @Override
    public boolean keyDown(int keycode) {
        keyStates.get(keycode).pressed = true;
        keyStates.get(keycode).down = true;
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        keyStates.get(keycode).down = false;
        keyStates.get(keycode).released = true;
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    public void update(){
        for (int i = 0; i < 256; i++) {
            KeyState k = keyStates.get(i);
            k.pressed = false;
            k.released = false;
        }

        for (int i = 0; i < touchStates.size; i++) {
            TouchState t = touchStates.get(i);

            t.pressed = false;
            t.released = false;

            t.displacement.x = 0;
            t.displacement.y = 0;
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        boolean pointerFound = false;

        //get altered coordinates
        int coord_x = coordinateX(screenX);
        int coord_y = coordinateY(screenY);

        //set the state of all touch state events
        for (int i = 0; i < touchStates.size; i++) {
            TouchState t = touchStates.get(i);
            if (t.pointer == pointer) {
                t.down = true;
                t.pressed = true;

                //store the coordinates of this touch event
                t.coordinates.x = coord_x;
                t.coordinates.y = coord_y;
                t.button = button;

                //recording last position for displacement values
                t.lastPosition.x = coord_x;
                t.lastPosition.y = coord_y;

                //this pointer exists, don't add a new one.
                pointerFound = true;
            }
        }

        //this pointer doesn't exist yet, add it to touchStates and initialize it.
        if (!pointerFound) {
            touchStates.add(new TouchState(coord_x, coord_y, pointer, button));
            TouchState t = touchStates.get(pointer);

            t.down = true;
            t.pressed = true;

            t.lastPosition.x = coord_x;
            t.lastPosition.y = coord_y;
        }
        return false;
    }

    private int coordinateX (int screenX) {
        return screenX;
    }
    private int coordinateY (int screenY) {
        return screenY;
    }

    public boolean isTouchPressed(int pointer){
        return touchStates.get(pointer).pressed;
    }
    public boolean isTouchDown(int pointer){
        return touchStates.get(pointer).down;
    }
    public boolean isTouchReleased(int pointer){
        return touchStates.get(pointer).released;
    }

    public boolean isMouseMoved(Vector3 currentMousePosition){
        if (currentMousePosition.x==previousMousePosition.x&&currentMousePosition.y==previousMousePosition.y){
            return false;
        }else{
            return true;
        }
    }

    public Vector2 touchCoordinates(int pointer){
        return touchStates.get(pointer).coordinates;
    }
    public Vector2 touchDisplacement(int pointer){
        return touchStates.get(pointer).displacement;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        previousMousePosition=new Vector3(currentMousePosition);
        currentMousePosition=new Vector3(screenX,screenY,0);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public boolean scrolled(int amount) {
        return false;
    }
/*
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }*/
}
