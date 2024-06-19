package com.mygdx.game.Views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameResources;

public class LiveView extends View{
    Texture texture;
    private byte leftLives;
    private final byte livePadding=6;
    public LiveView(float x, float y) {
        super(x, y);
        texture = new Texture(GameResources.LIVE_IMG_PATH);
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        leftLives = 0;
    }
    public void setLeftLives(byte leftLives) {
        this.leftLives = leftLives;
    }
    @Override
    public void draw(SpriteBatch batch) {
        if (leftLives > 0) batch.draw(texture, x + (texture.getWidth() + livePadding), y, width, height);
        if (leftLives > 1) batch.draw(texture, x, y, width, height);
        if (leftLives > 2) batch.draw(texture, x + 2 * (texture.getWidth() + livePadding), y, width, height);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
