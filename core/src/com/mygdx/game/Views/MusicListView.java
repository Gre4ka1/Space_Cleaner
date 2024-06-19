package com.mygdx.game.Views;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameResources;
import com.mygdx.game.Main;

import java.util.ArrayList;

public class MusicListView extends View{
     public ArrayList<ButtonView> musicList = new ArrayList<>();
     public ArrayList<String> musicPathList = new ArrayList<>();


    public MusicListView(float x, float y, float width, float height, Main main) {
        super(x, y, width, height);
        musicList.add(new ButtonView(x,y+height-40,230,30,main.commonBlackFont,GameResources.BUTTON_BACKGROUND_LONG,"Vangers forever"));
        musicList.add(new ButtonView(x,y+height-80,230,30,main.commonBlackFont,GameResources.BUTTON_BACKGROUND_LONG,"Secret theme"));
        musicList.add(new ButtonView(x,y+height-120,230,30,main.commonBlackFont,GameResources.BUTTON_BACKGROUND_LONG,"Xplo"));
        musicPathList.add(GameResources.VANGERS_FOREVER_PATH);
        musicPathList.add(GameResources.SECRET_THEME_PATH);
        musicPathList.add(GameResources.XPLO_PATH);
    }

    @Override
    public void draw(SpriteBatch batch) {
        for (ButtonView b:musicList) {
            b.draw(batch);
        }
    }
}
