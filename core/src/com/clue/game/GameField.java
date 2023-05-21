package com.clue.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameField extends Entity{
    private static Rectangle worldBounds;
    public GameField(){
        super();
        setWorldBounds(this.getWidth(),this.getHeight());
    }
    private void setWorldBounds(float width, float height)
    {
        worldBounds = new Rectangle( 150,50, width, height );
    }
    public void  act(float dt){
        super.act(dt);
        handleInput();
    }

    private void handleInput() {
        OrthographicCamera cam = (OrthographicCamera) this.getStage().getCamera();
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (cam.zoom<=1.3) {
                cam.zoom += 0.02;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            if (cam.zoom>0.8){
                cam.zoom -= 0.02;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (cam.position.x>=550){
                cam.translate(-3, 0, 0);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (cam.position.x<=600){
                cam.translate(3, 0, 0);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if (cam.position.y>=450){
                cam.translate(0, -3, 0);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (cam.position.y<=700) {
                cam.translate(0, 3, 0);
            }

        }
    }

    public void alignCamera()
    {
        Camera cam = this.getStage().getCamera();
        Viewport v = this.getStage().getViewport();
// center camera on actor
        cam.position.set( this.getX() + this.getOriginX(), this.getY() + this.getOriginY(), 0 );
// bound camera to layout

        cam.update();
    }
}
