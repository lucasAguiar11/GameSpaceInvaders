package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Laser {

    //position and dimensions
    float xPosition, yPosition;
    float width, height;

    //laser physical
    float movimentSpeed;

    //graphics
    TextureRegion textureRegion;

    public Laser(float xPosition, float yPosition,
                 float width, float height,
                 float movimentSpeed, TextureRegion textureRegion) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.movimentSpeed = movimentSpeed;
        this.textureRegion = textureRegion;
    }

    public void draw(Batch batch){
        batch.draw(textureRegion, xPosition - width/2, yPosition, width, height);
    }

}
