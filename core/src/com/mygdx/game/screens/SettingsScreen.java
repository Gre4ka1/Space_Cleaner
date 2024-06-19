package com.mygdx.game.screens;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.Main;
import com.mygdx.game.Views.ButtonView;
import com.mygdx.game.Views.ImageView;
import com.mygdx.game.Views.MovingBackground;
import com.mygdx.game.Views.MusicListView;
import com.mygdx.game.Views.TextView;
import com.mygdx.game.managers.AudioManager;
import com.mygdx.game.managers.MemoryManager;


public class SettingsScreen extends ScreenAdapter {
    Main main;



    MovingBackground background;
    ImageView middleBlakout;
    TextView title;
    TextView music;
    TextView sound;
    TextView clearRecord;
    ButtonView buttonView;
    MusicListView musicListView;
    TextView bonusOnView;

    public SettingsScreen(Main main) {
        this.main = main;
        background=new MovingBackground(GameResources.BACKGROUND_IMG_PATH);
        buttonView=new ButtonView(300,340,120,70,main.commonBlackFont,GameResources.BUTTON_BACKGROUND_SHORT,"return");
        middleBlakout=new ImageView((720- new Texture(GameResources.BLACKOUT_MIDDLE).getWidth())/2f,300,GameResources.BLACKOUT_MIDDLE);
        music=new TextView(main.commonWhiteFont,150,700,"music "+ (GameSettings.isMusic?"on":"off"));
        sound=new TextView(main.commonWhiteFont,150,630,"sounds "+(GameSettings.isSounds?"on":"off"));
        clearRecord=new TextView(main.commonWhiteFont,150,560,"clear record");
        title=new TextView(main.largeWhiteFont,300,900,"Settings");
        title.setX((GameSettings.SCR_WIDTH-title.getWidth())/2f);
        musicListView=new MusicListView(350,580,200,120,main);
        bonusOnView = new TextView(main.commonWhiteFont,150,490,"bonus "+(GameSettings.isBonus?"on":"off"));
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        if (main.getScreen().equals(main.settingsScreen)) {
            handleInput();
            music.setText("music "+ (GameSettings.isMusic?"on":"off"));
            sound.setText("sound "+ (GameSettings.isSounds?"on":"off"));
            draw(main.batch);
        }
    }

    private void draw(SpriteBatch batch) {
        main.camera.update();
        batch.setProjectionMatrix(main.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        batch.begin();
        background.draw(batch);
        middleBlakout.draw(batch);
        title.draw(batch);
        music.draw(batch);
        sound.draw(batch);
        clearRecord.draw(batch);
        musicListView.draw(batch);
        buttonView.draw(batch);
        bonusOnView.draw(batch);
        batch.end();
    }
    private void handleInput() {
        if (Gdx.input.justTouched()) {
            main.touch = main.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (music.isHit(main.touch.x, main.touch.y)) {
                //GameSettings.isMusic=!GameSettings.isMusic;

                MemoryManager.saveMusicSettings(!GameSettings.isMusic);
                main.audioManager.updateMusicFlag();
                //if (!GameSettings.isMusic) main.audioManager.backgroundMusic.stop();
                //else main.audioManager.backgroundMusic.play();
            } else if (sound.isHit(main.touch.x, main.touch.y)) {
                //GameSettings.isSounds=!GameSettings.isSounds;
                MemoryManager.saveSoundSettings(!GameSettings.isSounds);
                main.audioManager.updateSoundFlag();
            } else if (clearRecord.isHit(main.touch.x, main.touch.y)) {
                main.gameScreen.recordListView.reset();
            } else if (buttonView.isHit(main.touch.x, main.touch.y)) {
                main.setScreen(main.menuScreen);
            }else if (bonusOnView.isHit(main.touch.x, main.touch.y)) {
                GameSettings.isBonus=!GameSettings.isBonus;
                bonusOnView.setText("bonus "+(GameSettings.isBonus?"on":"off"));
            }

            for (int i = 0; i < musicListView.musicList.size(); i++) {
                if (musicListView.musicList.get(i).isHit(main.touch.x, main.touch.y)) {
                    main.audioManager.backgroundMusic.stop();
                    MemoryManager.saveMusicChange(musicListView.musicPathList.get(i));
                    main.audioManager.updateMusicChange();


                }
            }
        }
    }

    @Override
    public void dispose() {
        background.dispose();
        middleBlakout.dispose();
        title.dispose();
        music.dispose();
        sound.dispose();
        clearRecord.dispose();
        buttonView.dispose();
    }
}