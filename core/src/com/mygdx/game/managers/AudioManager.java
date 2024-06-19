package com.mygdx.game.managers;

import static com.mygdx.game.GameSettings.isMusic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;

public class AudioManager {
    public Music backgroundMusic;
    public Sound shootSound;
    public Sound explosionSound;
    public String musicPath;

    public AudioManager() {
        musicPath=GameResources.VANGERS_FOREVER_PATH;
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(musicPath));
        shootSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.SHOOT_SOUND_PATH));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.DESTROY_SOUND_PATH));

        backgroundMusic.setVolume(0.2f);
        backgroundMusic.setLooping(true);
        updateMusicChange();
        updateSoundFlag();
        updateMusicFlag();
    }
    public void updateMusicFlag() {
        isMusic = MemoryManager.loadIsMusicOn();
        if (isMusic) backgroundMusic.play();
        else backgroundMusic.stop();
    }
    public void updateSoundFlag() {
        GameSettings.isSounds = MemoryManager.loadIsSoundOn();
    }

    public void updateMusicChange() {
        musicPath = MemoryManager.loadMusicPath();
        backgroundMusic=Gdx.audio.newMusic(Gdx.files.internal(musicPath));
        backgroundMusic.setVolume(0.2f);
        backgroundMusic.setLooping(true);
        updateMusicFlag();
    }
}