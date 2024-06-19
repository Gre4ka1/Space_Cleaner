package com.mygdx.game.Units;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;

public class CoolDownBonus extends Bonus{
    public CoolDownBonus(World world) {
        super(GameResources.COOLDOWN_BONUS, world);
    }

    @Override
    public void hit() {
        super.hit();
        GameSettings.bonus="COOLDOWN";
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
