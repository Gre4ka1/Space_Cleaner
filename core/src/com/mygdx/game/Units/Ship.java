package com.mygdx.game.Units;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.Main;

public class Ship extends GameObject {
    public long lastShotTime;
    private byte lives;
    private long bonusStartTime;
    private boolean isBonus;
    public Ship(int x, int y, int width, int height, World world) {
        super(GameResources.SHIP_IMG_PATH, x, y, width, height, world,GameSettings.SHIP_BIT);
        body.setLinearDamping(8);
        lastShotTime=TimeUtils.millis();
        lives=3;
        isBonus=false;
    }

    @Override
    public void draw(SpriteBatch batch) {
        putInFrame();
        Gdx.input.isTouched();
        super.draw(batch);
    }

    @Override
    public void move(Vector3 touch){

        body.applyForceToCenter(
                new Vector2(
                        (touch.x - getX()) * GameSettings.SHIP_FORCE_RATIO,
                        (touch.y - getY()) * GameSettings.SHIP_FORCE_RATIO),
                true
        );
    }

    @Override
    public void hit() {
        lives--;

    }

    public void shot(Main main){
        if (needToShot()){
            new Bullet(getX(), (int) (getY()+height/2),main.world);
            if (GameSettings.isSounds) main.audioManager.shootSound.play(0.3f);
            lastShotTime= TimeUtils.millis();
        }
    }
    public boolean needToShot(){
        return TimeUtils.millis() - lastShotTime >= GameSettings.SHOOTING_COOL_DOWN;
    }

    private void putInFrame() {
        if (getY() > (GameSettings.SCR_HEIGHT / 2f - height / 2f)) {
            setY((int) (GameSettings.SCR_HEIGHT / 2 - height / 2));
        }
        if (getY() <= (height / 2f)) {
            setY((int) (height / 2));
        }
        if (getX() < 0) {
            setX((int) (GameSettings.SCR_WIDTH-width/2f));
        }
        if (getX()> (GameSettings.SCR_WIDTH)) {
            setX((int) (width/2));
        }
    }
    public void cooldownBonus(){
        bonusStartTime=TimeUtils.millis();
        GameSettings.SHOOTING_COOL_DOWN=600;
        isBonus=true;
    }
    public void checkBonusTime(){
        if (isBonus) {
            if (TimeUtils.millis() - bonusStartTime >= GameSettings.COOLDOWN_BONUS_TIME) {
                GameSettings.SHOOTING_COOL_DOWN=1000;
                isBonus=false;
            }
        }
    }

    public byte getLivesLeft() {
        return lives;
    }

    public void setLives(byte lives) {
        this.lives = lives;
    }
}
