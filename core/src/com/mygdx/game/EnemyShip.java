package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class EnemyShip extends Ship {

    Vector2 directionVector;
    float timeSinceLastDirectionChange = 0;
    float directionChangeFrequency = 0.75f;

    public EnemyShip(float xCentre, float yCentre,
                     float width, float height,
                     float movimentSpeed, int shield,
                     float laserWidth, float laserHeight,
                     float laserMovementSpeed, float timeBetweenShots,
                     TextureRegion shiTexture, TextureRegion shieldTextureRegion,
                     TextureRegion laserTextureRegion) {
        super(xCentre, yCentre, width, height, movimentSpeed, shield, laserWidth, laserHeight, laserMovementSpeed, timeBetweenShots, shiTexture, shieldTextureRegion, laserTextureRegion);

        directionVector = new Vector2(0, -1);
    }

    public Vector2 getDirectionVector() {
        return directionVector;
    }

    private void randomizeDirectionVector() {
        double bearing = SpaceInvaders.random.nextDouble() * 6.283185; // 0  to 2*PI
        directionVector.x = (float) Math.sin(bearing);
        directionVector.y = (float) Math.cos(bearing);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        timeSinceLastDirectionChange += deltaTime;
        if (timeSinceLastDirectionChange > directionChangeFrequency) {
            randomizeDirectionVector();
            timeSinceLastDirectionChange -= directionChangeFrequency;
        }
    }

    @Override
    public Laser[] fireLasers() {
        Laser[] laser = new Laser[2];

        //onde o laser vai sair da nave
        laser[0] = new Laser(boudingBox.x + boudingBox.width * 0.18f,
                boudingBox.y - laserHeight,
                laserWidth, laserHeight,
                laserMovementSpeed, laserTextureRegion);

        laser[1] = new Laser(boudingBox.x + boudingBox.width * 0.82f,
                boudingBox.y - laserHeight,
                laserWidth, laserHeight,
                laserMovementSpeed, laserTextureRegion);

        timeSinceLaserShot = 0;

        return laser;
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(shipTextureRegion, boudingBox.x, boudingBox.y, boudingBox.width, boudingBox.height);
        if (shield > 1)
            batch.draw(shieldTextureRegion,
                    boudingBox.x,
                    boudingBox.y - boudingBox.height * 0.5f,
                    boudingBox.width, boudingBox.height * 0.5f);
    }

}
