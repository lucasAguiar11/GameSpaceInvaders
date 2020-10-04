package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Laser {

    //position and dimensions
    Rectangle boudingBox;

    //laser physical
    float movimentSpeed;

    //graphics
    TextureRegion textureRegion;

    public Laser(float xPosition, float yPosition,
                 float width, float height,
                 float movimentSpeed, TextureRegion textureRegion) {
        this.boudingBox = new Rectangle(xPosition - width / 2, yPosition, width, height);
        this.movimentSpeed = movimentSpeed;
        this.textureRegion = textureRegion;
    }

    public void draw(Batch batch) {
        batch.draw(textureRegion, boudingBox.x - boudingBox.width / 2, boudingBox.y, boudingBox.width, boudingBox.height);
    }
}
