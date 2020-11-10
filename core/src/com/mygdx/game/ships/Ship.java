package com.mygdx.game.ships;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.effects.Laser;

public abstract class Ship {

    //charecteristcts
    public float movimentSpeed;
    public int shield;

    //position & dimensions
    public Rectangle boudingBox;

    //graphics
    public TextureRegion shipTextureRegion, shieldTextureRegion, laserTextureRegion;

    //laser
    public float laserWidth, laserHeight;
    public float laserMovementSpeed;
    public float timeBetweenShots;
    public float timeSinceLaserShot = 0;

    public Ship(
            float xCentre, float yCentre,
            float width, float height,
            float movimentSpeed, int shield,
            float laserWidth, float laserHeight, float laserMovementSpeed,
            float timeBetweenShots,
            TextureRegion shiTexture, TextureRegion shieldTextureRegion,
            TextureRegion laserTextureRegion) {
        this.movimentSpeed = movimentSpeed;
        this.shield = shield;

        this.boudingBox = new Rectangle(xCentre - width / 2, yCentre - height / 2, width, height);

        this.laserWidth = laserWidth;
        this.laserHeight = laserHeight;
        this.timeBetweenShots = timeBetweenShots;
        this.laserMovementSpeed = laserMovementSpeed;
        this.shipTextureRegion = shiTexture;
        this.shieldTextureRegion = shieldTextureRegion;
        this.laserTextureRegion = laserTextureRegion;
    }

    public void update(float deltaTime) {
        timeSinceLaserShot += deltaTime;
    }

    public boolean canFireLaser() {
        return (timeSinceLaserShot - timeBetweenShots > 0);
    }

    public abstract Laser[] fireLasers();

    public boolean intersects(Rectangle rectangle) {
        return boudingBox.overlaps(rectangle);
    }

    public boolean hitAndCheckDestroyed(Laser laser) {
        if (shield > 0) {
            shield--;
            return false;
        }
        return true;
    }

    public void translate(float xChange, float yChange) {
        boudingBox.setPosition(boudingBox.x + xChange, boudingBox.y + yChange);
    }

    public void draw(Batch batch) {
        batch.draw(shipTextureRegion, boudingBox.x, boudingBox.y, boudingBox.width, boudingBox.height);
        if (shield > 1)
            batch.draw(shieldTextureRegion,
                    boudingBox.x - 0.3f, boudingBox.y + boudingBox.height / 1.7f,
                    boudingBox.width * 1.1f, boudingBox.height * 0.8f);
    }

}









