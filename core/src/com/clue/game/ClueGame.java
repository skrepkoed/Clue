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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.clue.game.gamelogic.Field;
import com.clue.game.gamelogic.GameBroker;
import com.clue.game.gamelogic.InGameCard;
import com.clue.game.gamelogic.InGamePlayer;
import com.clue.game.gamelogic.Person;

import java.util.ArrayList;
import java.util.Iterator;

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

	GameBroker gameBroker;
	GameState gameState;
	//ArrayList<Player>players;
	//Iterator<Player> nextPlayer;
	Player currentPlayer;
	@Override
	public void create () {
		skin = new Skin(Gdx.files.internal("./skin/uiskin.json"));
		Texture background_ui=new Texture(Gdx.files.internal("./assets/background_ui.png"));
		skin.add("background_ui",background_ui);
		Label.LabelStyle labelStyle=new Label.LabelStyle();
		labelStyle.background=skin.getDrawable("background_ui");
		labelStyle.font = skin.getFont("default-font");
		skin.add("default",labelStyle);
		batch =new SpriteBatch();
		Camera camera=new OrthographicCamera(300,300);
		Camera camera1=new OrthographicCamera(1200,1000);
		ExtendViewport viewport =new ExtendViewport(400, 400,camera);
		ScreenViewport guiviewport=new ScreenViewport(camera1);
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
		field.setTexture(new Texture(Gdx.files.internal("assets/field3.png")));
		field.setSize(1200,1000);
		mainStage.addActor(field);
		gameBroker=GameBroker.getGame();
		ArrayList<Player>players=new ArrayList<Player>();
		for (Person person:gameBroker.charactersDeck) {
			Player player=new Player(person);
			for (InGamePlayer inGameplayer:gameBroker.players) {
				if (inGameplayer.character==person){
					players.add(player);
				}
			}
			mainStage.addActor(player);
		}

		gameState=new GameState(players);
		Field.createFieldMap();
		table = new Table();
		table.setFillParent(true);
		/*Label[] textarray=new Label[6];
		CardUI[] cardarray=new CardUI[6];
		for (int i=0;i<textarray.length;i++) {
			//Label.LabelStyle labelStyle=
			textarray[i]=new Label(gameBroker.weapons.get(i).toString(),skin);
			Card card=new Card(gameBroker.weapons.get(i));
			card.setVisible(false);
			CardUI cardUI=new CardUI(card,gameState);
			cardarray[i]=cardUI;
			mainStage.addActor(card);
		}
		Table table2=new Table();
		table2.row().height(150);
		for (int i=0; i<textarray.length;i++) {
			table2.add(cardarray[i]).top().size(150);
			table2.add(textarray[i]).size(100,150);
			table2.row().height(150);
		}*/
		Table table2=createCardTable(gameBroker.weapons);
		Table table3=createCardTable(gameBroker.persons);
		table2.setVisible(false);
		Stack stack=new Stack(table2,table3);
		table.add(stack).expandY().top().right().maxWidth(400);
		table.top().right();
		table.setDebug(true);




		uistage.addActor(table);

	}

	public <E extends Enum<E> & InGameCard> Table createCardTable(ArrayList<E> cards){
		Label[] textArray=new Label[cards.size()];
		CardUI[] cardArray=new CardUI[cards.size()];
		for (int i=0;i<textArray.length;i++) {
			textArray[i]=new Label(cards.get(i).toString(),skin);
			Card card=new Card(cards.get(i));
			card.setVisible(false);
			CardUI cardUI=new CardUI(card,gameState);
			cardArray[i]=cardUI;
			mainStage.addActor(card);
		}
		Table table=new Table();
		table.row().height(150);
		for (int i=0; i<textArray.length;i++) {
			table.add(cardArray[i]).top().size(150);
			table.add(textArray[i]).size(100,150);
			table.row().height(150);
		}
		return table;
	}

	@Override
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();
		if (processor.isTouchPressed(0)) {
			Vector3 worldCoord=mainStage.getCamera().unproject(new Vector3(processor.touchStates.get(0).coordinates.x,processor.touchStates.get(0).coordinates.y,0));
			Vector2 destination = ClueGameUtils.adjust_coordinates(worldCoord);
			gameState.getCurrentPlayer().setDestinationTile(destination);
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
		uistage.getCamera().update();
		mainStage.getCamera().update();

	}
	public void resize(int width, int height) {
		mainStage.getViewport().update(width,height,true);
		uistage.getViewport().update(width,height,true);
	}
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
