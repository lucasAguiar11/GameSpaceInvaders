package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Align;

public class GameWin implements Screen {

    /** the SpriteBatch used to draw the background, logo and text **/
    private final SpriteBatch spriteBatch;
    /** the background texture **/
    private final TextureRegion background;
    /** the logo texture **/
    private final Texture logo;
    /** the font **/
    private final BitmapFont font;
    /** is done flag **/
    private boolean isDone = false;
    /** view & transform matrix **/
    private final Matrix4 viewMatrix = new Matrix4();
    private final Matrix4 transformMatrix = new Matrix4();
    private final GlyphLayout glyphLayout = new GlyphLayout();

    private TextureRegion[] backgrounds;
    private TextureAtlas TextureSpaceAtlas;

    public GameWin(){
        spriteBatch = new SpriteBatch();
//        background = new Texture(Gdx.files.internal("darkPurpleStarscape.png"));
//        background.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        logo = new Texture(Gdx.files.internal("data/vencedor.png"));
        logo.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font = new BitmapFont(Gdx.files.internal("data/font16.fnt"), false);

        TextureSpaceAtlas = new TextureAtlas("space.atlas");
        background = TextureSpaceAtlas.findRegion("ESTRELA  PEQUENA");

    }

    public boolean getIsDone(){
        return isDone;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        if (Gdx.input.justTouched()) {
            isDone = true;
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewMatrix.setToOrtho2D(0, 0, 480, 320);
        spriteBatch.setProjectionMatrix(viewMatrix);
        spriteBatch.setTransformMatrix(transformMatrix);
        spriteBatch.begin();

        spriteBatch.disableBlending();
        spriteBatch.setColor(Color.WHITE);

        spriteBatch.draw(background, 0, 0, 480, 320);
        spriteBatch.enableBlending();
        spriteBatch.draw(logo, 10, 170, 460, 50, 0, 0, 564, 54, false, false);
        glyphLayout.setText(font, "Parabens voce venceu o jogo!\nPara ir ao proximo nivel clique na tela!",
                Color.WHITE, 500, Align.center, true);

        spriteBatch.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
        font.draw(spriteBatch, glyphLayout, 0, 140);
        spriteBatch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
