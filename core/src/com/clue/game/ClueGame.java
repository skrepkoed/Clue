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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.clue.game.gamelogic.Accusation;
import com.clue.game.gamelogic.Field;
import com.clue.game.gamelogic.GameBroker;
import com.clue.game.gamelogic.InGameCard;
import com.clue.game.gamelogic.InGamePlayer;
import com.clue.game.gamelogic.Person;
import com.clue.game.gamelogic.Place;
import com.clue.game.gamelogic.Statement;
import com.clue.game.gamelogic.Weapon;

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
	Stage uistage;
	Stage menuStage;
	Label buttonLabelBack;
	Skin skin;
	private Table table;
	Label buttonLabelAccuse;
	Label buttonLabelNextTurn;

	private Table table5;
	ArrayList<StatementHolder> statementHolderArray=new ArrayList<>();

	GameBroker gameBroker;
	GameState gameState;

	Player currentPlayer;
	boolean nextTurn=false;
	@Override
	public void create () {
		skin = new Skin(Gdx.files.internal("./skin/uiskin.json"));
		Texture background_ui=new Texture(Gdx.files.internal("./assets/background_ui.png"));
		Texture metal_button=new Texture(Gdx.files.internal("./assets/metall_button.png"));
		skin.add("background_ui",background_ui);
		skin.add("metal_button",metal_button);
		Label.LabelStyle labelStyle=new Label.LabelStyle();
		Label.LabelStyle buttonStyle=new Label.LabelStyle();

		labelStyle.background=skin.getDrawable("background_ui");
		buttonStyle.background=skin.getDrawable("metal_button");
		labelStyle.font = skin.getFont("default-font");
		buttonStyle.font=skin.getFont("default-font");
		skin.add("defaultBack",labelStyle);
		skin.add("metalButton",buttonStyle);

		batch =new SpriteBatch();
		Camera camera=new OrthographicCamera(300,300);
		Camera camera1=new OrthographicCamera(1200,1000);

		ExtendViewport viewport =new ExtendViewport(400, 400,camera);

		ScreenViewport guiviewport=new ScreenViewport(camera1);

		mainStage = new Stage(viewport,batch);
		uistage = new Stage(guiviewport,batch);
		initializeMenu("Play");
		initialize();
		mainStage.getCamera().translate(450,350,0);


		multiplexer=new InputMultiplexer();
		processor =new PlayerInputProcessor();
		multiplexer.addProcessor(uistage);
		multiplexer.addProcessor(menuStage);
		multiplexer.addProcessor(processor);
		Gdx.input.setInputProcessor(multiplexer);


	}

	public void initializeMenu(String text){
		Camera camera2=new OrthographicCamera(1200,1000);
		ExtendViewport menuViewport=new ExtendViewport(1200,1000,camera2);
		menuStage=new Stage(menuViewport,batch);
		Label playButton=new Label(text,skin,"metalButton");
		playButton.setSize(300,200);
		playButton.setPosition(600,600);
		playButton.setAlignment(Align.center);
		playButton.setFontScale(3);
		playButton.addListener(new ClickListener(){
			public void clicked(InputEvent event,float x,float y){
				menuStage.dispose();
			}
		});
		Entity menuScreen=new Entity();
		menuScreen.setTexture(new Texture(Gdx.files.internal("./assets/menu_screen.png")));
		menuScreen.setSize(Gdx.graphics.getWidth()+50,Gdx.graphics.getHeight()+50);
		menuStage.addActor(menuScreen);
		menuStage.addActor(playButton);
	}
	public void initialize(){
		field=new GameField();
		field.setTexture(new Texture(Gdx.files.internal("assets/field3.png")));
		field.setSize(1200,1000);
		mainStage.addActor(field);

		gameBroker=GameBroker.getGame();
		//ArrayList<Player>players=new ArrayList<Player>();
		gameState=new GameState();
		for (Person person:gameBroker.charactersDeck) {
			Player player=new Player(person);
			for (InGamePlayer ip:gameBroker.players) {
				if (ip.character==person){
					gameState.players.add(player);
				}
			}
			gameState.characters.add(player);
			mainStage.addActor(player);
		}
		gameState.setIterator();

		Field.createFieldMap();
		table = new Table();
		table.setFillParent(true);

		Table table2=createCardTable(gameBroker.weapons);
		Table table3=createCardTable(gameBroker.persons);


		buttonLabelAccuse=new Label("Accuse",skin,"metalButton");
		buttonLabelAccuse.setAlignment(Align.center);
		buttonLabelAccuse.addListener(new ClickListener(){
			public void clicked(InputEvent event,float x,float y){
				accuse();
				GameBroker.getGame().currentPlayer.accusation=new Accusation();
				Table table=(Table)buttonLabelAccuse.getParent();
				table.getCell(buttonLabelAccuse).setActor(buttonLabelNextTurn).fillX().size(150,100);

			}
		});
		buttonLabelNextTurn=new Label("Next turn",skin,"metalButton");
		buttonLabelNextTurn.setAlignment(Align.center);
		buttonLabelNextTurn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				//buttonLabelNextTurn.getParent().addActor(buttonLabelAccuse);
				//buttonLabelNextTurn.remove();
				Table table=(Table)buttonLabelNextTurn.getParent();
				table.getCell(buttonLabelNextTurn).setActor(buttonLabelAccuse).fillX().size(150,100);
				gameBroker.getCurrentPlayer();
				gameState.getCurrentPlayer();
				nextTurn=true;
				refreshCurrentPlayer();
			}
		});
		 buttonLabelBack=new Label("Back",skin,"metalButton");
		buttonLabelBack.setAlignment(Align.center);
		buttonLabelBack.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Stack stack=(Stack) buttonLabelBack.getParent().getChild(0);
				if (stack.getChild(0).isVisible()){
					stack.getChild(0).setVisible(false);
					stack.getChild(1).setVisible(true);
				}
			}
		});
		table2.setVisible(false);
		Table table4=new Table();
		Stack stack=new Stack(table2,table3);
		table4.add(stack).expandY().top().colspan(2).maxWidth(300);
		table4.row();
		table4.add(buttonLabelBack).fillX().size(150,100);
		table4.add(buttonLabelAccuse).fillX().size(150,100);
		//table4.add(buttonLabelNextTurn).fillX().size(150,100);
		table5 = new Table();
		createSolutionTable();
		table.add(table5).maxWidth(500).top().padRight(Gdx.graphics.getWidth()-800);
		table.add(table4);
		table.top().right();
		uistage.addActor(table);
		GameBroker.getGame().getCurrentPlayer();
		gameState.getCurrentPlayer();
		refreshCurrentPlayer();
	}

	public void refreshCurrentPlayer(){
		table5.getCells().get(0).setActor(new Label("Current player: \n"+GameBroker.getGame().currentPlayer.character+"\n" +
						"Moves: "
						+GameBroker.getGame().currentPlayer.getMoves(),skin,"defaultBack")).size(100,100);
	}

	public Entity initializeEnvelope(){
		Entity envelope=new Entity();
		envelope.setTexture(new Texture(Gdx.files.internal("./assets/envelope.png")));
		envelope.setSize(100,100);
		envelope.addListener(new ClickListener(){
			public void clicked(InputEvent event,float x,float y){
				GameBroker.getGame().finalAccusation=true;
				table5.getCells().get(0).setActor(new Label("Final\n Accusation!",skin,"metalButton"));

			}
		});
		return envelope;
	}

	public void createSolutionTable(){
		table5.add(new Label("Current player: +\n"+GameBroker.getGame().currentPlayer.character+"\n" +
						"Moves: "
						+GameBroker.getGame().currentPlayer.getMoves(),skin,"defaultBack")).size(100,100);
		for (InGamePlayer player:gameBroker.players) {
			table5.add(new Label(player.character.toString(),skin,"defaultBack")).size(100,100);
		}
		table5.add(initializeEnvelope());
		table5.row().height(40);
		createSubTable(table5,gameBroker.persons);
		createSubTable(table5,gameBroker.places);
		createSubTable(table5,gameBroker.weapons);
	}

	public <E extends Enum<E>& InGameCard> Table createSubTable(Table table,ArrayList<E> array){
		for (E card:array) {
			table.add(new Label(card.toString(),skin,"defaultBack")).size(100,40);
			for (InGamePlayer player:gameBroker.players) {
				StatementHolder statementHolder=new StatementHolder(new Statement(player,card));
				statementHolderArray.add(statementHolder);
				if (!player.isAi()&&player.getHand().contains(card)){
					table.add(statementHolder.setTrue());
				} else if (gameBroker.sharedCards.getHand().contains(card)) {
					table.add(statementHolder.setTrue());
				} else {
					table.add(statementHolder);
				}

			}
			table.row().height(40);
		}
		return table;
	}

	public <E extends Enum<E> & InGameCard> Table createCardTable(ArrayList<E> cards){
		Label[] textArray=new Label[cards.size()];
		CardUI[] cardArray=new CardUI[cards.size()];
		for (int i=0;i<textArray.length;i++) {
			textArray[i]=new Label(cards.get(i).toString(),skin,"defaultBack");
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
			table.add(textArray[i]).size(150,150);
			table.row().height(150);


		}
		return table;
	}

	@Override
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();
		if (!gameBroker.currentPlayer.isAi()) {
			buttonLabelBack.getParent().getChild(0).setTouchable(Touchable.enabled);
			buttonLabelBack.getParent().getChild(1).setTouchable(Touchable.enabled);
			if (processor.isTouchPressed(0)) {
				Vector3 worldCoord = mainStage.getCamera().unproject(new Vector3(processor.touchStates.get(0).coordinates.x, processor.touchStates.get(0).coordinates.y, 0));
				Vector2 destination = ClueGameUtils.adjust_coordinates(worldCoord);
				gameState.currentPlayer.setDestinationTile(destination,true);
			}
		}else {
			if (nextTurn){
				buttonLabelBack.getParent().getChild(0).setTouchable(Touchable.disabled);
				buttonLabelBack.getParent().getChild(1).setTouchable(Touchable.disabled);
				nextTurn=false;
			GameBroker.getGame().aITurn();
			gameState.currentPlayer.setDestinationTile(GameBroker.locationsTile.get(gameBroker.currentPlayer.accusation.getPlace()).composeVector(),true);
			Table t1=(Table)(uistage.getActors().get(0));
			Table t11= (Table)(t1.getCells().get(1).getActor());
			Stack s1=(Stack)( t11.getCells().get(0).getActor());
			Table t2=(Table)s1.getChild(1);
			//CardUI card=(CardUI)(t2.getCells().get(gameBroker.persons.indexOf(gameBroker.currentPlayer.accusation.getPerson())+3).getActor());
			ArrayList<CardUI> cardUIArrayListPerson=new ArrayList<>();
			for (Cell cell:t2.getCells()) {
				if (cell.getActor().getClass().getSimpleName().equals("CardUI")){
					cardUIArrayListPerson.add((CardUI) cell.getActor());
				}
			}
			CardUI cardPerson=cardUIArrayListPerson.get(gameBroker.persons.indexOf(gameBroker.currentPlayer.accusation.getPerson()));

			if(cardPerson.aIUiInteraction()){
			Table t3=(Table)s1.getChild(0);
			ArrayList<CardUI> cardUIArrayListWeapon =new ArrayList<>();
			for (Cell cell:t3.getCells()) {
				if (cell.getActor().getClass().getSimpleName().equals("CardUI")){
					cardUIArrayListWeapon.add((CardUI) cell.getActor());
				}
			}
			CardUI cardUIWeapon =cardUIArrayListWeapon.get(gameBroker.weapons.indexOf(gameBroker.currentPlayer.accusation.getWeapon()));
			cardUIWeapon.aIUiInteraction();
			}else {
				Table table=(Table)buttonLabelAccuse.getParent();
				table.getCell(buttonLabelAccuse).setActor(buttonLabelNextTurn).fillX().size(150,100);
			}
			}
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
		menuStage.draw();



		processor.update();
	}

	public void accuse(){
		if (GameBroker.getGame().currentPlayer.accusation.isComplete()) {
			Stack stack = (Stack) buttonLabelAccuse.getParent().getChild(0);
			stack.getChild(1).setVisible(true);
			stack.getChild(0).setVisible(false);
			Table table = (Table) stack.getChild(1);
			if (GameBroker.getGame().finalAccusation) {
				String result=GameBroker.getGame().currentPlayer.accusation.toString();
				if(GameBroker.solution.revelation(GameBroker.getGame().currentPlayer.accusation)){
					initializeMenu("You win!\n"+result);
				}else {
					initializeMenu("You loose!\n"+GameBroker.solution.toString());
				}
			} else {
				Statement statement = gameBroker.defineStatement();
				if (!GameBroker.getGame().currentPlayer.isAi()) {
					for (StatementHolder st : statementHolderArray) {
						if (st.statement.equals(statement)) {
							st.setTrue();
							if ((statementHolderArray.indexOf(st) + 1) % 3 == 0) {
								statementHolderArray.get(statementHolderArray.indexOf(st) - 1).setFalse();
								statementHolderArray.get(statementHolderArray.indexOf(st) - 2).setFalse();
							} else if ((statementHolderArray.indexOf(st) + 1) % 3 == 2) {
								statementHolderArray.get(statementHolderArray.indexOf(st) + 1).setFalse();
								statementHolderArray.get(statementHolderArray.indexOf(st) - 1).setFalse();
							} else {
								statementHolderArray.get(statementHolderArray.indexOf(st) + 1).setFalse();
								statementHolderArray.get(statementHolderArray.indexOf(st) + 2).setFalse();
							}
						}
					}
				}
				for (int i = 0; i < table.getCells().size; i += 2) {
					InGameCard card = ((CardUI) (table.getCells().get(i).getActor())).getCard().getInGameCard();
					Person person = (Person) card;

					if (!gameBroker.currentPlayer.isAi()) {
						if (!statement.isEmpty()) {
							Person person1 = statement.inGamePlayer.character;
							if (person1 == person) {
								((Label) table.getCells().get(i + 1).getActor()).setText("I have " + statement.card + " card");
							}
						} else {
							Person person1 = gameBroker.currentPlayer.character;
							if (person == person1) {
								((Label) table.getCells().get(i + 1).getActor()).setText("Maybe no one has such card");
							}
						}
					} else {
						if (!statement.isEmpty()) {
							Person person1 = statement.inGamePlayer.character;
							if (gameBroker.currentPlayer.character == person) {
								((Label) table.getCells().get(i + 1).getActor()).setText("I got some card from +\n" + statement.inGamePlayer.character);
							}
						} else {
							Person person1 = gameBroker.currentPlayer.character;
							if (person == person1) {
								((Label) table.getCells().get(i + 1).getActor()).setText("Maybe no one has such card");
							}
						}
					}
				}
			}
		}
	}

	public void update(float dt){
		uistage.getCamera().update();
		mainStage.getCamera().update();

	}
	public void resize(int width, int height) {
		mainStage.getViewport().update(width,height);
		uistage.getViewport().update(width,height,true);
		if (!menuStage.getActors().isEmpty()){
			menuStage.getViewport().update(width,height,true);
			menuStage.getActors().get(0).setSize(width+50,height+50);
		}
		table.getCells().get(0).padRight(Gdx.graphics.getWidth()-800);

	}
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
