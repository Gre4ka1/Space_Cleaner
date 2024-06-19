package com.mygdx.game.Views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameSettings;

public class MovingBackground extends View {

    Texture texture;

    int texture1Y;
    int texture2Y;
    int speed = 2;

    public MovingBackground(String pathToTexture) {
        super(0, 0);
        texture1Y = 0;
        texture2Y = GameSettings.SCR_HEIGHT;
        texture = new Texture(pathToTexture);
    }
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, 0, texture1Y, GameSettings.SCR_WIDTH, GameSettings.SCR_HEIGHT);
        batch.draw(texture, 0, texture2Y, GameSettings.SCR_WIDTH, GameSettings.SCR_HEIGHT);
    }

    public void move() {
        texture1Y -= speed;
        texture2Y -= speed;
        if (texture1Y+ GameSettings.SCR_HEIGHT <= 0) {
            texture1Y = GameSettings.SCR_HEIGHT;
        }
        if (texture2Y+ GameSettings.SCR_HEIGHT <= 0) {
            texture2Y = GameSettings.SCR_HEIGHT;
        }
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}