package com.clue.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ClueGame extends Game {
	SpriteBatch batch;
	Texture img;
	Stage mainStage;

	Entity field;
	Player player;
	PlayerInputProcessor processor;
	
	@Override
	public void create () {

		batch =new SpriteBatch();
		ExtendViewport viewport =new ExtendViewport(200,200,new OrthographicCamera());
		mainStage = new Stage(viewport,batch);
		//Gdx.input.setInputProcessor(mainStage);
		initialize();
		//InputMultiplexer inputMultiplexer = new InputMultiplexer();
		processor =new PlayerInputProcessor();
		//processor.setPlayer(player);
		//inputMultiplexer.addProcessor(processor);
		Gdx.input.setInputProcessor(processor);
	}

	public void initialize(){
		field=new GameField();
		field.setTexture(new Texture(Gdx.files.internal("assets/field1.png")));
		//field.setPosition(150,50);
		field.setSize(800,600);

		player=new Player();
		player.setPosition(150,236);
		player.setTexture(new Texture(Gdx.files.internal("assets/for_hover.png")));
		mainStage.addActor(field);
		mainStage.addActor(player);
		//mainStage.getCamera().translate(100,100,0);

	}

	@Override
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();
		mainStage.act(dt);
		if (processor.isTouchPressed(0)) {
			//System.out.println("PRESSED");
			Vector3 worldCoord=mainStage.getCamera().unproject(new Vector3(processor.touchStates.get(0).coordinates.x,processor.touchStates.get(0).coordinates.y,0));
			player.setPosition(worldCoord.x,worldCoord.y);
			System.out.println(worldCoord.x);
			System.out.println(worldCoord.y);
		}
		if (processor.isTouchDown(0)) {

		}
		if (processor.isTouchReleased(0)) {

		}

// act method

// defined by user
		update(dt);
// clear the screen
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(mainStage.getViewport().getCamera().combined);
// draw the graphics
		mainStage.draw();

		processor.update();
	}

	public void update(float dt){

	}


	public void resize(int width, int height) {
		mainStage.getViewport().update(width,height);
	}


	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
