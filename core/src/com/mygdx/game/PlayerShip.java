package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerShip extends Ship {


    public PlayerShip(float xCentre, float yCentre,
                      float width, float height,
                      float movimentSpeed, int shield,
                      float laserWidth, float laserHeight,
                      float laserMovementSpeed, float timeBetweenShots,
                      TextureRegion shiTexture, TextureRegion shieldTextureRegion,
                      TextureRegion laserTextureRegion) {
        super(xCentre, yCentre, width, height, movimentSpeed, shield, laserWidth, laserHeight, laserMovementSpeed, timeBetweenShots, shiTexture, shieldTextureRegion, laserTextureRegion);
    }

    @Override
    public Laser[] fireLasers() {
        Laser[] laser = new Laser[2];

        laser[0] = new Laser(xPosition + width * 0.07f,
                yPosition + height * 0.45f,
                laserWidth, laserHeight,
                laserMovementSpeed, laserTextureRegion);

        laser[1] = new Laser(xPosition + width * 0.93f,
                yPosition + height * 0.45f,
                laserWidth, laserHeight,
                laserMovementSpeed, laserTextureRegion);

        timeSinceLaserShot = 0;

        return laser;
    }
}
