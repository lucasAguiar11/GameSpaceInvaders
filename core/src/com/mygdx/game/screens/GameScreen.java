package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.effects.Explosion;
import com.mygdx.game.effects.Laser;
import com.mygdx.game.SpaceInvaders;
import com.mygdx.game.ships.enemy.SquidEnemy;
import com.mygdx.game.ships.enemy.BossEnemy;
import com.mygdx.game.ships.enemy.CellEnemy;
import com.mygdx.game.ships.enemy.EnemyShip;
import com.mygdx.game.ships.player.PlayerShip;

import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Locale;

public class GameScreen implements Screen {

    private Camera camera;
    private Viewport viewport;
    public boolean hasDefeated = false;
    public boolean hasVictory = false;
    public boolean hasBoss = false;


    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private TextureAtlas TextureSpaceAtlas;
    private Texture explosionTexture;

    //    private Texture background;
    private TextureRegion[] backgrounds;

    private TextureRegion playerShipTextureRegion, playerShieldTextureRegion, playerLaserTextureRegion;

    //Enemy type 1
    private TextureRegion enemyShipTextureRegion, enemyShieldTextureRegion, enemyLaserTextureRegion;

    //Enemy type 2
    private TextureRegion enemySquidShipTextureRegion, enemySquidShieldTextureRegion, enemySquidLaserTextureRegion;

    //boss
    private TextureRegion enemyBossTextureRegion, enemyBossLaserTextureRegion;

    private float[] backgroundOffSets = {0, 0, 0, 0, 0, 0};
    private float backgroundMaxScrollingSpeed;
    public float timeBetweenEnemySpawns = 3f;
    public float enemySpanTimer = 0;
    public int quantityEnemies = 0;
    public int level = 1;

    private final int WORLD_WIDTH = 28;
    private final int WORLD_HEIGHT = 128;
    private final float TOUCH_MOVIMENT_THRESHOLD = 0.5f;
    private final int MAX_ENEMIES = 2;

    //gameObjects
    public com.mygdx.game.ships.player.PlayerShip playerShip;
    public LinkedList<com.mygdx.game.ships.enemy.EnemyShip> enemyShipList;
    public LinkedList<Laser> playerLaserList;
    public LinkedList<Laser> enemyLaserList;
    public LinkedList<Explosion> explosionList;

    public int score = 0;

    //Heads-Up Display
    BitmapFont font;
    float hudVerticalMargin, hudLeftX, hudRightX, hudCentreX, hudRowY, hudRow2Y, hudSectionWidth;
    public GameScreen() {
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        // background = new Texture("bk-nebulosa.gif");
        // backgroundOffSet = 0;

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

        backgroundMaxScrollingSpeed = (float) (WORLD_HEIGHT) / 4;

        //init texture;

        //player
        playerShipTextureRegion = TextureSpaceAtlas.findRegion("PLAYER");
        playerShieldTextureRegion = TextureSpaceAtlas.findRegion("ESCUDO");
        playerLaserTextureRegion = textureAtlas.findRegion("laserBlue03");

        //enemy type 1
        enemyShipTextureRegion = TextureSpaceAtlas.findRegion("ALIEN-01");
        enemyShieldTextureRegion = TextureSpaceAtlas.findRegion("ALIEN ESCUDO-01");
        enemyShieldTextureRegion.flip(false, true);
        enemyLaserTextureRegion = TextureSpaceAtlas.findRegion("ALIEN TIRO-01");

        //enemy type 2
        enemySquidShipTextureRegion = TextureSpaceAtlas.findRegion("LULA ALIEN-01");
        enemySquidShieldTextureRegion = TextureSpaceAtlas.findRegion("LULA ALIEN ESCUDO-01");
        enemySquidShieldTextureRegion.flip(false, true);
        enemySquidLaserTextureRegion = TextureSpaceAtlas.findRegion("LULA ALIEN TIRO-01");

        //boss
        enemyBossTextureRegion = TextureSpaceAtlas.findRegion("minion-01");
        enemyBossLaserTextureRegion = TextureSpaceAtlas.findRegion("minio tiro-01");

        explosionTexture = new Texture("explosion.png");

        //set up game obj
        playerShip = new PlayerShip(
                (float) WORLD_WIDTH / 2, (float) WORLD_HEIGHT / 4,
                6, 14,
                48, 6,
                0.4f, 5, 2, 1f,
                playerShipTextureRegion, playerShieldTextureRegion, playerLaserTextureRegion);


        enemyShipList = new LinkedList<>();
        playerLaserList = new LinkedList<>();
        enemyLaserList = new LinkedList<>();
        explosionList = new LinkedList<>();

        batch = new SpriteBatch();

        prepareHUD();
    }


