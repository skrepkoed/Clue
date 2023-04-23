package com.clue.game;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class ClueGame extends Game {
	SpriteBatch batch;
	Texture img;
	Stage mainStage;

	Entity field;
	Player player;
	PlayerInputProcessor processor;
	InputMultiplexer multiplexer;
	InputAdapter uiprocessor;
	Stage uistage;
	Skin skin;
	private Table table;
	@Override
	public void create () {
		skin = new Skin(Gdx.files.internal("./skin/uiskin.json"));
		batch =new SpriteBatch();
		Camera camera=new OrthographicCamera(300,300);
		Camera camera1=new OrthographicCamera(800,600);
		ExtendViewport viewport =new ExtendViewport(400, 400,camera);
		FitViewport guiviewport=new FitViewport(800,600,camera);
		mainStage = new Stage(viewport,batch);
		uistage = new Stage(guiviewport,batch);


		initialize();


		multiplexer=new InputMultiplexer();
		processor =new PlayerInputProcessor();
		multiplexer.addProcessor(uistage);
		multiplexer.addProcessor(processor);
		Gdx.input.setInputProcessor(multiplexer);


	}

	public void initialize(){

		field=new GameField();
		field.setTexture(new Texture(Gdx.files.internal("assets/field1.png")));
		//field.setPosition(150,50);
		field.setSize(800,600);
		mainStage.addActor(field);

		table = new Table();
		table.setFillParent(true);
		TextField[] textarray=new TextField[8];
		CardUI[] cardarray=new CardUI[8];
		for (int i=0;i<textarray.length;i++) {
			textarray[i]=new TextField("TEXT",skin);
			Card card=new Card();
			card.setVisible(false);
			CardUI cardUI=new CardUI(card);
			cardarray[i]=cardUI;
			mainStage.addActor(card);
		}
		Table table2=new Table();
		table2.row().height(75);
		for (int i=0; i<textarray.length;i++) {
			table2.add(textarray[i]);
			table2.add(cardarray[i]).maxHeight(30).maxWidth(30).top();
			table2.row().height(75);
		}

		table.add(table2).expandY().top();
		table.top().right();
		table.setDebug(true);


		player=new Player(150,50);
		//player.setPosition(150,50);
		player.setTexture(new Texture(Gdx.files.internal("assets/lady_scarlet.png")));
		player.setSize(player.getWidth(),player.getHeight());
		uistage.addActor(table);

		mainStage.addActor(player);

	}

	@Override
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();

		if (processor.isTouchPressed(0)) {
			//System.out.println("PRESSED");
			Vector3 worldCoord=mainStage.getCamera().unproject(new Vector3(processor.touchStates.get(0).coordinates.x,processor.touchStates.get(0).coordinates.y,0));
			Vector2 destination = ClueGameUtils.adjust_coordinates(worldCoord);
			player.setDestination(destination);
		}
		if (processor.isTouchDown(0)) {

		}
		if (processor.isTouchReleased(0)) {

		}



// act method

// defined by user
		update(dt);
		mainStage.act(dt);
// clear the screen
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(mainStage.getViewport().getCamera().combined);
// draw the graphics
		mainStage.draw();
		uistage.draw();

		processor.update();
	}

	public void update(float dt){
			mainStage.getCamera().update();
	}




	public void resize(int width, int height) {
		mainStage.getViewport().update(width,height);
		uistage.getViewport().update(width,height);

	}


	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
