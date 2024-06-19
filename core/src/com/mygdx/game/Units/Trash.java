package com.mygdx.game.Units;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.Main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Random;

public class Trash extends GameObject{
    public static ArrayList<Trash> trashes = new ArrayList<>();
    private static final int paddingHorizontal = 30;
    protected byte lives;
    World world;

    public Trash(String texturePath,int width, int height, World world) {
        super(texturePath, paddingHorizontal+new Random().nextInt(GameSettings.SCR_WIDTH-2*paddingHorizontal-width), GameSettings.SCR_HEIGHT+height/2, width, height, world,GameSettings.TRASH_BIT);
        body.setLinearVelocity(new Vector2(new Random().nextInt(8)-4, -GameSettings.TRASH_VELOCITY));
        lives=1;
        this.world=world;
        trashes.add(this);

    }
    public static void drawAll(SpriteBatch batch){
        for (Trash t:trashes) {
            batch.draw(t.texture,t.getX()-t.width/2,t.getY()-t.height/2,t.width,t.height);
        }
    }

    @Override
    public void hit() {
        lives--;
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
    public static void updateTrash(Main main){
        Iterator<Trash> iterator = trashes.iterator();

        while(iterator.hasNext()){
            Trash t = iterator.next();
            if (!t.isInFrame() || t.lives<=0){
                if (t.lives<=0  && GameSettings.isSounds) main.audioManager.explosionSound.play(0.1f);
                main.world.destroyBody(t.body);
                main.gameScreen.session.destructionRegistration();
                iterator.remove();

            }
        }

        /*for (Trash t: trashes) {
            if (!t.isInFrame()){
                main.world.destroyBody(t.body);
                trashes.remove(t);

            }
        }*/
    }
}
