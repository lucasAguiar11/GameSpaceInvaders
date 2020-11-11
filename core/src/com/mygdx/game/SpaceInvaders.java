package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.screens.GameOver;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.GameWin;

import java.util.Random;

public class SpaceInvaders extends Game {

	com.mygdx.game.screens.GameScreen gameScreen;
	com.mygdx.game.screens.GameOver gameOverScreen;
	com.mygdx.game.screens.GameWin gameWinScreen;

	public static Random random = new Random();

	@Override
	public void create() {
		gameScreen = new GameScreen();
		gameOverScreen = new GameOver();
		gameWinScreen = new GameWin();

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
		
		if(gameScreen.hasVictory){
			setScreen(gameWinScreen);
		}

		if(gameOverScreen.getIsDone()){
			gameOverScreen = new GameOver();
			clearGame();
			setScreen(gameScreen);
		}

		if( gameWinScreen.getIsDone()){
			clearGame();
			gameWinScreen = new GameWin();
			gameScreen.level += 1;
			setScreen(gameScreen);
		}

		super.render();
	}

	private void clearGame(){
		gameScreen.playerShip.lives = 3;
		gameScreen.quantityEnemies = 0;
		gameScreen.enemySpanTimer = 0;
		gameScreen.timeBetweenEnemySpawns = 3f;

		gameScreen.enemyShipList.clear();
		gameScreen.enemyLaserList.clear();
		gameScreen.explosionList.clear();
		gameScreen.playerLaserList.clear();

		gameScreen.hasDefeated = false;
		gameScreen.hasVictory = false;
		gameScreen.hasBoss = false;

	}



	@Override
	public void resize(int width, int height) {
		gameScreen.resize(width, height);
	}
}
