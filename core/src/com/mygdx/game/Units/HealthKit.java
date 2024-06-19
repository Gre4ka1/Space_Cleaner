package com.mygdx.game.Units;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;

public class HealthKit extends Bonus{

    public HealthKit(World world) {
        super(GameResources.HEALTH_KIT, world);
    }
    @Override
    public void hit() {
        super.hit();
        GameSettings.bonus="HEALTHKIT";
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
