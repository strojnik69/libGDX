package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

import jdk.nashorn.internal.runtime.regexp.joni.Config;

import static com.mygdx.game.Assets.asteroidImage128;
import static com.mygdx.game.Assets.coinImage;
import static com.mygdx.game.Assets.coinSound;
import static com.mygdx.game.Assets.collisionSound;
import static com.mygdx.game.Assets.rocketImage;

public class MyGdxGame extends ApplicationAdapter {


	private SpriteBatch batch;
	private OrthographicCamera camera;
	//private Rectangle rocket;
	//private Array<Coin> coins;
	//private Array<Rectangle> asteroids;
	private Array<GameObjectDynamic> dynamicActors;
	private Score score;
	private Rocket rocket;
	private Render render;

	//private int health; //100

	//private BitmapFont font;


	//private static int SPEED = 600; // pixels per second
	//private static int SPEED_COIN = 250; // pixels per second
	//private static int SPEED_ASTROID = 130; // pixels per second
	//private static long CREATE_COIN_TIME = 2000000000; //ns
	//private static long CREATE_ASTEROID_TIME = 1300000000; //ns



	private void exitGame(){
		Gdx.app.exit();
	}
	
	@Override
	public void create () {

		//health = 100;

		BitmapFont font = new BitmapFont();
		render = new Render(font);
		font.getData().setScale(2);
		float height = Gdx.graphics.getHeight();
		float width = Gdx.graphics.getWidth();

		score = new Score(0,0, width, height, 0, 5);
		Assets.Load();
		//camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();

		rocket = new Rocket((int)width/2 ,  20);

		dynamicActors = new Array<>();
		spawnAsteroid();
		spawnCoin();

	}


	private void spawnCoin() {
		dynamicActors.add(new Coin());
		Coin.setCreateNextInTime();
	}

	private void spawnAsteroid() {
		dynamicActors.add(new Meteor());
		Meteor.setCreateNextInTime();
	}


	private void reset() {
		create();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clears screen

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		if(!score.isGameEnd())
		{
			if(Gdx.input.isTouched()) rocket.touched(camera); //mouse or touch screen
			if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) rocket.moveLeft();
			if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) rocket.moveRight();
			if(Gdx.input.isKeyPressed(Input.Keys.UP)) rocket.moveTop();
			if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) rocket.moveBottom();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) exitGame();
		if(Gdx.input.isKeyPressed(Input.Keys.R)) reset();


		batch.begin();
		{
			rocket.render(batch);
			for(GameObjectDynamic act: dynamicActors)
			{
				act.render(batch);
			}

			score.render(batch);
		}
		batch.end();

		//spawn novi objekt po casu
		if(Meteor.isTimeToCreateNew()) {
			spawnAsteroid();
		}

		if(Coin.isTimeToCreateNew()){
			spawnCoin();
		}


		//preverjanje konca igre
		if(score.getCurrentLife() > 0){

			for(Iterator<GameObjectDynamic> iter=dynamicActors.iterator(); iter.hasNext();)
			{
				GameObjectDynamic act = iter.next();
				act.getRectangle().y -= act.getSpeed()*Gdx.graphics.getDeltaTime();
				if(act.getRectangle().y +act.getRectangle().height < 0)
					iter.remove();
				if (act.getRectangle().overlaps(rocket.getShip())) {
					act.updateScore(score, iter);
					iter.remove();
				}
			}
		}else {
			batch.begin();

			{
				render.renderEndGame(batch);
			}
			batch.end();
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		Assets.Dispose();
	}
}
