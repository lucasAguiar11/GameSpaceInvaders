package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EnemyShip extends Ship {


    public EnemyShip(float xCentre, float yCentre,
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

        laser[0] = new Laser(xPosition + width * 0.18f,
                yPosition - laserHeight,
                laserWidth, laserHeight,
                laserMovementSpeed, laserTextureRegion);

        laser[1] = new Laser(xPosition + width * 0.82f,
                yPosition - laserHeight,
                laserWidth, laserHeight,
                laserMovementSpeed, laserTextureRegion);

        timeSinceLaserShot = 0;

        return laser;
    }
}
