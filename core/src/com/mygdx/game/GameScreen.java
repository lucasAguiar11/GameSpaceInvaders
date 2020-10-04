package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;

public class GameScreen implements Screen {

    private Camera camera;
    private Viewport viewport;

    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private TextureAtlas TextureSpaceAtlas;

    //    private Texture background;
    private TextureRegion[] backgrounds;

    private TextureRegion playerShipTextureRegion, playerShieldTextureRegion,
            enemyShipTextureRegion, enemyShieldTextureRegion,
            playerLaserTextureRegion, enemyLaserTextureRegion;


    //    private  int backgroundOffSet;
    private float[] backgroundOffSets = {0, 0, 0, 0, 0, 0};
    private float backgroundMaxScrollingSpeed;

    private final int WORLD_WIDTH = 28;
    private final int WORLD_HEIGHT = 128;

    //gameObjects
    private Ship playerShip;
    private Ship enemyShip;
    private LinkedList<Laser> playerLaserList;
    private LinkedList<Laser> enemyLaserList;

    GameScreen() {
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

//        background = new Texture("bk-nebulosa.gif");
//        backgroundOffSet = 0;


        //set texture with atlas
        textureAtlas = new TextureAtlas("images.atlas");
        TextureSpaceAtlas = new TextureAtlas("space.atlas");

        backgrounds = new TextureRegion[6];
        backgrounds[0] = TextureSpaceAtlas.findRegion("SPACE");
        backgrounds[1] = TextureSpaceAtlas.findRegion("NEBULOSA");
        backgrounds[2] = TextureSpaceAtlas.findRegion("ESTRELA  GRANDE");
        backgrounds[3] = TextureSpaceAtlas.findRegion("ESTRELA  MEDIAS");
        backgrounds[4] = TextureSpaceAtlas.findRegion("ESTRELA  MEDIAS E PEQUENAS");
        backgrounds[5] = TextureSpaceAtlas.findRegion("ESTRELA  PEQUENA");

//        backgrounds[0] = textureAtlas.findRegion("Starscape00");
//        backgrounds[1] = textureAtlas.findRegion("Starscape01");
//        backgrounds[2] = textureAtlas.findRegion("Starscape02");
//        backgrounds[3] = textureAtlas.findRegion("Starscape03");

        backgroundMaxScrollingSpeed = (float) (WORLD_HEIGHT) / 4;

        //init texture;
        playerShipTextureRegion = textureAtlas.findRegion("playerShip2_blue");
        enemyShipTextureRegion = textureAtlas.findRegion("enemyRed3");
        playerShieldTextureRegion = textureAtlas.findRegion("shield2");
        enemyShieldTextureRegion = textureAtlas.findRegion("shield1");
        enemyShieldTextureRegion.flip(false, true);

        playerLaserTextureRegion = textureAtlas.findRegion("laserBlue03");
        enemyLaserTextureRegion = textureAtlas.findRegion("laserRed03");


        //set up game obj
        playerShip = new PlayerShip(
                (float) WORLD_WIDTH / 2, (float) WORLD_HEIGHT / 4,
                4, 7,
                5, 7,
                0.4f, 4, 45, 0.5f,
                playerShipTextureRegion, playerShieldTextureRegion, playerLaserTextureRegion);

        enemyShip = new EnemyShip(
                (float) WORLD_WIDTH / 2, (float) WORLD_HEIGHT * 3 / 4,
                4, 7,
                5, 7,
                0.4f, 4, 50, 0.8f,
                enemyShipTextureRegion, enemyShieldTextureRegion, enemyLaserTextureRegion);

        playerLaserList = new LinkedList<>();
        enemyLaserList = new LinkedList<>();

        batch = new SpriteBatch();
    }


    @Override
    public void render(float deltaTime) {
        batch.begin();

        playerShip.update(deltaTime);
        enemyShip.update(deltaTime);

        //Scrolling with paralax
        renderBackground(deltaTime);

        //enemy
        enemyShip.draw(batch);

        //player
        playerShip.draw(batch);

        //lasers
        //create news lasers
        //player lasers
//        if (playerShip.canFireLaser()) {
//            Laser[] lasers = playerShip.fireLasers();
//            Collections.addAll(playerLaserList, lasers);
//        }

        //enemy lasers
        if (enemyShip.canFireLaser()) {
            Laser[] lasers = enemyShip.fireLasers();
            Collections.addAll(enemyLaserList, lasers);
        }

        //draw lasers
        //remove old lasers
        ListIterator<Laser> iterator = playerLaserList.listIterator();
        while (iterator.hasNext()) {
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.yPosition += laser.movimentSpeed + deltaTime;
            if (laser.yPosition > WORLD_HEIGHT) {
                iterator.remove();
            }
        }

        iterator = enemyLaserList.listIterator();
        while (iterator.hasNext()) {
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.yPosition -= laser.movimentSpeed + deltaTime;
            if (laser.yPosition + laser.height < 0) {
                iterator.remove();
            }
        }

        //explosions

        batch.end();
    }

    private void renderBackground(float deltaTime) {
//        backgroundOffSet ++;
//        if(backgroundOffSet % WORLD_HEIGHT == 0){
//            backgroundOffSet = 0;
//        }
//        batch.draw(background, 0, -backgroundOffSet , WORLD_WIDTH, WORLD_HEIGHT);
//        batch.draw(background, 0, -backgroundOffSet + WORLD_HEIGHT , WORLD_WIDTH, WORLD_HEIGHT);

        backgroundOffSets[0] += deltaTime * backgroundMaxScrollingSpeed / 10;
        backgroundOffSets[1] += deltaTime * backgroundMaxScrollingSpeed / 8;
        backgroundOffSets[2] += deltaTime * backgroundMaxScrollingSpeed / 6;
        backgroundOffSets[3] += deltaTime * backgroundMaxScrollingSpeed / 4;
        backgroundOffSets[4] += deltaTime * backgroundMaxScrollingSpeed / 2;
        backgroundOffSets[5] += deltaTime * backgroundMaxScrollingSpeed;

        for (int layer = 0; layer < backgroundOffSets.length; layer++) {

            //reiniciar valor do backgroundOffSets para não ultrapassar a tela
            if (backgroundOffSets[layer] > WORLD_HEIGHT)
                backgroundOffSets[layer] = 0;

            batch.draw(backgrounds[layer],
                    0,
                    -backgroundOffSets[layer],
                    WORLD_WIDTH,
                    WORLD_HEIGHT);

            //criar nova imagem e na bugar
            batch.draw(backgrounds[layer],
                    0,
                    -backgroundOffSets[layer] + WORLD_HEIGHT,
                    WORLD_WIDTH,
                    WORLD_HEIGHT);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
