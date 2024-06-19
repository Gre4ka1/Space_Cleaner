package com.mygdx.game.Units;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.Main;

import java.util.Iterator;

public class BigTrash extends Trash{
    public BigTrash(int width, int height, World world) {
        super(GameResources.BIG_TRASH,width, height, world);
        lives=2;
    }
}
