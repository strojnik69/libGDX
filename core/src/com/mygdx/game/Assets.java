package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Assets {

    static Texture coinImage;
    static Texture rocketImage;
    static Texture asteroidImage128;
    static Sound coinSound;
    static Sound collisionSound;
    static BitmapFont font;

    static void Load() {

        rocketImage = new Texture(Gdx.files.internal("rocket64.png"));
        coinImage = new Texture(Gdx.files.internal("dollar32.png"));
        asteroidImage128 = new Texture(Gdx.files.internal("asteroid128.png"));
        coinSound = Gdx.audio.newSound(Gdx.files.internal("coinPick.wav"));
        collisionSound = Gdx.audio.newSound(Gdx.files.internal("collision.wav"));

        font = new BitmapFont();
        font.getData().setScale(2);

    }

    static void Dispose()
    {
        font.dispose();
        coinImage.dispose();
        rocketImage.dispose();
        coinSound.dispose();
    }
}
