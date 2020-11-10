package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.screens.GameOver;
import com.mygdx.game.screens.GameScreen;

import java.util.Random;

public class SpaceInvaders extends Game {

	com.mygdx.game.screens.GameScreen gameScreen;
	com.mygdx.game.screens.GameOver gameOverScreen;

	public static Random random = new Random();

	@Override
	public void create() {
		gameScreen = new GameScreen();
		gameOverScreen = new GameOver();

		if(gameScreen.hasDefeated){
			setScreen(gameOverScreen);
		}else{
			setScreen(gameScreen);
		}

	}

	@Override
	public void dispose() {
		gameScreen.dispose();
		gameOverScreen.dispose();
	}

	@Override
	public void render() {

		if(gameScreen.hasDefeated){
			setScreen(gameOverScreen);
		}

		if(gameOverScreen.getIsDone()){
			gameOverScreen = new GameOver();
			gameScreen.hasDefeated = false;
			gameScreen.playerShip.lives = 3;
			gameScreen.quantityEnemies = 0;
			gameScreen.hasBoss = false;
			gameScreen.score = 0;
			gameScreen.enemyShipList.clear();
			setScreen(gameScreen);
		}

		super.render();
	}

	@Override
	public void resize(int width, int height) {
		gameScreen.resize(width, height);
	}
}
