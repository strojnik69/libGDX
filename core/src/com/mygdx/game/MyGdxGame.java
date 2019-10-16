package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class MyGdxGame extends ApplicationAdapter {

	private Texture coinImage;
	private Texture rocketImage;
	private Texture asteroidImage128;
	private Sound coinSound;
	private Sound collisionSound;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Rectangle rocket;
	private Array<Rectangle> coins; //special LibGDX Array
	private Array<Rectangle> asteroids;
	private long lastCoinTime;
	private long lastAsteroidTime;
	private int score;
	private int health; //100

	private BitmapFont font;

	private static int SPEED = 600; // pixels per second
	private static int SPEED_COIN = 250; // pixels per second
	private static int SPEED_ASTROID = 130; // pixels per second
	private static long CREATE_COIN_TIME = 2000000000; //ns
	private static long CREATE_ASTEROID_TIME = 1300000000; //ns

	private void moveLeft(){
		rocket.x -= SPEED * Gdx.graphics.getDeltaTime();
		if (rocket.x < 0){
			rocket.x = 0;
		}
	}

	private void moveRight(){
		rocket.x += SPEED * Gdx.graphics.getDeltaTime();
		if (rocket.x > Gdx.graphics.getWidth() - rocketImage.getWidth()){
			rocket.x = Gdx.graphics.getWidth() - rocketImage.getWidth();
		}
	}

	private void moveTop() {
		rocket.y += SPEED * Gdx.graphics.getDeltaTime();
		if (rocket.y > Gdx.graphics.getHeight() - rocketImage.getHeight()){
			rocket.y = Gdx.graphics.getHeight() - rocketImage.getHeight();
		}
	}
	private void moveBottom() {
		rocket.y -= SPEED * Gdx.graphics.getDeltaTime();
		if (rocket.y < 0){
			rocket.y = 0;
		}
	}
	private void touched() {
		Vector3 touchPos = new Vector3();
		touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(touchPos);
		rocket.x = touchPos.x - rocketImage.getWidth() / 2;
	}


	private void exitGame(){
		Gdx.app.exit();
	}
	
	@Override
	public void create () {

		font = new BitmapFont();
		font.getData().setScale(2);
		score = 0;
		health = 100;

		//slike
		rocketImage = new Texture(Gdx.files.internal("rocket64.png"));
		coinImage = new Texture(Gdx.files.internal("dollar32.png"));
		asteroidImage128 = new Texture(Gdx.files.internal("asteroid128.png"));
		coinSound = Gdx.audio.newSound(Gdx.files.internal("coinPick.wav"));
		collisionSound = Gdx.audio.newSound(Gdx.files.internal("collision.wav"));

		//camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();

		rocket = new Rectangle();
		rocket.x = Gdx.graphics.getWidth() / 2 - rocketImage.getWidth() / 2; //zacnemo na sredini oz postavimo ta
		rocket.y = 30;  //30 pikslov visje od spodnjega ekrana
		rocket.width = rocketImage.getWidth(); //dodamo okvir raketi, da zaznamo
		rocket.height = rocketImage.getHeight();

		coins = new Array<Rectangle>();
		asteroids = new Array<Rectangle>();

		spawnAsteroid();
		spawnCoin();
	}

	private void spawnCoin(){
		Rectangle coin = new Rectangle();
		coin.x = MathUtils.random(0, Gdx.graphics.getWidth() - coinImage.getWidth());
		coin.y = Gdx.graphics.getHeight();
		coin.width  = coinImage.getWidth();
		coin.height = coinImage.getHeight();
		coins.add(coin);
		lastCoinTime = TimeUtils.nanoTime();
	}

	private void spawnAsteroid() {
		Rectangle asteroid = new Rectangle();
		asteroid.x = MathUtils.random(0, Gdx.graphics.getWidth()- asteroidImage128.getWidth());
		asteroid.y = Gdx.graphics.getHeight();
		asteroid.width = asteroidImage128.getWidth();
		asteroid.height = asteroidImage128.getHeight();
		asteroids.add(asteroid);
		lastAsteroidTime = TimeUtils.nanoTime();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clears screen

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		{
			batch.draw(rocketImage, rocket.x, rocket.y);

			for (Rectangle asteroid : asteroids){
				batch.draw(asteroidImage128, asteroid.x, asteroid.y);
			}

			for (Rectangle coin : coins){
				batch.draw(coinImage, coin.x, coin.y);
			}

			font.setColor(Color.WHITE);
			font.draw(batch, "Score", Gdx.graphics.getWidth() - 140, Gdx.graphics.getHeight() - 20);
			font.setColor(Color.YELLOW);
			font.draw(batch, "" + score, Gdx.graphics.getWidth() - 50, Gdx.graphics.getHeight() - 20);
			font.setColor(Color.WHITE);
			font.draw(batch, "Health", 20, Gdx.graphics.getHeight() - 20);
			font.setColor(Color.GREEN);
			font.draw(batch, "" + health, 120, Gdx.graphics.getHeight() - 20);
		}
		batch.end();

		if(Gdx.input.isTouched()) touched(); //mouse or touch screen
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) moveLeft();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) moveRight();
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) moveTop();
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) moveBottom();
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) exitGame();

		//spawn novi objekt po casu
		if(TimeUtils.nanoTime() - lastCoinTime > CREATE_COIN_TIME) spawnCoin();
		if(TimeUtils.nanoTime() - lastAsteroidTime > CREATE_ASTEROID_TIME) spawnAsteroid();

		//preverjanje konca igre
		if(health > 0){
			for (Iterator<Rectangle> iter = asteroids.iterator(); iter.hasNext(); ) {
				Rectangle asteroid = iter.next();
				asteroid.y -= SPEED_ASTROID * Gdx.graphics.getDeltaTime();
				if (asteroid.y + asteroidImage128.getHeight() < 0) iter.remove();
				if (asteroid.overlaps(rocket)) {
					collisionSound.play();
					health--;
				}
			}

			for (Iterator<Rectangle> iter = coins.iterator(); iter.hasNext(); ) {
				Rectangle coin = iter.next();
				coin.y -= SPEED_COIN * Gdx.graphics.getDeltaTime();
				if (coin.y + coinImage.getHeight() < 0) iter.remove(); //odstranimo
				if (coin.overlaps(rocket)) {
					coinSound.play();
					score++;
					if (score%10==0){
						SPEED_ASTROID+=50; //povecamo hitrost asteroidov

						if (health <= 90){ //regenaracija zivlenja
							health+=10;
						}else {
							health = 100;
						}
					}
					iter.remove(); //smart Array enables remove from Array
				}
			}
		}else {
			batch.begin();

			{
				final String text = "The End";
				final GlyphLayout layout = new GlyphLayout(font, text);

				final float fontX =  (Gdx.graphics.getWidth() - layout.width) / 2;
				final float fontY = (Gdx.graphics.getHeight() + layout.height) / 2;

				font.setColor(Color.RED);
				font.draw(batch, text, fontX , fontY);
			}
			batch.end();
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		coinImage.dispose();
		rocketImage.dispose();
		coinSound.dispose();
	}
}
