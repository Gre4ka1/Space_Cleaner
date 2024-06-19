package com.mygdx.game.Units;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.Main;

import java.util.ArrayList;
import java.util.Iterator;

public class Bullet extends GameObject{
    private boolean isHit=false;
    public World world;

    public static ArrayList<Bullet> bullets = new ArrayList<>();
    public Bullet(int x, int y, World world) {
        super(GameResources.BULLET_IMG_PATH, x, y, GameSettings.BULLET_WIDTH, GameSettings.BULLET_HEIGHT, world,GameSettings.BULLET_BIT);
        this.world=world;
        body.setBullet(true);
        body.setLinearVelocity(new Vector2(0,GameSettings.BULLET_VELOCITY));
        bullets.add(this);

    }
    @Override
    public void hit() {
        isHit=true;
    }

    public static void updateBullets(Main main){
        Iterator<Bullet> iterator = bullets.iterator();

        while(iterator.hasNext()){
            Bullet t = iterator.next();
            if (!t.isInFrame() || t.isHit || t.body.getLinearVelocity().y<40){
                main.world.destroyBody(t.body);
                iterator.remove();

            }
        }
    }
    public static void drawAll(SpriteBatch batch) {
        for (Bullet b:bullets) {

            batch.draw(b.texture,b.getX()-b.width/2,b.getY()-b.height/2,b.width,b.height);
        }
    }
    public boolean isInFrame(){
        return getY() - height / 2 < GameSettings.SCR_HEIGHT;
    }

}
