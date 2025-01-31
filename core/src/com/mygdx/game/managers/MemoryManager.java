package com.mygdx.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.GameResources;

import java.util.ArrayList;

public class MemoryManager {
    private static final Preferences preferences = Gdx.app.getPreferences("User saves");
    public static void saveSoundSettings(boolean isOn) {
        preferences.putBoolean("isSoundOn", isOn);
        preferences.flush();
    }

    public static boolean loadIsSoundOn() {
        return preferences.getBoolean("isSoundOn",true);
    }
    public static void saveMusicChange(String musicPath) {
        preferences.putString("musicChange",musicPath);
        preferences.flush();
    }
    public static void saveMusicSettings(boolean isOn) {
        preferences.putBoolean("isMusicOn", isOn);
        preferences.flush();
    }
    public static boolean loadIsMusicOn() {
        return preferences.getBoolean("isMusicOn", true);
    }
    public static void saveTableOfRecords(ArrayList<Integer> table) {
        if (table==null){
            preferences.remove("recordTable");
            preferences.flush();
            return;
        }
        Json json = new Json();
        String tableInString = json.toJson(table);
        preferences.putString("recordTable", tableInString);

        preferences.flush();
    }
    public static ArrayList<Integer> loadRecordsTable() {
        if (!preferences.contains("recordTable"))
            return null;

        String scores = preferences.getString("recordTable");
        Json json = new Json();
        ArrayList<Integer> table = json.fromJson(ArrayList.class, scores);
        return table;
    }


    public static String loadMusicPath() {
        return preferences.getString("musicChange", GameResources.VANGERS_FOREVER_PATH);
    }
}
