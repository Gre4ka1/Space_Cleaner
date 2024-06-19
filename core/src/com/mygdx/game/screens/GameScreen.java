package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Units.BigTrash;
import com.mygdx.game.Units.Bonus;
import com.mygdx.game.Units.CoolDownBonus;
import com.mygdx.game.Units.HealthKit;
import com.mygdx.game.Views.RecordListView;
import com.mygdx.game.managers.ContactManager;
import com.mygdx.game.FontBuilder;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSession;
import com.mygdx.game.GameSettings;
import com.mygdx.game.GameState;
import com.mygdx.game.Main;
import com.mygdx.game.Units.Bullet;
import com.mygdx.game.Units.Ship;
import com.mygdx.game.Units.Trash;
import com.mygdx.game.Views.ButtonView;
import com.mygdx.game.Views.ImageView;
import com.mygdx.game.Views.LiveView;
import com.mygdx.game.Views.MovingBackground;
import com.mygdx.game.Views.TextView;
import com.mygdx.game.managers.MemoryManager;

import java.util.Random;

public class GameScreen extends ScreenAdapter {
    Main main;
    public Ship ship;
    public GameSession session;
    ContactManager contactManager;
    MovingBackground movingBackground;
    LiveView liveView;
    ImageView topBlackoutView;
    ImageView fullBlackoutView;
    TextView scoreTextView;
    TextView pauseText;
    ButtonView pauseButton;
    ButtonView resumeButton;
    ButtonView homeButton;
    ButtonView homeButton2;
    RecordListView recordListView;
    GameState gameState;
    public GameScreen(Main main) {
        contactManager = new ContactManager(main.world);
        session=new GameSession();
        this.main=main;
        ship=new Ship(GameSettings.SCR_WIDTH/2, 100, GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT, main.world);
        movingBackground = new MovingBackground(GameResources.BACKGROUND_IMG_PATH);
        topBlackoutView = new ImageView(0, 1180, GameResources.IMAGE_VIEW_PATH);
        fullBlackoutView = new ImageView(0, 0, GameResources.BLACKOUT_FULL);
        liveView = new LiveView(305, 1215);
        main.commonWhiteFont = FontBuilder.generate(24, Color.WHITE, GameResources.FONT_PATH);
        scoreTextView = new TextView(main.commonWhiteFont, 50, 1215);
        pauseText = new TextView(main.commonWhiteFont, 320, 780,"Pause");
        pauseButton = new ButtonView(605, 1200, 46, 54, GameResources.PAUSE_IMG_PATH);
        homeButton = new ButtonView(200, 640, 150, 75, main.commonWhiteFont, GameResources.BUTTON_BACKGROUND_SHORT,"Home");
        homeButton2 = new ButtonView(285, 250, 150, 75, main.commonWhiteFont, GameResources.BUTTON_BACKGROUND_SHORT,"Home");
        recordListView=new RecordListView(main.commonWhiteFont,640);
        resumeButton = new ButtonView(370, 640, 150, 75, main.commonWhiteFont, GameResources.BUTTON_BACKGROUND_SHORT,"Resume");
    }
    @Override
    public void show() {
        restartGame();
    }
    @Override
    public void render(float delta) {
        if (session.state == GameState.PLAYING) {
            main.stepWorld();

            if (session.shouldSpawnTrash()) {
                if(GameSettings.isBonus) {
                    int temp = new Random().nextInt(10);
                    if (temp <= 1) new HealthKit(main.world);
                    else if (temp <= 3) new CoolDownBonus(main.world);
                }
                if (new Random().nextInt(6)!=0) new Trash(GameResources.TRASH_IMG_PATH,GameSettings.TRASH_WIDTH, GameSettings.TRASH_HEIGHT, main.world);
                else new BigTrash(GameSettings.TRASH_WIDTH,GameSettings.TRASH_HEIGHT,main.world);
            }
            ship.shot(main);
            switch (GameSettings.bonus){
                case "HEALTHKIT":
                    ship.setLives((byte) 3);
                    GameSettings.bonus="none";
                    break;
                case "COOLDOWN":
                    ship.cooldownBonus();
                    GameSettings.bonus="none";
                    break;
            }
            ship.checkBonusTime();
            Trash.updateTrash(main);
            Bullet.updateBullets(main);
            if(GameSettings.isBonus) Bonus.updateBonuses(main);
            movingBackground.move();
            liveView.setLeftLives(ship.getLivesLeft());
            session.updateScore();
            scoreTextView.setText("Score: " + session.getScore());
            if (ship.getLivesLeft()<=0) {
                session.endGame();
                recordListView.setRecords(MemoryManager.loadRecordsTable());
            }
        }
        handleInput();
        draw();
    }
    private void draw() {
        main.camera.update();
        main.batch.setProjectionMatrix(main.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        main.batch.begin();
        movingBackground.draw(main.batch);
        ship.draw(main.batch);
        Trash.drawAll(main.batch);
        Bullet.drawAll(main.batch);
        if(GameSettings.isBonus) Bonus.drawAll(main.batch);
        topBlackoutView.draw(main.batch);
        scoreTextView.draw(main.batch);
        liveView.draw(main.batch);
        pauseButton.draw(main.batch);
        if (session.state==GameState.PAUSED){
            fullBlackoutView.draw(main.batch);
            pauseText.draw(main.batch);
            homeButton.draw(main.batch);
            resumeButton.draw(main.batch);
        }
        if (session.state==GameState.ENDED){
            fullBlackoutView.draw(main.batch);
            recordListView.draw(main.batch);
            homeButton2.draw(main.batch);
        }
        main.batch.end();
    }
    private void handleInput() {
        if (Gdx.input.isTouched()) {
            main.touch = main.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            switch (session.state) {
                case PLAYING:
                    if (pauseButton.isHit(main.touch.x, main.touch.y)) {
                        session.pauseGame();
                    }
                    ship.move(main.touch);
                    break;

                case PAUSED:
                    if (resumeButton.isHit(main.touch.x, main.touch.y)) {
                        session.resumeGame();
                    }
                    if (homeButton.isHit(main.touch.x,main.touch.y)){
                        main.setScreen(main.menuScreen);
                    }
                    break;
                case ENDED:
                    if (homeButton2.isHit(main.touch.x,main.touch.y)){
                        main.setScreen(main.menuScreen);
                    }
            }
        }


    }
    private void restartGame() {
        session.resetScore();
        for (int i = 0; i < Trash.trashes.size(); i++) {
            main.world.destroyBody(Trash.trashes.get(i).body);
        }
        Trash.trashes.clear();

        if (ship != null) {
            main.world.destroyBody(ship.body);
        }

        for (int i = 0; i < Bullet.bullets.size(); i++) {
            main.world.destroyBody(Bullet.bullets.get(i).body);
        }
        Bullet.bullets.clear();

        ship = new Ship(
                GameSettings.SCR_WIDTH / 2, 150,
                GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT,
                main.world
        );
        session.startGame();
    }

    @Override
    public void dispose() {
        ship.dispose();
        liveView.dispose();
        movingBackground.dispose();
        topBlackoutView.dispose();

    }
}
