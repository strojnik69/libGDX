package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Score extends GameObject{
    private int currentLife;
    private int currentCoin;

    public Score(float x, float y, float width, float height, int currentCoin, int currentLife) {
        super(x, y, width, height);
        this.currentLife=currentLife;
        this.currentCoin=currentCoin;
    }

    public int getCurrentLife() {
        return currentLife;
    }

    public int getCurrentCoin() {
        return currentCoin;
    }

    public void setCurrentLife(int currentLife) {
        this.currentLife = currentLife;
    }

    public void setCurrentCoin(int currentCoin) {
        this.currentCoin = currentCoin;
    }

    boolean isGameEnd()
    {
        if(currentLife<=0)
            return true;
        return false;
    }

    @Override
    public void render(SpriteBatch batch) {
        Assets.font.setColor(Color.WHITE);
        Assets. font.draw(batch, "Score", Gdx.graphics.getWidth() - 140, Gdx.graphics.getHeight() - 20);
        Assets.font.setColor(Color.YELLOW);
        Assets.font.draw(batch, "" + currentCoin, Gdx.graphics.getWidth() - 50, Gdx.graphics.getHeight() - 20);

        Assets.font.setColor(Color.WHITE);
        Assets.font.draw(batch, "Health", 20, Gdx.graphics.getHeight() - 20);
        Assets.font.setColor(Color.GREEN);
        Assets.font.draw(batch, "" + currentLife, 120, Gdx.graphics.getHeight() - 20);

    }
}
