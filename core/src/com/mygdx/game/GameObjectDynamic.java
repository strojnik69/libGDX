package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.Iterator;


public abstract class GameObjectDynamic extends GameObject {

    public GameObjectDynamic(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    public abstract Rectangle getRectangle();
    public abstract void render(SpriteBatch batch);
    public abstract void updateScore(Score score, Iterator<GameObjectDynamic> iter);
    public abstract int getSpeed();

}


