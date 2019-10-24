package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

import static com.mygdx.game.Assets.asteroidImage128;
import static com.mygdx.game.Assets.collisionSound;

public class Meteor extends GameObjectDynamic{

    private Rectangle meteor;
    private static long createNextInTime;
    private static long CREATE_METEOR_TIME = 1300000000;
    private float rotate = (float) 0.4;


    public Meteor() {
        super(MathUtils.random(0,Gdx.graphics.getWidth() - asteroidImage128.getWidth()), Gdx.graphics.getHeight(), asteroidImage128.getWidth(), asteroidImage128.getHeight());
        meteor = new Rectangle();
        meteor.x = MathUtils.random(0, Gdx.graphics.getWidth() - asteroidImage128.getWidth());
        meteor.y = Gdx.graphics.getHeight();
        meteor.width=asteroidImage128.getWidth();
        meteor.height=asteroidImage128.getHeight();
    }

    static boolean isTimeToCreateNew()
    {
        return TimeUtils.nanoTime()- createNextInTime > CREATE_METEOR_TIME;
    }

    static void setCreateNextInTime()
    {
        createNextInTime= TimeUtils.nanoTime();
    }

    @Override
    public void render(SpriteBatch batch)
    {
        batch.draw(asteroidImage128, meteor.x, meteor.y, meteor.x/2, meteor.y/2, asteroidImage128.getWidth(), asteroidImage128.getHeight(), 1, 1, rotate, 0, 0, (int) bounds.width, (int) bounds.height, false, false);

    }

    @Override
    public void updateScore(Score score, Iterator<GameObjectDynamic> iter) {
        collisionSound.play();
        score.setCurrentLife(score.getCurrentLife()-1);
    }

    @Override
    public Rectangle getRectangle() {
        return meteor;
    }

    @Override
    public int getSpeed() {
        return 130;
    }
}
