package com.mygdx.game.ships.enemy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.effects.Laser;
import com.mygdx.game.ships.enemy.EnemyShip;

public class SquidEnemy extends EnemyShip {
    public SquidEnemy(float xCentre, float yCentre, float width, float height, float movimentSpeed, int shield, float laserWidth, float laserHeight, float laserMovementSpeed, float timeBetweenShots, TextureRegion shiTexture, TextureRegion shieldTextureRegion, TextureRegion laserTextureRegion) {
        super(xCentre, yCentre, width, height, movimentSpeed, shield, laserWidth, laserHeight, laserMovementSpeed, timeBetweenShots, shiTexture, shieldTextureRegion, laserTextureRegion);
    }
    @Override
    public com.mygdx.game.effects.Laser[] fireLasers() {
        com.mygdx.game.effects.Laser[] laser = new com.mygdx.game.effects.Laser[1];

        //onde o laser vai sair da nave
        laser[0] = new Laser(boudingBox.x + boudingBox.width * 0.85f ,
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
                    boudingBox.width, boudingBox.height * 0.4f);
    }
}
