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
        //
        handleInput();

    }

    private void handleInput() {

        OrthographicCamera cam = (OrthographicCamera) this.getStage().getCamera();

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            cam.zoom += 0.02;
            //If the A Key is pressed, add 0.02 to the Camera's Zoom
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            cam.zoom -= 0.02;
            //If the Q Key is pressed, subtract 0.02 from the Camera's Zoom
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            cam.translate(-3, 0, 0);
            //If the LEFT Key is pressed, translate the camera -3 units in the X-Axis
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            cam.translate(3, 0, 0);
            //If the RIGHT Key is pressed, translate the camera 3 units in the X-Axis
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            cam.translate(0, -3, 0);
            //If the DOWN Key is pressed, translate the camera -3 units in the Y-Axis
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            cam.translate(0, 3, 0);
            //If the UP Key is pressed, translate the camera 3 units in the Y-Axis
        }

        this.getStage().getCamera().update();

        //cam.zoom = MathUtils.clamp(cam.zoom, 0.1f, 100/cam.viewportWidth);


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
