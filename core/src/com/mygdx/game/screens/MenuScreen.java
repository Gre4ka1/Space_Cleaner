package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.Main;
import com.mygdx.game.Views.ButtonView;
import com.mygdx.game.Views.MovingBackground;
import com.mygdx.game.Views.TextView;

public class MenuScreen extends ScreenAdapter {

    Main main;
    MovingBackground background;
    TextView titleView;
    ButtonView startButtonView;
    ButtonView settingsButtonView;
    ButtonView exitButtonView;
    public MenuScreen(Main main) {
        this.main = main;
        background = new MovingBackground(GameResources.BACKGROUND_IMG_PATH);
        titleView = new TextView(main.largeWhiteFont, 180, 960, "Space Cleaner");
        titleView.setX((GameSettings.SCR_WIDTH-titleView.getWidth())/2f);
        startButtonView = new ButtonView(140, 646, 440, 70, main.commonBlackFont, GameResources.BUTTON_BACKGROUND_LONG, "start");
        settingsButtonView = new ButtonView(140, 551, 440, 70, main.commonBlackFont, GameResources.BUTTON_BACKGROUND_LONG, "settings");
        exitButtonView = new ButtonView(140, 456, 440, 70, main.commonBlackFont, GameResources.BUTTON_BACKGROUND_LONG, "exit");
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        if (main.getScreen().equals(main.menuScreen)) {
            handleInput();
            draw(main.batch);
        }
    }
    private void draw(SpriteBatch batch){
        main.camera.update();
        main.batch.setProjectionMatrix(main.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        main.batch.begin();
        background.draw(batch);
        titleView.draw(batch);
        exitButtonView.draw(batch);
        startButtonView.draw(batch);
        settingsButtonView.draw(batch);
        main.batch.end();
    }
    private void handleInput() {
        if (Gdx.input.justTouched()) {
            main.touch = main.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (startButtonView.isHit(main.touch.x, main.touch.y)) {
                main.setScreen(main.gameScreen);
            }
            else if (exitButtonView.isHit(main.touch.x, main.touch.y)) {
                Gdx.app.exit();
            }
            else if (settingsButtonView.isHit(main.touch.x, main.touch.y)) {
                main.setScreen(main.settingsScreen);
            }
        }
    }

    @Override
    public void dispose() {
        background.dispose();
        titleView.dispose();
        startButtonView.dispose();
        settingsButtonView.dispose();
        exitButtonView.dispose();
    }
}