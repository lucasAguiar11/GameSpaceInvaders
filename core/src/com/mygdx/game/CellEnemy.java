package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CellEnemy extends EnemyShip{
    public CellEnemy(float xCentre, float yCentre, float width, float height, float movimentSpeed, int shield, float laserWidth, float laserHeight, float laserMovementSpeed, float timeBetweenShots, TextureRegion shiTexture, TextureRegion shieldTextureRegion, TextureRegion laserTextureRegion) {
        super(xCentre, yCentre, width, height, movimentSpeed, shield, laserWidth, laserHeight, laserMovementSpeed, timeBetweenShots, shiTexture, shieldTextureRegion, laserTextureRegion);
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