    private void prepareHUD(){

        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("EdgeOfTheGalaxyRegular-OVEa6.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();


        fontParameter.size = 32;
        fontParameter.borderWidth = 3.6f;
        fontParameter.color = new Color(1,1,1,8.3f);
        fontParameter.borderColor = new Color(0,0,0,0.3f);

        font = fontGenerator.generateFont(fontParameter);
        font.getData().setScale(0.06f);
        //calculate hud margins, etc.
        hudVerticalMargin = font.getCapHeight();
        hudLeftX = hudVerticalMargin;
        hudRightX = WORLD_WIDTH * 2 / 3 - hudLeftX;
        hudCentreX = WORLD_WIDTH / 3;
        hudRowY = WORLD_HEIGHT - hudVerticalMargin;
        hudRow2Y = hudRowY - hudVerticalMargin - font.getCapHeight();
        hudSectionWidth = WORLD_WIDTH / 3;

    }

    @Override
    public void render(float deltaTime) {
        batch.begin();


        //Scrolling with paralax
        renderBackground(deltaTime);

        detectInput(deltaTime);

        playerShip.update(deltaTime);

        spawnEnemyShip(deltaTime);

       for (com.mygdx.game.ships.enemy.EnemyShip enemyShip : enemyShipList) {
            moveEnemies(enemyShip, deltaTime);
            enemyShip.update(deltaTime);
            enemyShip.draw(batch);
        }

        //morreu?
        verifyStatusPlayer();

        //player
        playerShip.draw(batch);

        //lasers
        renderLasers(deltaTime);

        //detect collisions
        detectCollisions();

        //explosions
        updateAndRenderExplosion(deltaTime);

        //hud rendering
        updateAndRenderHUD();

        batch.end();
    }


    private void verifyStatusPlayer(){
        if(playerShip.lives < 1)
            hasDefeated = true;
    }


    private void updateAndRenderHUD() {
        //render top row labels
        font.draw(batch, "Pontos", hudLeftX, hudRowY, hudSectionWidth, Align.left, false);
        font.draw(batch, "Escudos", hudCentreX, hudRowY, hudSectionWidth, Align.center, false);
        font.draw(batch, "Vidas", hudRightX, hudRowY, hudSectionWidth, Align.right, false);
        font.draw(batch, "Level", 1, 5, hudSectionWidth, Align.left, false);

        //render second row values
        font.draw(batch, String.format(Locale.getDefault(), "%06d", score), hudLeftX, hudRow2Y, hudSectionWidth, Align.left, false);
        font.draw(batch, String.format(Locale.getDefault(), "%02d", playerShip.shield), hudCentreX, hudRow2Y, hudSectionWidth, Align.center, false);
        font.draw(batch, String.format(Locale.getDefault(), "%02d", playerShip.lives), hudRightX, hudRow2Y, hudSectionWidth, Align.right, false);
        font.draw(batch, String.format(Locale.getDefault(), "%02d", level), 7, 5, hudSectionWidth, Align.left, false);
    }

    private void spawnEnemyShip(float deltaTime) {
        enemySpanTimer += deltaTime;
        if (enemySpanTimer > timeBetweenEnemySpawns && quantityEnemies < (MAX_ENEMIES + level) && !hasBoss) {

            System.out.println(Math.abs(deltaTime * SpaceInvaders.random.nextFloat() * 100));

            if ((int) (deltaTime * SpaceInvaders.random.nextFloat() * 100) % 2 == 0) {
                enemyShipList.add(new CellEnemy(SpaceInvaders.random.nextFloat() * (WORLD_WIDTH * 16) + 5,
                        (float) WORLD_HEIGHT - 5,
                        6, 14,
                        15, 10,
                        2, 4f,
                        2, 0.7f,
                        enemyShipTextureRegion, enemyShieldTextureRegion, enemyLaserTextureRegion));
                enemySpanTimer -= timeBetweenEnemySpawns;
            } else {

                enemyShipList.add(new SquidEnemy(SpaceInvaders.random.nextFloat() * (WORLD_WIDTH * 16) + 5,
                        (float) WORLD_HEIGHT - 5,
                        3f, 14,
                        15, 10,
                        2f, 4f,
                        4, 0.9f,
                        enemySquidShipTextureRegion, enemySquidShieldTextureRegion, enemySquidLaserTextureRegion));
            }

            quantityEnemies++;
        }

        if(hasBoss && quantityEnemies < 1){

            quantityEnemies = 0;
            enemyShipList.clear();
            enemyLaserList.clear();

            enemyShipList.add(new BossEnemy(SpaceInvaders.random.nextFloat() * (WORLD_WIDTH * 16) + 5,
                    (float) WORLD_HEIGHT - 5,
                    15f, 32,
                    20, 30, //20
                    2f, 6f,
                    2, 1f,
                    enemyBossTextureRegion, enemySquidShieldTextureRegion, enemyBossLaserTextureRegion, true));

            quantityEnemies++;
        }



    }

    private void moveEnemies(com.mygdx.game.ships.enemy.EnemyShip enemyShip, float deltaTime) {
        //keyboard input
        float leftLimit, rightLimit, upLimit, downLimit;
        leftLimit = -enemyShip.boudingBox.x;
        downLimit = (float) WORLD_HEIGHT / 2 - enemyShip.boudingBox.y;
        rightLimit = WORLD_WIDTH - enemyShip.boudingBox.x - enemyShip.boudingBox.width;
        upLimit = (float) WORLD_HEIGHT - enemyShip.boudingBox.y - enemyShip.boudingBox.height;

        //scale to the maximum speed of the ship
        float xMove = enemyShip.getDirectionVector().x * enemyShip.movimentSpeed * deltaTime;
        float yMove = enemyShip.getDirectionVector().y * enemyShip.movimentSpeed * deltaTime;

        if (xMove > 0) xMove = Math.min(xMove, rightLimit);
        else xMove = Math.max(xMove, leftLimit);

        if (yMove > 0) yMove = Math.min(yMove, upLimit);
        else yMove = Math.max(yMove, downLimit);

        enemyShip.translate(xMove, yMove);

    }

    private void detectInput(float deltaTime) {

        //keyboard input
        float leftLimit, rightLimit, upLimit, downLimit;
        leftLimit = -playerShip.boudingBox.x;
        downLimit = -playerShip.boudingBox.y;
        rightLimit = WORLD_WIDTH - playerShip.boudingBox.x - playerShip.boudingBox.width;
        upLimit = (float) WORLD_HEIGHT / 2 - playerShip.boudingBox.y - playerShip.boudingBox.height;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && rightLimit > 0) {
            playerShip.translate(Math.min(playerShip.movimentSpeed * deltaTime, rightLimit), 0f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && upLimit > 0) {
            playerShip.translate(0f, Math.min(playerShip.movimentSpeed * deltaTime, upLimit));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && leftLimit < 0) {
            playerShip.translate(-Math.max(playerShip.movimentSpeed * deltaTime, leftLimit), 0f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && downLimit < 0) {
            playerShip.translate(0f, -Math.max(playerShip.movimentSpeed * deltaTime, downLimit));
        }

        //touch input
        if (Gdx.input.isTouched()) {
            //get screen position
            float xTouchPixels = Gdx.input.getX();
            float yTouchPixels = Gdx.input.getY();

            //convert to world position
            Vector2 touchPoint = new Vector2(xTouchPixels, yTouchPixels);
            touchPoint = viewport.unproject(touchPoint);

            //calculate the x and y differences
            Vector2 playerShipCentre = new Vector2(
                    playerShip.boudingBox.x + playerShip.boudingBox.width / 2,
                    playerShip.boudingBox.y + playerShip.boudingBox.height / 2);

            float touchDistance = touchPoint.dst(playerShipCentre);

            if (touchDistance > TOUCH_MOVIMENT_THRESHOLD) {
                float xTouchDifference = touchPoint.x - playerShipCentre.x;
                float yTouchDifference = touchPoint.y - playerShipCentre.y;

                //scale to the maximum speed of the ship
                float xMove = xTouchDifference / touchDistance * playerShip.movimentSpeed * deltaTime;
                float yMove = yTouchDifference / touchDistance * playerShip.movimentSpeed * deltaTime;

                if (xMove > 0) xMove = Math.min(xMove, rightLimit);
                else xMove = Math.max(xMove, leftLimit);

                if (yMove > 0) yMove = Math.min(yMove, upLimit);
                else yMove = Math.max(yMove, downLimit);

                playerShip.translate(xMove, yMove);
            }

        }
    }

    private void detectCollisions() {
        //for each player laser, check intersects
        ListIterator<Laser> laserListIterator = playerLaserList.listIterator();
        while (laserListIterator.hasNext()) {
            Laser laser = laserListIterator.next();
            for (com.mygdx.game.ships.enemy.EnemyShip enemyShip : enemyShipList) {
                if (enemyShip.intersects(laser.boudingBox)) {
                    //contact enemy
                    if (enemyShip.hitAndCheckDestroyed(laser)) {
                        enemyShipList.remove();

                        if (enemyShip.IsABoss()){
                            hasVictory = true;
                        }
                        quantityEnemies--;
                        explosionList.add(new Explosion(explosionTexture, new Rectangle(enemyShip.boudingBox), 0.7f));
                    }
                    score += 100;

                    if(score >= 2000)
                        hasBoss = true;

                    laserListIterator.remove();
                    break;
                }
            }
        }

        //for each enemy laser, check intersects
        laserListIterator = enemyLaserList.listIterator();
        while (laserListIterator.hasNext()) {
            Laser laser = laserListIterator.next();
            if (playerShip.intersects(laser.boudingBox)) {
                //contact player
                if (playerShip.hitAndCheckDestroyed(laser)) {
                    explosionList.add(new Explosion(explosionTexture, new Rectangle(playerShip.boudingBox), 1.6f));
                    playerShip.shield = 10;
                    playerShip.lives--;
                }
                laserListIterator.remove();
            }

        }
    }

    private void updateAndRenderExplosion(float DeltaTime) {
        ListIterator<Explosion> explosionListIterator = explosionList.listIterator();
        while (explosionListIterator.hasNext()) {
            Explosion explosion = explosionListIterator.next();
            explosion.update(DeltaTime);
            if (explosion.isFinished()) {
                explosionListIterator.remove();
            } else {
                explosion.draw(batch);
            }
        }
    }

    private void renderLasers(float deltaTime) {
        //create news lasers
        //player lasers
        if (playerShip.canFireLaser()) {
            Laser[] lasers = playerShip.fireLasers();
            Collections.addAll(playerLaserList, lasers);
        }

        //enemy lasers
        for (EnemyShip enemyShip : enemyShipList) {
            if (enemyShip.canFireLaser()) {
                Laser[] lasers = enemyShip.fireLasers();
                Collections.addAll(enemyLaserList, lasers);
            }
        }

        //draw lasers
        //remove old lasers
        ListIterator<Laser> iterator = playerLaserList.listIterator();
        while (iterator.hasNext()) {
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.boudingBox.y += laser.movimentSpeed + deltaTime;
            if (laser.boudingBox.y > WORLD_HEIGHT) {
                iterator.remove();
            }
        }

        iterator = enemyLaserList.listIterator();
        while (iterator.hasNext()) {
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.boudingBox.y -= laser.movimentSpeed + deltaTime;
            if (laser.boudingBox.y + laser.boudingBox.height < 0) {
                iterator.remove();
            }
        }
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
        batch.dispose();
        font.dispose();
        textureAtlas.dispose();;
        TextureSpaceAtlas.dispose();;
        explosionTexture.dispose();;
    }
}
