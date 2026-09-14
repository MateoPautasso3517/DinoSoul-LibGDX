package com.dinosoul;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class DinoSoulMain extends Game {
    private SpriteBatch batch;
    private Texture image;
    private BitmapFont font;


    @Override
    public void create() {
        batch = new SpriteBatch();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("CherryCreamSoda-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParams = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParams.size = 24;
        fontParams.color = Color.WHITE;
        font = generator.generateFont(fontParams);
        generator.dispose();

        setScreen((Screen) new PantallaControles(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        font.dispose();
    }

    public BitmapFont getFont() {
        return font;
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
