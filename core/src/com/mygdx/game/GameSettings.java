package com.mygdx.game;

import com.mygdx.game.Units.Trash;

public class GameSettings {
    public static final float SCALE = 0.05f;
    public static final int SHIP_WIDTH = 150;
    public static final int SHIP_HEIGHT = 150;
    public static final int SHIP_FORCE_RATIO=10;
    public static final int SCR_WIDTH = 720;
    public static final int SCR_HEIGHT = 1280;
    public static final float STEP_TIME = 1f / 60;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 6;
    public static final float TRASH_VELOCITY = 10;
    public static final int STARTING_TRASH_APPEARANCE_COOL_DOWN = 2000;
    public static final int TRASH_WIDTH = 140;
    public static final int TRASH_HEIGHT = 100;
    public static final int BULLET_WIDTH=30;
    public static final int BULLET_HEIGHT=60;
    public static final int BULLET_VELOCITY=200;
    public static final long COOLDOWN_BONUS_TIME = 5000;
    public static int SHOOTING_COOL_DOWN =1000;
    public static final short TRASH_BIT = 1;
    public static final short SHIP_BIT = 2;
    public static final short BULLET_BIT = 4;
    public static final short BONUS_BIT = 8;
    public static final byte LIVES = 3;
    public static final int BONUS_HEIGHT = 100;
    public static final int BONUS_WIDTH = 100;
    public static boolean isMusic=true;
    public static boolean isSounds=true;

    public static String bonus="none";
    public static boolean isBonus=true;
}
