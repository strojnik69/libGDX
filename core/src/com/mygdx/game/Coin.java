package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

import static com.mygdx.game.Assets.coinSound;
import static org.omg.CORBA.ORB.init;
import static com.mygdx.game.Assets.coinImage;

public class Coin extends GameObjectDynamic{

    private static int SPEED_COIN = 250; // pixels per second
    private static long CREATE_COIN_TIME = 2000000000; //ns
    private static long lastCoinTime;
    private Rectangle coin;


    Coin() {
        super(MathUtils.random(0, Gdx.graphics.getWidth() - coinImage.getWidth()), Gdx.graphics.getHeight(), coinImage.getWidth(), coinImage.getHeight());
        coin = new Rectangle();
        coin.x = MathUtils.random(0, Gdx.graphics.getWidth() - coinImage.getWidth());
        coin.y = Gdx.graphics.getHeight();
        coin.width = coinImage.getWidth();
        coin.height = coinImage.getHeight();
    }

    static boolean isTimeToCreateNew() {
        return TimeUtils.nanoTime() - lastCoinTime > CREATE_COIN_TIME;
    }

    static void setCreateNextInTime() {
        lastCoinTime = TimeUtils.nanoTime();
    }


    @Override
    public void render(SpriteBatch batch) {
        batch.draw(coinImage, coin.x, coin.y);
    }

    @Override
    public void updateScore(Score score, Iterator<GameObjectDynamic> iter) {
        coinSound.play();
        score.setCurrentCoin(score.getCurrentCoin() + 1);
    }

    @Override
    public Rectangle getRectangle() {
        return coin;
    }

    @Override
    public int getSpeed() {
        return SPEED_COIN;
    }


}
