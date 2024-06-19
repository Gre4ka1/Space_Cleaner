package com.mygdx.game.Units;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.GameSettings;
import com.mygdx.game.Main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Bonus extends GameObject{
    public static ArrayList<Bonus> bonuses=new ArrayList<>();
    World world;
    boolean isHit;
    private long spawnTime;


    Bonus(String texturePath, World world) {
        super(texturePath,
                30+new Random().nextInt(GameSettings.SCR_WIDTH-60),
                GameSettings.SCR_HEIGHT+GameSettings.BONUS_HEIGHT/2,
                GameSettings.BONUS_WIDTH, GameSettings.BONUS_HEIGHT, world,
                GameSettings.BONUS_BIT);
        body.setLinearVelocity(new Vector2(new Random().nextInt(8)-4, -10));
        this.world=world;
        bonuses.add(this);
        isHit=false;
        spawnTime=TimeUtils.millis();
    }
    public static void drawAll(SpriteBatch batch){
        for (Bonus b:bonuses) {
            batch.draw(b.texture,b.getX()-b.width/2,b.getY()-b.height/2,b.width,b.height);
        }
    }
    public boolean isInFrame(){
        if (getX()+width/2<0){
            setX((int) (GameSettings.SCR_WIDTH+width/2));
        }
        if (getX()-width/2>GameSettings.SCR_WIDTH){
            setX((int) (0-width/2));
        }
        return getY() + height / 2 > 0 && getY()<2*GameSettings.SCR_HEIGHT;
    }
    private boolean needToDelete(){
        return TimeUtils.millis()-spawnTime>=8000;
    }
    public static void updateBonuses(Main main){
        Iterator<Bonus> iterator = bonuses.iterator();
        while(iterator.hasNext()){
            Bonus b = iterator.next();
            if (!b.isInFrame() || b.isHit || b.needToDelete()){
                main.world.destroyBody(b.body);
                iterator.remove();
            }
        }
    }

    @Override
    public void hit() {
        isHit=true;

    }
}
