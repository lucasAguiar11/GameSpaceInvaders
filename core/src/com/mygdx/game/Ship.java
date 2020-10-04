package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

abstract class Ship {

    //charecteristcts
    float movimentSpeed;
    int shield;

    //position & dimensions
    float xPosition, yPosition;
    float width, height;

    //graphics
    TextureRegion shipTextureRegion, shieldTextureRegion, laserTextureRegion;

    //laser
    float laserWidth, laserHeight;
    float laserMovementSpeed;
    float timeBetweenShots;
    float timeSinceLaserShot = 0;

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
        this.xPosition = xCentre - width / 2;
        this.yPosition = yCentre - height / 2;
        this.width = width;
        this.height = height;
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

    public void draw(Batch batch) {
        batch.draw(shipTextureRegion, xPosition, yPosition, width, height);
        if (shield > 1)
            batch.draw(shieldTextureRegion, xPosition, yPosition, width, height);
    }
}









