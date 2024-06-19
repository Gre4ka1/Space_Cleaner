package com.mygdx.game.Views;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.mygdx.game.GameSettings;
import com.mygdx.game.managers.MemoryManager;

import java.util.ArrayList;

public class RecordListView extends TextView {

    public RecordListView(BitmapFont font, float y) {
        super(font, 0, y, "");
    }
    public void setRecords(ArrayList<Integer> recordsList) {
        text = "";
        int countOfRows = Math.min(recordsList.size(), 5);
        for (int i = 0; i < countOfRows; i++) {
            text += (i + 1) + ". - " + recordsList.get(i) + "\n";
        }

        GlyphLayout glyphLayout = new GlyphLayout(font, text);
        x = (GameSettings.SCR_WIDTH - glyphLayout.width) / 2;
    }
    public void reset(){
        MemoryManager.saveTableOfRecords(null);
    }

}