package com.mygdx.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.managers.AudioManager;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.SettingsScreen;

public class Main extends Game {
	public World world;

	public GameScreen gameScreen;
	public MenuScreen menuScreen;
	public SettingsScreen settingsScreen;
	public Vector3 touch;
    public OrthographicCamera camera;
    public SpriteBatch batch;
	float accumulator = 0;
	public BitmapFont commonWhiteFont;
	public BitmapFont commonBlackFont;
	public BitmapFont largeWhiteFont;
	public AudioManager audioManager;
	@Override
	public void create () {
		Box2D.init();
		world = new World(new Vector2(0, 0), false);
		batch = new SpriteBatch();
        camera = new OrthographicCamera();
		commonWhiteFont = FontBuilder.generate(24, Color.WHITE, GameResources.FONT_PATH);
		commonBlackFont = FontBuilder.generate(24, Color.BLACK, GameResources.FONT_PATH);
		largeWhiteFont = FontBuilder.generate(48, Color.WHITE, GameResources.FONT_PATH);
        camera.setToOrtho(false, GameSettings.SCR_WIDTH, GameSettings.SCR_HEIGHT);
		gameScreen = new GameScreen(this);
		menuScreen = new MenuScreen(this);
		settingsScreen = new SettingsScreen(this);
		audioManager=new AudioManager();
		setScreen(menuScreen);
	}

	public void stepWorld() {
		float delta = Gdx.graphics.getDeltaTime();
		accumulator += delta;

		if (accumulator >= GameSettings.STEP_TIME) {
			accumulator -= GameSettings.STEP_TIME;
			world.step(GameSettings.STEP_TIME, GameSettings.VELOCITY_ITERATIONS, GameSettings.POSITION_ITERATIONS);
		}
	}
	
	@Override
	public void dispose () {
        batch.dispose();
    }
}
