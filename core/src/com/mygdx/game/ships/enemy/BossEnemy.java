package com.mygdx.game.ships.enemy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.effects.Laser;

public class BossEnemy extends EnemyShip {
    public BossEnemy(float xCentre, float yCentre, float width, float height, float movimentSpeed, int shield, float laserWidth, float laserHeight, float laserMovementSpeed, float timeBetweenShots, TextureRegion shiTexture, TextureRegion shieldTextureRegion, TextureRegion laserTextureRegion, boolean boss) {
        super(xCentre, yCentre, width, height, movimentSpeed, shield, laserWidth, laserHeight, laserMovementSpeed, timeBetweenShots, shiTexture, shieldTextureRegion, laserTextureRegion, boss);
    }

    @Override
    public Laser[] fireLasers() {
        Laser[] laser = new Laser[1];

        //onde o laser vai sair da nave
        laser[0] = new Laser(boudingBox.x + boudingBox.width * 0.58f ,
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
