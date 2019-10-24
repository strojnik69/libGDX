package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import static com.mygdx.game.Assets.rocketImage;

class Rocket {
    private Rectangle rocket;
    private static int SPEED = 600; // pixels per second

    public Rectangle getShip() {
        return rocket;
    }

    public void setShip(Rectangle ship) {
        this.rocket = rocket;
    }

    void render(SpriteBatch batch)
    {
        batch.draw(rocketImage, rocket.x, rocket.y);
    }

    public Rocket(int x, int y) {
        this.rocket=new Rectangle();
        this.rocket.x=x;
        this.rocket.y=y;
        this.rocket.width= rocketImage.getWidth();
        this.rocket.height= rocketImage.getHeight();
    }

    public void moveLeft(){
        rocket.x -= SPEED * Gdx.graphics.getDeltaTime();
        if (rocket.x < 0){
            rocket.x = 0;
        }
    }

    public void moveRight(){
        rocket.x += SPEED * Gdx.graphics.getDeltaTime();
        if (rocket.x > Gdx.graphics.getWidth() - rocketImage.getWidth()){
            rocket.x = Gdx.graphics.getWidth() - rocketImage.getWidth();
        }
    }

    public void moveTop() {
        rocket.y += SPEED * Gdx.graphics.getDeltaTime();
        if (rocket.y > Gdx.graphics.getHeight() - rocketImage.getHeight()){
            rocket.y = Gdx.graphics.getHeight() - rocketImage.getHeight();
        }
    }
    public void moveBottom() {
        rocket.y -= SPEED * Gdx.graphics.getDeltaTime();
        if (rocket.y < 0){
            rocket.y = 0;
        }
    }


    public void touched(OrthographicCamera camera) {
        Vector3 touchPos = new Vector3();
        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPos);
        rocket.x = touchPos.x - rocket.getWidth() / 2;
        rocket.y = touchPos.y - rocket.getHeight() / 2;

        if (rocket.x < 0){
            rocket.x = 0;
        }

        if (rocket.y > Gdx.graphics.getHeight() - rocketImage.getHeight()){
            rocket.y = Gdx.graphics.getHeight() - rocketImage.getHeight();
        }

        if (rocket.y < 0){
            rocket.y = 0;
        }

        if (rocket.x > Gdx.graphics.getWidth() - rocketImage.getWidth()){
            rocket.x = Gdx.graphics.getWidth() - rocketImage.getWidth();
        }
    }
}
